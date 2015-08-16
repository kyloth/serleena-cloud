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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe di utilità generale per il package controller.
 *
 * @field mailSender : JavaMailSenderImpl Campo dati statico contenente un oggetto di utilità per l'invio di email
 * @author Nicola Mometto <nicola.mometto@studenti.unipd.it>
 * @version 1.0
 */

public class Utils {
    
    /**
     * Oggetto di utilità per l'inivio di email.
     */

    private static JavaMailSenderImpl mailSender;

    static {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setUsername("");
        mailSender.setPassword("");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
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
