package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.CommitteeConf;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for Committee
 */
public interface CommitteeDao {
  void createAndStoreCommittee(String conference, String name, Integer year, Boolean G_flag,
      Boolean P_flag);

  List<CommitteeConf> getOrderedCommittees();

  void storeCommittees(List<CommitteeConf> committeeConfList);
}
