package com.neu.reviewerfinder.queryengine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.neu.reviewerfinder.queryengine.Author;
import com.neu.reviewerfinder.queryengine.AuthorDetails;
import com.neu.reviewerfinder.queryengine.CriteriaImpl;
import com.neu.reviewerfinder.queryengine.QueryEngineImpl;

import static org.junit.Assert.*;

public class QueryEngineImplTest {
	QueryEngineImpl qe;
	CriteriaImpl c1;
	List<Author> validAuthors;
	List<Author> emptyAuthors;
	ArrayList<String> conf;
	ArrayList<String> keywords;
	
	@Before 
	public void before() throws Exception {
		qe = new QueryEngineImpl();
		c1 = new CriteriaImpl();
		
		AuthorDetails a1 = new AuthorDetails("Lutz Michael Wegner", (long)123);
		AuthorDetails a2 = new AuthorDetails("Michael A. Palley", (long)124);
		
		validAuthors = new ArrayList<Author>();
		validAuthors.add(a1);
		validAuthors.add(a2);
		
		emptyAuthors = null;
		
		conf = new ArrayList<String>();
		conf.add("OOSLA");
		conf.add("ECOOP");
		
		keywords = new ArrayList<String>();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCriteriaQuery1() {
		c1.setQueryNum(1);
		c1.setPublishers(conf);
		c1.setAuthorName("Michael");
		qe.getAuthors(c1);
	}
	
	@Test()
	public void testValidAuthorListQuery1() {
		c1.setQueryNum(1);
		c1.setPublishers(conf);
		c1.setYearSince(2010);
		assertTrue(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testEmptyAuthorListQuery1() {
		c1.setQueryNum(1);
		c1.setPublishers(conf);
		c1.setYearSince(2017);
		assertFalse(qe.getAuthors(c1).size() > 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCriteriaQuery2() {
		c1.setQueryNum(2);
		c1.setPublishers(conf);
		c1.setConsecutiveYearConstraint(2);
		c1.setAuthorName("Michael");
		qe.getAuthors(c1);
	}
	
	@Test()
	public void testValidAuthorListQuery2() {
		c1.setQueryNum(2);
		c1.setPublishers(conf);
		c1.setMinimumPapersPublished(2);
		c1.setConsecutiveYearConstraint(2);
		assertTrue(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testEmptyAuthorListQuery2() {
		c1.setQueryNum(2);
		c1.setPublishers(conf);
		c1.setMinimumPapersPublished(500);
		c1.setConsecutiveYearConstraint(50);
		assertFalse(qe.getAuthors(c1).size() > 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCriteriaQuery3() {
		c1.setQueryNum(3);
		c1.setPublishers(conf);
		qe.getAuthors(c1);
	}
	
	@Test()
	public void testValidAuthorListQuery3() {
		c1.setQueryNum(3);
		c1.setAuthorName("Michael");
		assertTrue(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testEmptyAuthorListQuery3() {
		c1.setQueryNum(3);
		c1.setAuthorName("&^$@#");
		assertFalse(qe.getAuthors(c1).size() > 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCriteriaQuery4() {
		c1.setQueryNum(4);
		c1.setTitleKeywords(keywords);
		qe.getAuthors(c1);
	}
	
	@Test()
	public void testValidAuthorListQuery4() {
		c1.setQueryNum(4);
		keywords.add("pointer");
		keywords.add("analysis");
		c1.setTitleKeywords(keywords);
		assertTrue(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testEmptyAuthorListQuery4() {
		c1.setQueryNum(4);
		keywords.add("abcdefgh");
		c1.setTitleKeywords(keywords);
		assertFalse(qe.getAuthors(c1).size() > 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCriteriaQuery5() {
		c1.setQueryNum(5);
		qe.getAuthors(c1);
	}
	
	@Test()
	public void testValidAuthorListQuery5() {
		c1.setQueryNum(5);
		c1.setPublishers(conf);
		c1.setAuthorName("Frank Tip");
		assertTrue(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testEmptyAuthorListQuery5() {
		c1.setQueryNum(5);
		c1.setMinimumPapersPublished(400);
		c1.setPublishers(conf);
		c1.setConsecutiveYearConstraint(10);
		assertFalse(qe.getAuthors(c1).size() > 0);
	}
	
	@Test()
	public void testCriteriaFunction(){
		assertEquals(c1.getCriteria(),c1);
	}
	
	@Test()
	public void testGetAuthorFunction(){
		AuthorDetails author = new AuthorDetails();
		author.setAuthorId(-1l);
		author.setAuthorName("Demo Author");
		assertTrue(author.getAuthorId()<0);
	}
	@Test()
	public void testGetAuthorToStringFunction(){
		AuthorDetails author = new AuthorDetails();
		author.setAuthorId(-1l);
		author.setAuthorName("Demo Author");
		author.setConferencePaperCount(1);
		author.setJournalPaperCount(1);
		ArrayList<String> listDum = new ArrayList<>();
		listDum.add("DummyKey");
		author.setPaperKeys(listDum);
		AuthorDetails obj = author.getAuthor("Demo Author");
		String authStr = String.format("ID: " + obj.getAuthorId() +
        		" Name: " + author.getAuthorName() + 
        		" Paper Keys: " + String.join(", ", author.getPaperKeys()) + 
        		" Conference Papers Published: " + author.getConferencePaperCount() +
        		" Journal Papers Published: " + author.getJournalPaperCount());;
		assertTrue(author.toString().equals(authStr));
	}
	
	@Test()
	public void testGetInValidAuthor(){
		AuthorDetails author = new AuthorDetails();
		author.setAuthorName("Demo");
		AuthorDetails authObj = author.getAuthor("NotDemo");
		assertEquals(authObj,null);
		assertFalse(author.equals(new String()));
	}
	
	@Test()
	public void isValidateCriteria(){
		c1.setQueryNum(0);
		assertFalse(qe.isValidCriteria(c1));
	}
}
