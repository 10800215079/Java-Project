package com.gov.Authmis.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gov.Authmis.entity.WhiteListSubauaForQueryBuilderEntity;
import com.gov.Authmis.jpa.WhiteListSubauaForQueryBuilderRepository;
import com.gov.Authmis.service.WhiteListSubauaForQueryBuilderService;

@Service
public class WhiteListSubauaForQueryBuilderServiceImpl implements WhiteListSubauaForQueryBuilderService {
	@Autowired
	private WhiteListSubauaForQueryBuilderRepository whiteListSubauaForQueryBuilderRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Object save(WhiteListSubauaForQueryBuilderEntity whiteListSubauaForQueryBuilderEntity, String ssoid) {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
		String executinDate = formatter.format(date);

		String query = "INSERT INTO AADHAAR.SSO_IP_WHITELIST( SSO_ID, IP, CREATED_DATE, CREATED_BY, AADHHAR_ID, TRANSACTION_ID, STATUS)"
				+ " VALUES ( :a, :b, :c, :d, :e, :f, :g)";

		Object o = entityManager.createNativeQuery(query)
				.setParameter("a", whiteListSubauaForQueryBuilderEntity.getSsoId())
				.setParameter("b", whiteListSubauaForQueryBuilderEntity.getIP()).setParameter("c", executinDate)
				.setParameter("d", ssoid).setParameter("e", whiteListSubauaForQueryBuilderEntity.getAadhaarId())
				.setParameter("f", whiteListSubauaForQueryBuilderEntity.getTRANSACTION_ID()).setParameter("g", 1)
				.executeUpdate();
		System.out.println("successfully save data !!!!!! ");
		return o;
	}

	@Override
	public HashMap<String, Object> getAllWhiteListSSO() {

		HashMap<String, Object> getData = new HashMap<>();
		try {
			String sql = "";
			sql = "SELECT SRNO,SSO_ID, IP, CREATED_DATE, CREATED_BY, AADHHAR_ID, TRANSACTION_ID, STATUS FROM AADHAAR.SSO_IP_WHITELIST";
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
				map.put("SRNO", array[0]);
				map.put("SSO_ID", array[1]);
				map.put("IP", array[2]);
				map.put("CREATED_DATE", array[3]);
				map.put("CREATED_BY", array[4]);
				map.put("AADHHAR_ID", array[5]);
				map.put("TRANSACTION_ID", array[6]);
				map.put("STATUS", array[7]);

				mapList.add(map);
			}
			if (mapList.size() > 0) {
				getData.put("details", mapList);
				System.out.println("");
				getData.put("dataIsNull", "false");
			} else {
				getData.put("dataIsNull", "true\t");
			}
			System.out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return getData;
	}

	@Override
	public void delete(Long SRNO) {
		// TODO Auto-generated method stub
		whiteListSubauaForQueryBuilderRepository.deleteById(SRNO);
	}

	@Transactional
	public void deleteBySrno(Long srno) {
		try {
			String queryString = "DELETE FROM SSO_IP_WHITELIST WHERE SRNO = :srno";
			int deletedCount = entityManager.createQuery(queryString).setParameter("srno", srno).executeUpdate();

			System.out.println("Deleted " + deletedCount + " record(s) successfully!");
		} catch (Exception e) {
			// Handle exceptions as needed
			e.printStackTrace();
		}
	}

//	@Override
//	@Transactional
//	public void updateStatus(Long srno, String status) {
//		String updateQuery ="";
//		if(status == "0") {
//			 updateQuery =" UPDATE AADHAAR.SSO_IP_WHITELIST SET STATUS =1 WHERE SRNO = " +srno+ " " ;			
//		}
//		else{
//				updateQuery =" UPDATE AADHAAR.SSO_IP_WHITELIST SET STATUS =0   WHERE SRNO = " +srno+ " " ;			
//			}
//		 entityManager.createQuery(updateQuery).executeUpdate();
//	}
//
//	
	@Autowired
	WhiteListSubauaForQueryBuilderRepository forQueryBuilderRepository;
	@Override
	@Transactional
	public void updateStatus(Long srno) {
		WhiteListSubauaForQueryBuilderEntity  builderEntity = forQueryBuilderRepository.findBySRNO(srno).orElse(null);
		
	    String updateQuery = "";
	    if (builderEntity.getSTATUS()== 0l) {
	    	builderEntity.setSTATUS(1l);
	    } else {
	    	builderEntity.setSTATUS(0l);
	    }
	    forQueryBuilderRepository.save(builderEntity);
	    System.out.println("Successfully updated !!");
	}


	/*
	 * public boolean isIPDuplicate(String IP) { // Implement your logic to check
	 * for duplicate IP addresses in the repository return
	 * whiteListSubauaForQueryBuilderRepository.existsByIP(IP); }
	 */
	public boolean isIPDuplicate(String IP) {
        // Implement your logic to check for duplicate IP addresses in the repository
        //return whiteListSubauaForQueryBuilderRepository.existsByIP(IP);
		 return whiteListSubauaForQueryBuilderRepository.existsByIPAndSTATUS(IP, 1L);
    }

}
