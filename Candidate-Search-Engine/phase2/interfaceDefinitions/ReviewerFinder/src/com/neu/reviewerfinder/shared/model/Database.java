package com.neu.reviewerfinder.shared.model;
import java.util.List;

public interface Database {
	public Database getDatabase(String dbName);
	public List<String> getTables(String dbName);
}
