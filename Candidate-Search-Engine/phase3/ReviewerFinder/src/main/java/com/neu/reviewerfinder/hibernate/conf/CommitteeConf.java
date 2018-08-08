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
 * @author DJ
 * This is the Hibernate Mapping Class for
 * Committees
 */
@Entity
@Table(name="Committee")
public class CommitteeConf {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
    @GenericGenerator(
            name = "hilo_sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "hilo_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1000000"),
                    @Parameter(name = "optimizer", value = "hilo")
            })
	@Id
	@Column(name="committee_id")
	private Long id;
	
	@Column(name="conference")
	private String conference;
	
	@Column(name="name")
	private String name;

	@Column(name="year")
	private Integer year;
	
	@Column(name="G_flg")
	private Boolean G_flag;
	

	@Column(name="P_flg")
	private Boolean P_flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getP_flag() {
		return P_flag;
	}

	public void setP_flag(Boolean p_flag) {
		P_flag = p_flag;
	}


	public Boolean getG_flag() {
		return G_flag;
	}

	public void setG_flag(Boolean G_flag) {
		this.G_flag = G_flag;
	}	
}

