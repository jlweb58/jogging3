/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webber.jogging.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author John L. Webber <jlweb58@gmail.com>
 */
public class CookieClearingLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  private static final Logger LOG = LoggerFactory.getLogger(CookieClearingLogoutSuccessHandler.class);
  
  public CookieClearingLogoutSuccessHandler(String defaultTargetUrl) {
    setAlwaysUseDefaultTargetUrl(true);
    setDefaultTargetUrl(defaultTargetUrl);
  }
  
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    LOG.debug("Logout success, searching for cookies");
    for (Cookie cookie : request.getCookies()) {
      LOG.debug("Got a cookie");
      if (cookie.getName().equals("JSESSIONID")) {
        LOG.debug("Matched a cookie");
        //Clear one cookie
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        //Clear the other cookie
        Cookie cookieWithSlash = (Cookie) cookie.clone();
        cookieWithSlash.setPath(request.getContextPath() + "/");
        response.addCookie(cookieWithSlash);
      }
      //This is actually a filter; continue along the chain
      super.onLogoutSuccess(request, response, authentication);
    }
  }
}
