package com.neu.reviewerfinder.backend;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.neu.reviewerfinder.backend.Parser;


public class ParserTest {
Parser parser;
	
	@Before
	public void Before() {
		
	}
	
//	@Test
//	public void testParserFalseFlag() throws SQLException {
//		parser = new Parser(null, null, false);
//		assertTrue(parser.getAuthorList().size() == 0);
//		HibernateSetup.getSessionFactory().close();
//	}
	
	@Test
	public void testParserTrueFlag() throws SQLException {
		String dblpUri = "testbed/dblp.xml";
		String committeeUri = "testbed/committees";
		parser = new Parser(dblpUri, committeeUri, true);
		System.out.println(parser.getAuthorList().size());
		assertTrue(parser.getAuthorList().size() ==  6);
	}

}
