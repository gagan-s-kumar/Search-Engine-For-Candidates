package com.neu.reviewerfinder.queryengine;

import com.neu.reviewerfinder.hibernate.conf.AuthorConf;
import com.neu.reviewerfinder.hibernate.conf.AuthorInfoConf;
import com.neu.reviewerfinder.hibernate.conf.CitationConf;
import com.neu.reviewerfinder.hibernate.conf.CommitteeConf;
import com.neu.reviewerfinder.hibernate.conf.PaperConf;
import com.neu.reviewerfinder.hibernate.dao.AuthorDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.AuthorInfoDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.CitationDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.CommitteeDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.PaperDaoImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class implements the Query Engine interface
 * 
 * @author Surekha
 *
 */
public class QueryEngineImpl implements QueryEngine {

  AuthorDaoImpl authorDaoImpl;
  AuthorInfoDaoImpl authorInfoDaoImpl;
  PaperDaoImpl paperDaoImpl;
  CitationDaoImpl citationDaoImpl;
  Map<String, AuthorInfoConf> authorsInfo = new HashMap<String, AuthorInfoConf>();

  public QueryEngineImpl() {
    authorDaoImpl = new AuthorDaoImpl();
    authorInfoDaoImpl = new AuthorInfoDaoImpl();
    paperDaoImpl = new PaperDaoImpl();
    citationDaoImpl = new CitationDaoImpl();
    loadAuthorInfo();
  }

  private void loadAuthorInfo() {
    List<AuthorInfoConf> results = authorInfoDaoImpl.getAuthorsInfo();
    for (AuthorInfoConf a : results) {
      authorsInfo.put(a.getName(), a);
    }
  }

  public List<AuthorDetails> getAuthors(Criteria c) throws IllegalArgumentException {

    if (!isValidCriteria(c)) {
      // return new ArrayList<AuthorDetails>();
      throw new IllegalArgumentException();
    }

    List<AuthorDetails> authorsByPapers = new ArrayList<AuthorDetails>();
    List<AuthorDetails> authorsByName = new ArrayList<AuthorDetails>();

    if ((c.getPublishers() != null && !c.getPublishers().isEmpty())
        || c.getYearSince() != Integer.MAX_VALUE
        || (c.getTitleKeywords() != null && !c.getTitleKeywords().isEmpty())
        || c.getMinimumPapersPublished() != Integer.MAX_VALUE
        || c.getConsecutiveYearConstraint() != Integer.MAX_VALUE) {
      authorsByPapers = getAuthorsByPaperCriteria(c);

      if (c.getMinimumPapersPublished() != Integer.MAX_VALUE) {
        authorsByPapers =
            filterAuthorsByMinimumPaperCount(authorsByPapers, c.getMinimumPapersPublished());
      }

      if (c.getConsecutiveYearConstraint() != Integer.MAX_VALUE) {
        authorsByPapers = filterAuthorsByConsecutiveYearConstraint(authorsByPapers,
            c.getConsecutiveYearConstraint());
      }
    }

    if (c.getAuthorName() != null && !c.getAuthorName().isEmpty()) {
      authorsByName = getSimilarAuthorsByName(c.getAuthorName());
      authorsByName = removeSameAuthorName(authorsByName, c.getAuthorName());
    }

    List<AuthorDetails> result = getCommonAuthors(authorsByPapers, authorsByName);
    return populateAuthorInfo(result);
  }

  /**
   * Checks whether the correct parameters are set for the given criteria as per the query number
   * 
   * @param search criteria
   * @return boolean
   */
  public boolean isValidCriteria(Criteria c) {
    int queryNum = c.getQueryNum();

    if (queryNum == 1) {
      if (c.getPublishers() == null || c.getPublishers().isEmpty()
          || c.getYearSince() == Integer.MAX_VALUE || c.getAuthorName() != null
          || c.getConsecutiveYearConstraint() != Integer.MAX_VALUE
          || c.getMinimumPapersPublished() != Integer.MAX_VALUE || c.getTitleKeywords() != null) {
        return false;
      }
    }

    if (queryNum == 2) {
      if (c.getPublishers() == null || c.getPublishers().isEmpty()
          || c.getMinimumPapersPublished() == Integer.MAX_VALUE || c.getAuthorName() != null
          || c.getConsecutiveYearConstraint() == Integer.MAX_VALUE
          || c.getYearSince() != Integer.MAX_VALUE || c.getTitleKeywords() != null) {
        return false;
      }
    }

    if (queryNum == 3) {
      if (c.getAuthorName() == null || c.getAuthorName().isEmpty() || c.getPublishers() != null
          || c.getMinimumPapersPublished() != Integer.MAX_VALUE
          || c.getConsecutiveYearConstraint() != Integer.MAX_VALUE
          || c.getYearSince() != Integer.MAX_VALUE || c.getTitleKeywords() != null) {
        return false;
      }
    }

    if (queryNum == 4) {
      if (c.getTitleKeywords() == null || c.getTitleKeywords().isEmpty()
          || c.getPublishers() != null || c.getMinimumPapersPublished() != Integer.MAX_VALUE
          || c.getConsecutiveYearConstraint() != Integer.MAX_VALUE
          || c.getYearSince() != Integer.MAX_VALUE || c.getAuthorName() != null) {
        return false;
      }
    }

    if (queryNum == 5) {
      if ((c.getTitleKeywords() == null || c.getTitleKeywords().isEmpty())
          && (c.getPublishers() == null || c.getPublishers().isEmpty())
          && (c.getAuthorName() == null || c.getAuthorName().isEmpty())
          && c.getMinimumPapersPublished() == Integer.MAX_VALUE
          && c.getConsecutiveYearConstraint() == Integer.MAX_VALUE
          && c.getYearSince() == Integer.MAX_VALUE) {
        return false;
      }
    }

    if (queryNum < 1 || queryNum > 5) {
      return false;
    }
    return true;
  }

  /**
   * Retrieves authors based on paper keys
   * 
   * @param search criteria
   * @return list
   */
  private List<AuthorDetails> getAuthorsByPaperCriteria(Criteria c) {

    List<PaperConf> paperConfs = paperDaoImpl.getPapersByCriteria(c);
    List<String> paperKeys = new ArrayList<String>();
    for (PaperConf paperConf : paperConfs) {
      paperKeys.add(paperConf.getPaper_key());
    }
    // System.out.println(paperKeys.size());
    List<AuthorDetails> authors = new ArrayList<AuthorDetails>();
    if (paperKeys.size() > 0) {
      authors = getAuthorDetailsFromConf(authorDaoImpl.getAuthorsByPaperKeys(paperKeys));
    }
    return authors;
  }

  /**
   * Retrieves authors which are similar to given author. One author is similar to other, if one has
   * cited other's paper or vice-versa
   * 
   * @param authorName
   * @return list
   */
  private List<AuthorDetails> getSimilarAuthorsByName(String authorName) {
    List<AuthorConf> authorConfs = authorDaoImpl.getAuthorsByName(authorName);
    List<String> authoredPapers = new ArrayList<String>();
    for (AuthorConf authorConf : authorConfs) {
      authoredPapers.add(authorConf.getPaper_key());
    }

    if (authoredPapers.isEmpty()) {
      return new ArrayList<AuthorDetails>();
    }

    List<CitationConf> citationConfs = citationDaoImpl.getCitationsByPaperCitedKey(authoredPapers);
    List<CitationConf> citedConfs = citationDaoImpl.getCitationsByPaperCiteKey(authoredPapers);
    List<String> paperKeys = new ArrayList<String>();
    for (CitationConf citationConf : citationConfs) {
      paperKeys.add(citationConf.getPaper_cite_key());
    }

    for (CitationConf citedConf : citedConfs) {
      paperKeys.add(citedConf.getPaper_cited_key());
    }

    paperKeys.addAll(authoredPapers);
    return getAuthorDetailsFromConf(authorDaoImpl.getAuthorsByPaperKeys(paperKeys));
  }

  /**
   * Filter out authors which were committee members for given consecutive years
   * 
   * @param authors
   * @param consecutiveYearConstraint
   * @return list
   */
  private List<AuthorDetails> filterAuthorsByConsecutiveYearConstraint(List<AuthorDetails> authors,
      int consecutiveYearConstraint) {
    List<String> committeeMembers = getConsecutiveCommitteeMap(consecutiveYearConstraint);

    Iterator<AuthorDetails> it = authors.iterator();

    while (it.hasNext()) {
      AuthorDetails a = it.next();
      if (committeeMembers.contains(a.getAuthorName())) {
        it.remove();
      }
    }

    return authors;
  }

  /**
   * Filter out authors who have published less than given number of papers
   * 
   * @param authors
   * @param minimumPapersPublished
   * @return list
   */
  private List<AuthorDetails> filterAuthorsByMinimumPaperCount(List<AuthorDetails> authors,
      int minimumPapersPublished) {
    Iterator<AuthorDetails> it = authors.iterator();

    while (it.hasNext()) {
      AuthorDetails a = it.next();
      if (a.getPaperKeys() != null && a.getPaperKeys().size() < minimumPapersPublished) {
        it.remove();
      }
    }

    return authors;
  }

  /**
   * Convert Hibernate config for Author to final author details
   * 
   * @param authorConfs
   * @return list
   */
  private List<AuthorDetails> getAuthorDetailsFromConf(List<AuthorConf> authorConfs) {
    HashMap<String, AuthorDetails> authorMap = new HashMap<String, AuthorDetails>();

    for (AuthorConf authorConf : authorConfs) {
      String authorName = authorConf.getName();
      AuthorDetails a = new AuthorDetails();
      ArrayList<String> papers = new ArrayList<String>();
      int conferencePaperCount = 0;
      int journalPaperCount = 0;
      String currPaperKey = authorConf.getPaper_key();
      if (authorMap.containsKey(authorName)) {
        a = authorMap.get(authorName);
        papers = a.getPaperKeys();
        conferencePaperCount = a.getConferencePaperCount();
        journalPaperCount = a.getJournalPaperCount();
      } else {
        a.setAuthorId(authorConf.getId());
        a.setAuthorName(authorName);
      }
      papers.add(currPaperKey);
      a.setPaperKeys(papers);
      if (currPaperKey.startsWith("conf/")) {
        a.setConferencePaperCount(conferencePaperCount + 1);
      } else {
        a.setJournalPaperCount(journalPaperCount + 1);
      }

      authorMap.put(authorName, a);
    }

    return new ArrayList<AuthorDetails>(authorMap.values());
  }

  /**
   * Parse committee data to find authors and maximum consecutive years they were part of the same
   * committee
   * 
   * @param consecutiveYearCount
   * @return list
   */
  private List<String> getConsecutiveCommitteeMap(int consecutiveYearCount) {
    CommitteeDaoImpl committeeDaoImpl = new CommitteeDaoImpl();
    List<CommitteeConf> committeeConfs = committeeDaoImpl.getOrderedCommittees();
    HashMap<String, Integer> committeeMap = new HashMap<String, Integer>();
    String previousKey = "";
    int previousYear = 0;
    int previousConsecutiveCount = 0;
    int newConsecutiveCount = 0;

    for (CommitteeConf committeeConf : committeeConfs) {
      String key = committeeConf.getName().trim() + "|" + committeeConf.getConference().trim();
      if (previousKey.equals(key) && (previousYear + 1) == committeeConf.getYear()) {
        newConsecutiveCount = previousConsecutiveCount + 1;
        if (committeeMap.containsKey(key) && committeeMap.get(key) < newConsecutiveCount) {
          committeeMap.put(key, newConsecutiveCount);
        }
      } else {
        newConsecutiveCount = 1;
        if (!committeeMap.containsKey(key)) {
          committeeMap.put(key, newConsecutiveCount);
          previousKey = key;
        }
      }
      previousYear = committeeConf.getYear();
      previousConsecutiveCount = newConsecutiveCount;
    }

    HashMap<String, Integer> reviewerMap = new HashMap<String, Integer>();
    Iterator<Entry<String, Integer>> it = committeeMap.entrySet().iterator();
    while (it.hasNext()) {
      Entry<String, Integer> e = (Entry<String, Integer>) it.next();
      String authorName = e.getKey().split("\\|")[0];
      int count = 0;
      if (reviewerMap.containsKey(authorName)) {
        count = reviewerMap.get(authorName);
      }
      reviewerMap.put(authorName, count + e.getValue());
    }

    Iterator<Entry<String, Integer>> i = reviewerMap.entrySet().iterator();
    while (i.hasNext()) {
      Entry<String, Integer> e = (Entry<String, Integer>) i.next();
      if (e.getValue() < consecutiveYearCount) {
        i.remove();
      }
    }
    return new ArrayList<String>(reviewerMap.keySet());
  }

  /**
   * Find common authors by papers and similarity
   * 
   * @param authorsByPapers
   * @param authorsByName
   * @return list
   */
  private List<AuthorDetails> getCommonAuthors(List<AuthorDetails> authorsByPapers,
      List<AuthorDetails> authorsByName) {
    List<AuthorDetails> authors = new ArrayList<AuthorDetails>();

    if (!(authorsByName.isEmpty() || authorsByPapers.isEmpty())) {
      for (AuthorDetails author : authorsByPapers) {
        if (authorsByName.contains(author)) {
          authors.add(author);
        }
      }
      return authors;
    }

    authors.addAll(authorsByName);
    authors.addAll(authorsByPapers);
    return authors;
  }

  private List<AuthorDetails> removeSameAuthorName(List<AuthorDetails> authorsByName, String name) {
    Iterator<AuthorDetails> it = authorsByName.iterator();

    while (it.hasNext()) {
      AuthorDetails a = it.next();
      if (a.getAuthorName().equals(name)) {
        it.remove();
      }
    }
    return authorsByName;
  }

  private List<AuthorDetails> populateAuthorInfo(List<AuthorDetails> authors) {
    if (authorsInfo.isEmpty())
      return authors;

    for (AuthorDetails a : authors) {
      if (authorsInfo.containsKey(a.getAuthorName())) {
        AuthorInfoConf authorInfoConf = authorsInfo.get(a.getAuthorName());
        a.setAffiliation(authorInfoConf.getAffiliation());
        a.setCountry(authorInfoConf.getCountry());
        a.setDept(authorInfoConf.getDept());
        // System.out.println(a.getAuthorName());
      }
    }
    return authors;
  }
}
