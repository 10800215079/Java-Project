package com.gov.Authmis.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gov.Authmis.entity.RequestForIpWhitelistEntity;
import com.gov.Authmis.entity.WhiteListSubAUAEntity;
import com.gov.Authmis.jpa.IPwhiteListRequestRepository;
import com.gov.Authmis.model.sendMailModel;
import com.gov.Authmis.service.IPwhiteListRequestService;
import com.gov.Authmis.service.WhiteListSubAUAIPAddressService;

@Service
public class IPwhiteListRequestServiceImpl implements IPwhiteListRequestService {

	@Autowired
	IPwhiteListRequestRepository iPwhiteListRequestRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	WhiteListSubAUAIPAddressService whiteListSubAUAIPAddresservice;
	
	@Override
	public List<RequestForIpWhitelistEntity> getAllRequestedIPList() {
		
		return iPwhiteListRequestRepository.findAll();
	}
	
	@Override
	public List<RequestForIpWhitelistEntity> findByStatusAndSubAUA(Long status, String subAUA) {
        return iPwhiteListRequestRepository.findByStatusAndSubAUACode(status, subAUA);
    }
	
	@Transactional
	@Override
	public Object updateRejectedRequest(Long id, String reason,String ssoId) {
		Optional<RequestForIpWhitelistEntity> entities = iPwhiteListRequestRepository.findById(id);
		RequestForIpWhitelistEntity newList = new RequestForIpWhitelistEntity();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		if(entities.isPresent()) {
			newList = entities.get();
			newList.setReason(reason);
			newList.setUpdatedBy(ssoId);
			newList.setUpdatedDate(date);
			newList.setStatus(3L);
		}
		return iPwhiteListRequestRepository.save(newList);
		
	}
	@Override
	public Optional<RequestForIpWhitelistEntity> getRequestToApprove(Long id) {
		
		Optional<RequestForIpWhitelistEntity> requestedList= iPwhiteListRequestRepository.findById(id);
		return requestedList;
	}
	
	@Transactional
	@Override
	public Object addWhitListIp(WhiteListSubAUAEntity entity ) {
		return whiteListSubAUAIPAddresservice.save(entity);
		
	}
	
	
	@Transactional
	@Override
	public Object updateApproveRequest(Long id,String ssoId) {
		Optional<RequestForIpWhitelistEntity> entities = iPwhiteListRequestRepository.findById(id);
		RequestForIpWhitelistEntity newList = new RequestForIpWhitelistEntity();
		Timestamp date = new Timestamp(System.currentTimeMillis());
		if(entities.isPresent()) {
			newList = entities.get();
			//newList.setReason(reason);
			newList.setStatus(1L);
			newList.setCurrentStatus(1L);
			newList.setUpdatedBy(ssoId);
			newList.setUpdatedDate(date);
		}
		return iPwhiteListRequestRepository.save(newList);
		
	}

	@Override
	public List<RequestForIpWhitelistEntity> getRequestedIPListByStatus(Long status) {
		return iPwhiteListRequestRepository.findByStatus(status);
	}
	
	@Override
	public List<RequestForIpWhitelistEntity> getRequestedIPListBySubAUACode(String subAUACode) {
		return iPwhiteListRequestRepository.findBysubAUACode(subAUACode);
	}
	
	
	@Transactional
	@Override
	public Object updateEmailStatus(List<sendMailModel> items,String ssoId) {
		for(sendMailModel mailModal : items ) {
			Optional<RequestForIpWhitelistEntity> entities = iPwhiteListRequestRepository.findById(mailModal.getSrNo());
			RequestForIpWhitelistEntity newList = new RequestForIpWhitelistEntity();
			Timestamp date = new Timestamp(System.currentTimeMillis());
			if(entities.isPresent()) {
				newList = entities.get();
				newList.setIsMailSent(1);
				newList.setUpdatedBy(ssoId);
				newList.setUpdatedDate(date);
			}
			 iPwhiteListRequestRepository.save(newList);
		}
		return 1;
		
	}
	
	

}
