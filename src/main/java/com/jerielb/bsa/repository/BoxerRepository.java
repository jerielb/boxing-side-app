package com.jerielb.bsa.repository;

import com.jerielb.bsa.model.Boxer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxerRepository extends JpaRepository<Boxer, Long> {
	Boxer findById(long playerId);
}
