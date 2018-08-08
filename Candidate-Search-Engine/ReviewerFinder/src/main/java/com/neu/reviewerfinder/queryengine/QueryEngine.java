package com.neu.reviewerfinder.queryengine;

import java.util.List;

/**
 * Intermediate Interface which connects backend and frontend
 * 
 * @author Surekha
 *
 */
public interface QueryEngine {
  /**
   * Retrieves the list of authors based on the input search criteria
   * 
   * @param search criteria
   * @return list
   */
  List<AuthorDetails> getAuthors(Criteria c);
}
