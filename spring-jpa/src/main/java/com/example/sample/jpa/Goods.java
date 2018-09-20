package com.example.sample.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "goods")
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "goods_id")
	private Integer goodsId;
	
	@Column(name = "goods_name")
	private String goodsName;
	
	@ManyToOne
	@JoinColumn(name = "maker_id")
	private Maker maker;
}
