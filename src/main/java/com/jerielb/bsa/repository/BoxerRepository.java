package com.jerielb.bsa.repository;

import com.jerielb.bsa.model.Boxer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxerRepository extends JpaRepository<Boxer, Long> {
	Boxer findById(long playerId);
	
	@Query(value = "select * from BOXER where FNC = 'Y'", nativeQuery = true)
	List<Boxer> findFNCRoster();
	
	@Query(value = "select * from BOXER where FNF = 'Y'", nativeQuery = true)
	List<Boxer> findFNFRoster();
	
	@Query(value = "select * from BOXER where ACTIVE = 'Y'", nativeQuery = true)
	List<Boxer> findCustomRoster();
	
	@Query(value = "select * from BOXER where ACTIVE = 'Y' and WEIGHTCLASS = ?1", nativeQuery = true)
	List<Boxer> findWeightClassBoxers(String weightclass);
}
