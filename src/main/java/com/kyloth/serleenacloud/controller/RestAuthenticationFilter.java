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


/**
 * Name: RestAuthenticationFilter.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

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

/**
 * Classe che si occupa di filtrare le richieste in arrivo assicurandosi che la richiesta contenga un token di autenticazione valido dove necessario.
 *
 * @use Ogni richiesta viene filtrata dal metodo doFilter, se la richiesta appartiene ad una classe di richieste definite nell'apposito file di configurazione come richiedente autenticazione, controlla che sia presente un token di autenticazione valido interagendo con AuthToken, rispondendo altrimenti alla richiesta con uno stato di errore.
 * @field ds : IDataSource Campo dati statico contenente un oggetto che permette di interfacciarsi con il database tramite DAO
 * @field patterns : String[] Insieme statico di stringhe contenenti espressioni regolari che permettono di identificare le route che richiedono autenticazione
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

@Component
public class RestAuthenticationFilter extends GenericFilterBean {

    /**
     * Oggetto che permette di interfacciarsi con il database tramite oggetti DAO.
     */

    static IDataSource ds = DataSourceFactory.getDataSource();

    /**
     * Insieme di stringhe rappresentanti espressioni regolari che permettono di identificare le route che richiedono autenticazione.
     */
    static String[] patterns = new String[] {
        "/users/pair/?$",
        "/data.*",
        "/experiences.*"
    };

    /**
     * Metodo di filtraggio delle richieste.
     *
     * @param request Rappresenta la richiesta http
     * @param response Rappresenta la risposta http
     * @param chain Lista di filtri da applicare a richiesta e risposta
     * @throws IOException Eccezione generale per problemi di input output
     * @throws ServletException Eccezione generale per problemi riguardanti le Servlet
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.err.println("---- REQUEST -----");
        java.util.Enumeration headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            System.err.println(headerName + ": " + req.getHeader(headerName));
        }
        java.util.Enumeration params = req.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            System.err.println("Attribute Name - "+paramName+", Value - "+req.getParameter(paramName));
        }
        System.err.println("---- REQUEST -----");

		res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        res.addHeader("Access-Control-Allow-Max-Age", "3600");
        res.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-AuthToken, X-AuthData");

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

    /**
     * Metodo che permette di stabilire se una richiesta richiede
     * autenticazione.
     *
     * @param path La richiesta.
     * @return Restituisce True se la richiesta richiede autenticazione, False altrimenti.
     */

    boolean requiresAuth(String path) {
        for (String p : patterns)
            if (Pattern.matches(p, path))
                return true;
        return false;
    }

}
