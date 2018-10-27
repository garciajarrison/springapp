package com.marketingpersonal.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LoginLDAP {
		
		//static final String LDAP_URL = "ldap://192.168.10.22:389/DC=local,DC=marketing, ou=Marketing\bPersonal";
		static final String LDAP_URL = "ldap://192.168.10.22:389/DC=marketing,DC=local";
		public boolean login(String usuario, String contrasenia) {
	        Hashtable env = new Hashtable();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, LDAP_URL);
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, "comtic@marketing.local");
	        env.put(Context.SECURITY_CREDENTIALS, contrasenia);

	        try {
	            DirContext ctx = new InitialDirContext(env);
	            ctx.close();
	            return true;

	        } catch (NamingException ex) {
	        	System.out.println(ex);;
	            return false;
	        }
	    }
		
		public static void main(String[] args) {
			LoginLDAP a =new LoginLDAP();
			System.out.println(a.login("comtic", "mpcomtic13"));
		}
	}