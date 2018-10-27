package com.marketingpersonal.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

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
