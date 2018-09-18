package com.example.sample.jpa;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringJpaApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringJpaApplication.class);

	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	MakerRepository makerRepository;
	@PersistenceContext
	EntityManager entityManager;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJpaApplication.class, args);
	}

	// This method is called in callRunners() in spring boot start-up processing.
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		// Do not call toString(), because it causes circular reference.
		goodsRepository.findAll().forEach((item) -> log.info(item.getGoodsName()));
		makerRepository.findAll().forEach((maker) -> log.info(maker.getMakerName()));

		// Save a maker
		makerRepository.save(new Maker(2, "Maker B", null));
		makerRepository.findAll().forEach((maker) -> {
			log.info("maker_id = {}", maker.getMakerId());
			Optional.ofNullable(maker.getGoods()).ifPresent(list -> {
				list.forEach((item) -> log.info("  GoodsName: {}", item.getGoodsName()));
			});
		});
		
		Optional<Maker> optMaker = makerRepository.findById(2);

		// save some goods
		goodsRepository.save(new Goods(2, "Pencil", optMaker.get()));
		goodsRepository.save(new Goods(3, "Car", optMaker.get()));
		
		goodsRepository.findAll().forEach((item) -> log.info(item.getGoodsName()));
		makerRepository.findAll().forEach((maker) -> log.info(maker.getMakerName()));
	}
}
