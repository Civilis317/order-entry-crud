package net.playground.orderservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/secured")
public class SecuredPageController {
    private static final Logger logger = LoggerFactory.getLogger(SecuredPageController.class);


    @Value("${frontend.angular-url}")
    private String frontendUrl;

    @GetMapping
    public ModelAndView index(ModelMap modelMap, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String username = ((UserDetails) auth.getPrincipal()).getUsername();
            modelMap.put("username", UriUtils.decode(username, "UTF-8"));
            modelMap.put("displayName", resolveDisplayName(userDetails.getUsername()));
        }
        return new ModelAndView("redirect:" + frontendUrl, modelMap);
    }

    private String resolveDisplayName(String username) {
        String result;
        switch (username) {
            case "jdoe@acme.org":
                result = "John Doe";
                break;
            case "admin":
                result = "Administrator";
                break;
            default:
                result = username;
        }
        return result;
    }

}
