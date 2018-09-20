package com.example.sample.jpa;


import java.util.ArrayList;
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
import org.springframework.data.domain.PageRequest;


@SpringBootApplication
public class SpringJpaApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringJpaApplication.class);

	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	GoodsService goodsService;
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
		Maker mb = new Maker(2, "Maker B");
		mb.setGoods(new ArrayList<>());
		makerRepository.save(mb);

		Maker mc = new Maker(3, "Maker C");
		mc.setGoods(new ArrayList<>());
		makerRepository.save(mc);
		
		Maker md = new Maker(4, "Maker D");
		md.setGoods(new ArrayList<>());
		makerRepository.save(md);
		makerRepository.findAll().forEach((maker) -> {
			log.info("maker_id = {}", maker.getMakerId());
			Optional.ofNullable(maker.getGoods()).ifPresent(list -> {
				list.forEach((item) -> log.info("  GoodsName: {}", item.getGoodsName()));
			});
		});
		
		Optional<Maker> optMaker = makerRepository.findById(2);
		optMaker.get().getGoods().add(new Goods(2, "Pencil", optMaker.get()));
		optMaker.get().getGoods().add(new Goods(3, "Car", optMaker.get()));
		makerRepository.saveAndFlush(optMaker.get());
		
		goodsRepository.findAll().forEach((item) -> log.info(item.getGoodsName()));
		makerRepository.findAll().forEach((maker) -> log.info("makerName = {}, lastModifiedBy = {}",
				maker.getMakerName(), maker.getLastModifiedBy()));

		makerRepository.delete(optMaker.get());
		
		//
		// JPA Naming Rule Test
		//
		
		// GreaterThan
		log.info("findByMakerIdGreaterThan...");
		makerRepository.findByMakerIdGreaterThan(1).forEach((maker) -> log.info(maker.getMakerName()));

		// Like
		log.info("findByMakerNameStartingWith...");
		makerRepository.findByMakerNameStartingWith("Maker").forEach((maker) -> log.info(maker.getMakerName()));
		
		//
		// Sample with EntityManager
		log.info("findGoodsByName...");
		Optional.ofNullable(goodsService.findGoodsByName("Phone")).ifPresent(list -> {
			list.forEach((item) -> log.info("GoodsName: {}", item.getGoodsName()));
		});
		
		//
		// JPQL
		//
		log.info("findAllMakersByMakerIdEven...");
		Optional.ofNullable(makerRepository.findAllMakersByMakerIdEven()).ifPresent(list -> {
			list.forEach((maker) -> log.info("MakerId: {}", maker.getMakerId()));
		});

		//
		// Native
		//
		log.info("findAllMakersByMakerIdEvenNatively...");
		Optional.ofNullable(makerRepository.findAllMakersByMakerIdEvenNatively()).ifPresent(list -> {
			list.forEach((maker) -> log.info("MakerId: {}", maker.getMakerId()));
		});

		//
		// Pagination
		//
		log.info("findAllMakersWithPagination...");
		Optional.ofNullable(makerRepository.findAllMakersWithPagination(PageRequest.of(0, 2))).ifPresent(list -> {
			list.forEach((maker) -> log.info("MakerId: {}", maker.getMakerId()));
		});

		log.info("findAllMakersWithPagination...");
		Optional.ofNullable(makerRepository.findAllMakersWithPagination(PageRequest.of(1, 2))).ifPresent(list -> {
			list.forEach((maker) -> log.info("MakerId: {}", maker.getMakerId()));
		});

		log.info("findAllMakersWithPaginationNatively...");
		Optional.ofNullable(makerRepository.findAllMakersWithPaginationNatively(PageRequest.of(1, 2))).ifPresent(list -> {
			list.forEach((maker) -> log.info("MakerId: {}", maker.getMakerId()));
		});

		//
		// Criteria
		//
		log.info("countAll...");
		log.info("make count = {}", makerRepository.countAll());
	}
	
}
