package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.AuthorInfoConf;
import java.util.List;

public interface AuthorInfoDao {
  void createAndStoreAuthor(String name, String affiliation, String country, String dept, int year);

  void storeAuthors(List<AuthorInfoConf> authorInfoConfList);

  List<AuthorInfoConf> getAuthorsInfo();
}
