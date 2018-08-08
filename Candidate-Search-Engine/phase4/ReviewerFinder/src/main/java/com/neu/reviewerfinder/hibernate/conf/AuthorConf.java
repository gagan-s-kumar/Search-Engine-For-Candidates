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
 * @author DJ This is the Hibernate Mapping Class for Authors
 */

@Entity
@Table(name = "Author")
public class AuthorConf {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
  @GenericGenerator(name = "hilo_sequence_generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "hilo_sequence"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "100000"),
          @Parameter(name = "optimizer", value = "hilo")})
  @Id
  @Column(name = "author_id")
  private Long id;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "paper_key")
  private String paper_key;

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

  public String getPaper_key() {
    return paper_key;
  }

  public void setPaper_key(String paper_key) {
    this.paper_key = paper_key;
  }
}
