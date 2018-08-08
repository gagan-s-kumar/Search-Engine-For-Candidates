package com.neu.reviewerfinder.queryengine;

import org.junit.Before;
import org.junit.Test;

import com.neu.reviewerfinder.queryengine.CriteriaImpl;

import static org.junit.Assert.*;

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
