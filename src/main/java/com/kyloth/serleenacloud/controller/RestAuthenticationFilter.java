/******************************************************************************
* Copyright (c) 2015 Nicola Mometto
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*  Nicola Mometto
*  Antonio Cavestro
*  Sebastiano Valle
*  Gabriele Pozzan
******************************************************************************/


package com.kyloth.serleenacloud.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;
import org.springframework.stereotype.Component;

import com.kyloth.serleenacloud.persistence.IDataSource;
import com.kyloth.serleenacloud.persistence.DataSourceFactory;

import com.kyloth.serleenacloud.datamodel.auth.User;
import com.kyloth.serleenacloud.datamodel.auth.AuthToken;

import java.util.regex.Pattern;

@Component
public class RestAuthenticationFilter extends GenericFilterBean {

    static IDataSource ds = DataSourceFactory.getDataSource();

    static String[] patterns = new String[] {
        "/users/pair/?$",
        "/data.*",
        "/experiences".*
    };

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (requiresAuth(req.getServletPath())) {
            String authToken = req.getHeader("X-AuthToken");
            if (authToken == null)
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            else {

                AuthToken t = new AuthToken(authToken);
                User u = ds.userDao().find(t.getEmail());

                if (!t.validFor(u))
                    ((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                else
                    chain.doFilter(request, response);
            }
        } else
            chain.doFilter(request, response);
    }

    boolean requiresAuth(String path) {
        for (String p : patterns)
            if (Pattern.matches(p, path))
                return true;
        return false;
    }

}
