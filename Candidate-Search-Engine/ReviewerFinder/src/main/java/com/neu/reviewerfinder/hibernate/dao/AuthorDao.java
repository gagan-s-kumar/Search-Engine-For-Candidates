package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.AuthorConf;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for Author
 */
public interface AuthorDao {
  void createAndStoreAuthor(String name, String key);

  void storeAuthors(List<AuthorConf> authorConfList);

  List<AuthorConf> getAuthorsByPaperKeys(List<String> paperKeys);

  List<AuthorConf> getAuthorsByName(String name);
}
