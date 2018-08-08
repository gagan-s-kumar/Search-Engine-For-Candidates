package com.neu.reviewerfinder.backend;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
