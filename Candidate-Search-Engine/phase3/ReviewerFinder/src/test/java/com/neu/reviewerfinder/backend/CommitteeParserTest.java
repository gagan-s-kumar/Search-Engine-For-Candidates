package com.neu.reviewerfinder.backend;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import com.neu.reviewerfinder.backend.CommitteeParser;

public class CommitteeParserTest {
	CommitteeParser parser;
	
	@Before
	public void before() {
		parser = new CommitteeParser("testbed/committees");
	}

	@Test
	public void numberOfRecordsCheck() {
    	assertTrue(parser.getCommitteeList().size() == 180);
	}
}
