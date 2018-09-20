package com.example.sample.jpa;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "makers")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Maker implements Serializable {

	private static final long serialVersionUID = 1L;

	public Maker(Integer makerId, String makerName) {
		this.makerId = makerId;
		this.makerName = makerName;
	}
	@Id
	@Column(name = "maker_id")
	private Integer makerId;
	
	@Column(name = "maker_name")
	private String makerName;

	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@CreatedDate
	@Column(name = "created_date")
	private LocalTime createdOn;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	private LocalTime lastModifiedDate;

	@OneToMany(mappedBy = "maker", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Goods> goods = new ArrayList<>();
}

