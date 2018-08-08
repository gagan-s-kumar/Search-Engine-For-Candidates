package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.UserConf;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for User
 */
public interface UserDao {
  void createAndStoreUser(String username, String password);

  List<UserConf> getUsers();

  void storeUsers(List<UserConf> userConfList);

  boolean checkIfUserExists(String username, String password);
}
