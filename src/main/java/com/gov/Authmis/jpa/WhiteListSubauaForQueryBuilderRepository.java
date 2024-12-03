package com.gov.Authmis.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;

@Repository
public interface WhiteListSubauaForQueryBuilderRepository extends JpaRepository<WhiteListSubauaForQueryBuilderEntity, Long> {


	Optional<WhiteListSubauaForQueryBuilderEntity>  findBySRNO(Long srno);

	boolean existsByIPAndSTATUS(String iP, long l);
	
	

}
