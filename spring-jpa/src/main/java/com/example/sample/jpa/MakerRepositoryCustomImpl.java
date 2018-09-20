package com.example.sample.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MakerRepositoryCustomImpl implements MakerRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public Long countAll() {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> query = builder.createQuery(Long.class);
		final Root<Maker> root = query.from(Maker.class);
		query.select(builder.count(root));
		return entityManager.createQuery(query).getSingleResult();
	}
}
