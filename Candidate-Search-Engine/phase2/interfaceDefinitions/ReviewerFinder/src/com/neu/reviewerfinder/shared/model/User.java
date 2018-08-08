package com.neu.reviewerfinder.shared.model;

public interface User {
	public void getAuthorized(String username, String password);
	public Boolean getUserType();
}
