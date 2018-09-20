package com.example.sample.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	GoodsRepository repository;
	
	@Transactional(readOnly = true)
	public List<Goods> findGoodsByName(String goodsName) {
		String jpgl = "select r from Goods r where r.goodsName = :goodsName";
		TypedQuery<Goods> query = entityManager.createQuery(jpgl, Goods.class);
		query.setParameter("goodsName", goodsName);
		return query.getResultList();
	}
}
