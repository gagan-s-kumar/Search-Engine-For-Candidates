package com.neu.reviewerfinder.backend;
import java.nio.file.Path;

public interface RawFile {
	public String getFileName();
	public Path getPath(String fileName);
	public String getSize(String fileName);
}
