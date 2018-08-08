package com.neu.reviewerfinder.ui;

public interface UI<JSON> {
	public void sendRequest(JSON json);
	public JSON getResponse();
}
