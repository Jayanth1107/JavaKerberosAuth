package com.jayan.java.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Driver {
	public enum Action {
		action1, action2, Logout
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();

		System.setProperty("java.security.auth.login.config", "jaasprj04.config");
		LoginContext loginContext = null;
		while (true) {
			try {
				loginContext = new LoginContext("JaJaasTutorial04", new JaCallbackHandler());
				loginContext.login();
				boolean flag = true;
				while (flag)
					flag = driver.performAction(loginContext);
			} catch (LoginException | IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	boolean performAction(LoginContext loginContext) throws IOException, LoginException {

		boolean flag = true;
		System.out.println("Please specify any of these actions : aciton1, action2 or Logout");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			switch (Action.valueOf(br.readLine())) {

			case Logout:
				loginContext.logout();
				System.out.println("Logged out........");
				flag = false;
				break;
			case action1:
				PrivilegedAction<Object> privilegedAction1 = () -> {
					System.out.println("action1 was performed");
					return null;
				};
				Subject.doAs(loginContext.getSubject(), privilegedAction1);
				System.out.println(" by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
				break;
			case action2:
				PrivilegedAction<Object> privilegedAction2 = () -> {
					System.out.println("action2 was performed");
					return null;
				};
				Subject.doAs(loginContext.getSubject(), privilegedAction2);
				System.out.println(" by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
				break;
			default:
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
