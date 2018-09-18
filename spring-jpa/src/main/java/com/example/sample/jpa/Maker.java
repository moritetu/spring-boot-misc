package com.example.sample.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "makers")
@AllArgsConstructor
@NoArgsConstructor
public class Maker implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "maker_id")
	private Integer makerId;
	
	@Column(name = "maker_name")
	private String makerName;

	@OneToMany(mappedBy = "maker", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Goods> goods;
}

