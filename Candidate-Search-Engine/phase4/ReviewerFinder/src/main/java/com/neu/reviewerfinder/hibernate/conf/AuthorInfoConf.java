package com.neu.reviewerfinder.hibernate.conf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "AuthorInfo")
public class AuthorInfoConf {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
  @GenericGenerator(name = "hilo_sequence_generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "hilo_sequence"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "100000"),
          @Parameter(name = "optimizer", value = "hilo")})
  @Id
  @Column(name = "authorinfo_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "affiliation")
  private String affiliation;

  @Column(name = "country")
  private String country;

  @Column(name = "dept")
  private String dept;

  @Column(name = "year")
  private int year;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getDept() {
    return dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}
