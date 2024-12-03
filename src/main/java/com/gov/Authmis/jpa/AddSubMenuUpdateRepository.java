package com.gov.Authmis.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.AddSubMenuUpdateEntity;


@Repository
public interface AddSubMenuUpdateRepository extends JpaRepository<AddSubMenuUpdateEntity, Long>{

}
