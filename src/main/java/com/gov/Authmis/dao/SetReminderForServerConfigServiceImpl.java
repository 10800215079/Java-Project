package com.gov.Authmis.dao;


import java.sql.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gov.Authmis.model.SetReminderForServerConfigModel;
import com.gov.Authmis.service.SetReminderForServerConfigService;

@Service
public class SetReminderForServerConfigServiceImpl implements SetReminderForServerConfigService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<String> GetProjectName() {
		String sql = "SELECT PROJECT_NAME FROM PROJECT_DETAILS WHERE STATUS = 1";
		Query query = this.em.createNativeQuery(sql);

		@SuppressWarnings("unchecked") // Suppress warning for this specific case
		List<String> projectList = query.getResultList();

		return projectList;
	}

	@Transactional
	@Override
	public Object save(SetReminderForServerConfigModel setRemainderForServerConfigModel) {
		String mailCc = setRemainderForServerConfigModel.getMAIL_CC();
		if(mailCc.isEmpty()) {
			mailCc = "null";
		}
		String query = "INSERT INTO AADHAAR.REMAINDER_DETAILS (PROJECT_NAME, PERFORM_ACTIVITY, REMAINDER_DATE, GET_REMAINDER,  CREATED_DATE, CREATED_BY, IP, STATUS, MAIL_TO, MAIL_CC)"
				+ " VALUES (:a, :b, :c, :d, :e, :f, :g, :h, :i, :j)";

		Object o = em.createNativeQuery(query).setParameter("a", setRemainderForServerConfigModel.getPROJECT_NAME())
				.setParameter("b", setRemainderForServerConfigModel.getPERFORM_ACTIVITY())
				.setParameter("c", setRemainderForServerConfigModel.getREMAINDER_DATE())
				.setParameter("d", setRemainderForServerConfigModel.getGET_REMAINDER())
				.setParameter("e", Date.valueOf(LocalDate.now()))
				.setParameter("f", setRemainderForServerConfigModel.getCREATED_BY())
				.setParameter("g", setRemainderForServerConfigModel.getIP())
				.setParameter("h", 1)
				.setParameter("i", setRemainderForServerConfigModel.getMAIL_TO())
				.setParameter("j", setRemainderForServerConfigModel.getMAIL_CC())
				.executeUpdate();

		return o;
	}

	@Override
	public HashMap<String, Object> getAllRemainders() {
		HashMap<String, Object> getData = new HashMap<>();
		try {
			String sql = "";
			sql = "SELECT SRNO, PROJECT_NAME, PERFORM_ACTIVITY, REMAINDER_DATE, CREATED_DATE, CREATED_BY, IP, STATUS, GET_REMAINDER, MAIL_TO, MAIL_CC FROM AADHAAR.REMAINDER_DETAILS ORDER BY CREATED_DATE DESC";
			Query query = this.em.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
				map.put("SRNO", array[0]);
				map.put("PROJECT_NAME", array[1]);
				map.put("PERFORM_ACTIVITY", array[2]);
				map.put("REMAINDER_DATE", array[3]);
				map.put("CREATED_DATE", array[4]);
				map.put("CREATED_BY", array[5]);
				map.put("IP", array[6]);
				map.put("STATUS", array[7]);
				map.put("GET_REMAINDER", array[8]);
				map.put("MAIL_TO", array[9]);
				map.put("MAIL_CC", array[10]);

				mapList.add(map);
			}
			if (mapList.size() > 0) {
				getData.put("remainders", mapList);
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

	@Transactional
	@Override
	public void deactivateRemainder(Long SRNO, Long status) {
		String updateStatusQuery = "UPDATE AADHAAR.REMAINDER_DETAILS SET STATUS = :status WHERE SRNO = :srno";

		em.createNativeQuery(updateStatusQuery).setParameter("status", status == 1 ? 0 : 1).setParameter("srno", SRNO)
				.executeUpdate();
	}

	@Transactional
	@Override
	public void deactivateRemainderWithReason(Long srno, String reason, String sso) {
	    String updateStatusQuery = "UPDATE AADHAAR.REMAINDER_DETAILS "
	            + "SET DEACTIVATED_BY = :sso, "
	            + "STATUS = 0, "
	            + "REASON_OF_DEACTIVATION = :reason, "
	            + "DEACTIVATED_DATE = TO_DATE(:deactivationDate, 'DD-MON-YY') "
	            + "WHERE SRNO = :srno";

	   Query deactivate = this.em.createNativeQuery(updateStatusQuery);
			   deactivate.setParameter("sso", sso);
			   deactivate.setParameter("reason", reason);
			   deactivate.setParameter("deactivationDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
			   deactivate.setParameter("srno", srno);
	   int noOfUpdatedRow = deactivate.executeUpdate();
	    
	   System.out.println(noOfUpdatedRow);
			
	}



}
