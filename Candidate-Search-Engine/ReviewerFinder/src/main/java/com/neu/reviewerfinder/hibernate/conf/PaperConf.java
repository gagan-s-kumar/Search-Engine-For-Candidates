package com.neu.reviewerfinder.hibernate.conf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author DJ This is the Hibernate Mapping Class for Papers
 */
@Entity
@Table(name = "Paper")
public class PaperConf {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
  @GenericGenerator(name = "hilo_sequence_generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "hilo_sequence"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "100000"),
          @Parameter(name = "optimizer", value = "hilo")})
  @Column(name = "paper_id")
  private Long id;

  @Column(name = "title", length = 100000)
  private String title;

  @Column(name = "year")
  private Integer year;

  @Column(name = "paper_key")
  private String paper_key;

  @Column(name = "publisher")
  private String publisher;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPaper_key() {
    return paper_key;
  }

  public void setPaper_key(String paper_key) {
    this.paper_key = paper_key;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

}
