package com.neu.reviewerfinder.backend;
import java.util.List;

public interface Parser {
	public Parser getParser(DataWrapper wr);
	public List<DataWrapper> getWrappers();
}
