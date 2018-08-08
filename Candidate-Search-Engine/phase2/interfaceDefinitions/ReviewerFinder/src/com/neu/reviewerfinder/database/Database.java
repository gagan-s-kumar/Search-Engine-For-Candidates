package com.neu.reviewerfinder.database;
import java.util.List;

public interface Database {
	public Database getDatabase(String dbName);
	public List<String> getTables(String dbName);
}
