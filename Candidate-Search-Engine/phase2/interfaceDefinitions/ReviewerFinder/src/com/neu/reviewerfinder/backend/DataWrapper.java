package com.neu.reviewerfinder.backend;
import java.util.List;

public interface DataWrapper {
	public DataWrapper getWrapper();
	public List<String> getTagIDs(Parser p);
	public List<String> getValueTags(Parser p);
	public void setTagIDs(String[] tagIds);
	public void setValueTagIDs(String[] valueIds);
}
