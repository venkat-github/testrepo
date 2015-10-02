package com.test.app.web.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.app.repository.PersistentTokenRepository;
import com.test.app.repository.UserRepository;
import com.test.app.service.MailService;
import com.test.app.service.UserService;

/**
 * REST controller for managing the current user's account.
 */
@Controller
@RequestMapping("/api")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;

    @RequestMapping(value = "/register3",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String registerAccount(HttpServletRequest request) {
    	System.out.println("hello there");
    	return "test";
    }

    
}
