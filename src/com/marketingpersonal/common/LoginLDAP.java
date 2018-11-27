package com.marketingpersonal.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Clase para manejo de Login contra el directorio activo
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public class LoginLDAP {
		
		static final String LDAP_URL = "ldap://192.168.10.22:389/DC=marketing,DC=local";
		
		/**
	     * Método que realiza el login del usuario contra el directorio activo
	     * @param usuario: Variable que contiene el usuario ingresado
	     * @param contrasenia: Variable que contiene la contrasenia ingresada
	     * @return variable booleana que indica si el usuario y contraseña ingresados son correctos
	     */
		public boolean login(String usuario, String contrasenia) {
	        Hashtable env = new Hashtable();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, LDAP_URL);
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, usuario+"@marketing.local");
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
	}