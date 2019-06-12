package net.playground.orderservice;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionEvent;

@SpringBootApplication
public class OrderServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceApplication.class);

    @Value("${truststore.override}")
    private boolean truststoreOverride;

    @Value("${truststore.hostname-verifier.override}")
    private boolean hostnameVerifierOverride;

    @Value("${truststore.location}")
    private String truststore;

    @Value("${truststore.password}")
    private String truststorePassword;

    @Value("${cas.server.url}")
    private String casUrl;

    @Value("${cas.client.service-id}")
    private String clientServiceId;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @PostConstruct
    public void setTrustStore() {
        if (truststoreOverride) {
            System.setProperty("javax.net.ssl.trustStore", Thread.currentThread().getContextClassLoader().getResource(truststore).getPath());
            System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
        }

        if (hostnameVerifierOverride) {
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                    (hostname, sslSession) -> {
                        return true;
                    }
            );
        }
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(clientServiceId);
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties sP) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(casUrl + "/login");
        entryPoint.setServiceProperties(sP);
        return entryPoint;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(casUrl);
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());

        User johnDoe = new User("jdoe@acme.org",
                "",
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("tmrenew"));

        User admin = new User("admin",
                "",
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("primaryusers", "basicusers", "tmrenew"));

        UserDetailsService userDetailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                UserDetails userDetails = null;
                switch (s) {
                    case "admin":
                        userDetails = admin;
                        break;
                    case "jdoe@acme.org":
                        userDetails = johnDoe;
                        break;
                    default:
                        throw new UsernameNotFoundException("Unknown username: {" + s + "}");
                }
                return userDetails;
            }
        };

        provider.setUserDetailsService(userDetailsService);

        provider.setKey("CAS_PROVIDER_LOCALHOST_9000");
        return provider;
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(
                casUrl + "/logout", securityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl("/logout/cas");
        return logoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casUrl);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    @EventListener
    public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(HttpSessionEvent event) {
        return new SingleSignOutHttpSessionListener();
    }


}
