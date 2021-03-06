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
 * Name: Utils.java
 * Package: com.kyloth.serleenacloud.controller
 * Author: Nicola Mometto
 *
 * History:
 * Version  Programmer      Changes
 * 1.0.0    Nicola Mometto  Creazione file, codice e javadoc iniziali
 */

package com.kyloth.serleenacloud.controller;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe di utilità generale per il package controller.
 *
 * @field mailSender : JavaMailSenderImpl Campo dati statico contenente un oggetto di utilità per l'invio di email
 * @field host : String Campo dati statico contenente l'host SMTP verso cui stabilire una connessione.
 * @field port : String Campo dati statico contenente la porta dell'host SMTP verso cui stabilire una connessione.
 * @field username : String Campo dati statico contenente l'username dell'host SMTP verso cui stabilire una connessione.
 * @field password : String Campo dati statico contenente la password dell'host SMTP verso cui stabilire una connessione.
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Utils {

    /**
     * Oggetto di utilità per l'inivio di email.
     */

    private static JavaMailSenderImpl mailSender;

    /**
     * Host SMTP verso cui stabilire una connessione.
     */

    private static String host;

    /**
     * Porta dell'host SMTP verso cui stabilire una connessione.
     */

    private static String port;

    /**
     * Username dell'host SMTP verso cui stabilire una connessione.
     */

    private static String username;

    /**
     * Password dell'host SMTP verso cui stabilire una connessione.
     */

    private static String password;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

        host = (String)context.getBean("host");
        port = (String)context.getBean("port");
        username  = (String)context.getBean("username");
        password  = (String)context.getBean("password");

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.socketFactory.port", port);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", port);
        mailSender.setJavaMailProperties(properties);
    }

    /**
     * Metodo che permette di inviare una email.
     *
     * @param to Indirizzo del destinatario.
     * @param subj Soggetto della email.
     * @param body Corpo del messaggio.
     */

    public static void sendMail(String to, String subj, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subj);
        message.setText(body);
        mailSender.send(message);
    }

}
