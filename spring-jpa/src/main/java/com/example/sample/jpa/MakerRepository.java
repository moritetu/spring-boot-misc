package com.example.sample.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Integer>, MakerRepositoryCustom {
	
	public List<Maker> findByMakerIdGreaterThan(Integer makerId);
	
	public List<Maker> findByMakerNameStartingWith(String makerName);
	
	@Query("SELECT r FROM Maker r WHERE r.makerId % 2 = 0")
	public List<Maker> findAllMakersByMakerIdEven();

	@Query(value = "SELECT * FROM makers r WHERE r.maker_id % 2 = 0", nativeQuery = true)
	public List<Maker> findAllMakersByMakerIdEvenNatively();

	@Query("SELECT r FROM Maker r ORDER BY r.makerId")
	public Page<Maker> findAllMakersWithPagination(Pageable pageable);

	@Query(value = "SELECT * FROM makers r ORDER BY r.maker_id",
			countQuery = "SELECT count(*) FROM makers" ,
			nativeQuery = true)
	public Page<Maker> findAllMakersWithPaginationNatively(Pageable pageable);
}
