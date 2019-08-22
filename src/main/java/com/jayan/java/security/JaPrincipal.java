package com.jayan.java.security;

import java.io.Serializable;
import java.security.Principal;

public class JaPrincipal implements Principal, Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	public JaPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		System.out.println("JaPrincipal.getName..........");
		return name;
	}
	
	public boolean equals(Object object) {
		boolean flag = false;
		if(object instanceof JaPrincipal) flag = name.equals(((JaPrincipal) object).getName());
		return flag;
	}

}
