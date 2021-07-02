package me.amvse.qpro.typings;

import java.io.Serializable;

public class SigninSuccess implements Serializable {
	private final String token;

	public SigninSuccess (String token) { this.token = token; }

	public String getToken () { return this.token; }
}
