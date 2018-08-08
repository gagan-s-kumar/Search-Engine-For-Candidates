package com.neu.reviewerfinder.hibernate;

import org.hibernate.dialect.MySQLInnoDBDialect;
/**
 * @author DJ
 * This is the Hibernate Custom Dialect Class
 * Extends the InnoDB Dialect with utf8mb4 charset
 */
public class DBLPCustomDialect extends MySQLInnoDBDialect {
	
	//Sets the Table Type to the utf8mb4 charset
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
	}
}