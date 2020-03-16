package com.thejacket.springbootH2Mockmvc.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ExtendedBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {


        /* Override default security entrypoints to write a custom message when access is unauthorized */
        @Override
        public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 - " + authException.getMessage());
        }

        @Override
        public void afterPropertiesSet() {
            setRealmName("TEST REALM");
        }

    }