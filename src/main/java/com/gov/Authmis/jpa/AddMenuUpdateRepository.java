package com.gov.Authmis.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.AddMenuUpdateEntity;



@Repository
public interface AddMenuUpdateRepository extends JpaRepository<AddMenuUpdateEntity, Long> {

	

	

}
