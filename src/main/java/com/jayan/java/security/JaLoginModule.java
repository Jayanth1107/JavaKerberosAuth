package com.jayan.java.security;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class JaLoginModule implements LoginModule {
	
	public static final String[][] TEST_USERS = {{"test1","password1"},{"test2","password2"},{"test3","password3"}}; 
	
	private Subject subject = null;
	private CallbackHandler callbackHandler = null;
	private JaPrincipal jaPrincipal;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		// TODO Auto-generated method stub
		System.out.println("JaLoginModule.initialize........");
		this.subject = subject;
		this.callbackHandler = callbackHandler;

	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		boolean flag = false;
		System.out.println("JaLoginModule.login..........");
		Callback[] callbackArray = new Callback[2];
		callbackArray[0] = new NameCallback("User name: ");
		callbackArray[1] = new PasswordCallback("Password:", false);
		try {
			callbackHandler.handle(callbackArray);
			String name = ((NameCallback) callbackArray[0]).getName();
			String password = new String(((PasswordCallback) callbackArray[1]).getPassword());
			
			int i = 0;
			while(i< TEST_USERS.length )
			{
				System.out.println("checking for name: "+ name+" and password: "+ password);
				if(TEST_USERS[i][0].equals(name) && TEST_USERS[i][1].equals(password)) {
					jaPrincipal = new JaPrincipal(name);
					System.out.println("Authentication Success........");
					flag = true;
					break;
				}
				i++;
			}
			if(flag==false) throw new FailedLoginException("Authentication Failure.......");
		}
		catch(IOException | UnsupportedCallbackException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(subject != null && !subject.getPrincipals().contains(jaPrincipal)) {
			subject.getPrincipals().add(jaPrincipal);
			flag = true;
		}
		System.out.println("JaLoginModule.commit..........");
		return flag;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("JaLoginModule.abort...........");
		if(subject != null && jaPrincipal != null && subject.getPrincipals().contains(jaPrincipal)) {
			subject.getPrincipals().remove(jaPrincipal);
			subject = null;
			jaPrincipal = null;
		}
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("JaLoginModule.logout...........");
		subject.getPrincipals().remove(jaPrincipal);
		subject = null;
		return true;
	}

}
