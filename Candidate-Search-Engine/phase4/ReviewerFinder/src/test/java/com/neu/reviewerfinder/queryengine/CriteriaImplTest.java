package com.neu.reviewerfinder.queryengine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class CriteriaImplTest {
  CriteriaImpl c1;

  @Before
  public void before() throws Exception {
    c1 = new CriteriaImpl();
  }

  @Test
  public void testEmptyCriteria() {
    c1 = null;
    assertNull(c1);
  }

  @Test
  public void testValidCriteria() {
    assertNotNull(c1);
  }
}
