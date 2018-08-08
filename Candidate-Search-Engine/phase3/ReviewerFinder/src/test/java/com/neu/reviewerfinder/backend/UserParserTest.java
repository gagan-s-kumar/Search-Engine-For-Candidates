package com.neu.reviewerfinder.backend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.neu.reviewerfinder.backend.UserParser;

public class UserParserTest {
	UserParser userParser;
	
	@Before
	public void before() {
		userParser = new UserParser("testbed/users.txt");
	}
	
	@Test
	public void checkUserCount() {
		assertTrue(userParser.getUserList().size() == 5);
	}
}
