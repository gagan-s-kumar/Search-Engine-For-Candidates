package com.neu.reviewerfinder.hibernate.dao;

import java.util.List;

import com.neu.reviewerfinder.hibernate.conf.UserConf;
/**
 * @author DJ
 * This is the Hibernate DAO Interface for
 * User
 */
public interface UserDao {
	void createAndStoreUser(String username, String password);
	List<UserConf> getUsers();
	void storeUsers(List<UserConf> userConfList);
	boolean checkIfUserExists(String username,String password);
}
