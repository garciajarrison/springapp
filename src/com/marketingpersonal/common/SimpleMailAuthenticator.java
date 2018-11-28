package com.marketingpersonal.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Clase para manejo de autenticación para envío de correo
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
class SimpleMailAuthenticator extends Authenticator {


    String userName;
    String password;
    PasswordAuthentication authentication;

    public SimpleMailAuthenticator(String userName,String password) {
        super();
        this.userName = userName;
        this.password = password;           
        authentication = new PasswordAuthentication(userName, password);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }


}  
