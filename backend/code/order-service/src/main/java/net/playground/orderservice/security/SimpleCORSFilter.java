package net.playground.orderservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SimpleCORSFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

    @Value("${frontend.angular-url}")
    private String frontendOrigin;

    public SimpleCORSFilter() {
        logger.info("SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Origin", frontendOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "SET-COOKIE, Authorization, Content-Type, Accept, X-Requested-With, remember-me, jsessionid, X-XSRF-TOKEN");

        /*
         * This is to facilitate a pre-flight options request, which the browser will perform
         * preliminary to a javascript defined GET or POST request. If this preflight request
         * is blocked by authorization, the whole request will fail. The code below will allow
         * the options request to succeed and pave the way for the actual request.
         */
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}