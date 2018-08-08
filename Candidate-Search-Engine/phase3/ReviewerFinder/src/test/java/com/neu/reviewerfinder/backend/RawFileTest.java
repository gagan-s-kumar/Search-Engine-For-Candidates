package com.neu.reviewerfinder.backend;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class RawFileTest {

	@Test
	public void checkIfFileExists() {
		File file = new File("testbed/users.txt");
		assertTrue(file.exists());
	}
	
	@Test
	public void checkIfFileDoesNotExist() {
		File file = new File("file1");
		assertTrue(!file.exists());
	}
	
	@Test
	public void checkIfInputDirExists() {
		File file = new File("testbed");
		assertTrue(file.exists());
	}
	
	@Test
	public void checkIfInputDirDoesNotExist() {
		File file = new File("lib1");
		assertTrue(!file.exists());
	}
}
