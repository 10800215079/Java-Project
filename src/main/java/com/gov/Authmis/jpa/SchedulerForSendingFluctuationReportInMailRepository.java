package com.gov.Authmis.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gov.Authmis.entity.ViewShareLicenceKeyDetails;


@Repository
public interface SchedulerForSendingFluctuationReportInMailRepository extends JpaRepository<ViewShareLicenceKeyDetails, Long> {

	List<ViewShareLicenceKeyDetails> findByActive(int i);

}
