package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gov.Authmis.entity.AddMenuUpdateEntity;
import com.gov.Authmis.jpa.AddMenuUpdateRepository;
import com.gov.Authmis.model.Menu;
import com.gov.Authmis.model.ResultSetToListConverter;

@Service
public class MenuService {
	@Autowired
	private AddMenuUpdateRepository addMenuUpdateRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Object save(Menu menu, String ssoid) throws ParseException {

		Object s = null;

		// Insert a new record into TBLMENUMASTER
		String insertQuery = "INSERT INTO AADHAAR.TBLMENUMASTER(MENUNAME,MENUDESC,URL_REPO,PRIORITY_TYPE,STATUS,CREATIONDATE,CREATIONBY,MTYPID,ISNEWTAB) VALUES (:a, :b, :c, :d, :e, :f, :g,:h,:i)";

		s = entityManager.createNativeQuery(insertQuery).setParameter("a", menu.getMENUNAME())
				.setParameter("b", menu.getMENUDESC()).setParameter("c", "#").setParameter("d", 1)
				.setParameter("e", 1).setParameter("f", java.time.LocalDate.now()).setParameter("g", ssoid)
				.setParameter("h", 1).setParameter("i", 1).executeUpdate();

		return s;
	}

	public List<Map<String, Object>> GetRoleAndName() {
		StoredProcedureQuery query1 = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_MIS_ROLES_AND_NAME")
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs2 = (ResultSet) query1.getOutputParameterValue("prc");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs2);
		return subauaList;
		
	}

	public HashMap<String, Object> getAllMainMenu() {
		HashMap<String, Object> getData = new HashMap<>();
		try {
			String sql = "";			
			sql = "SELECT MENUID, MENUNAME, MENUDESC, URL_REPO, PRIORITY_TYPE,STATUS,CREATIONDATE,CREATIONBY,MTYPID,ISNEWTAB FROM AADHAAR.TBLMENUMASTER";
			Query query = this.entityManager.createNativeQuery(sql);
			@SuppressWarnings("unchecked")
			List<Object> data = query.getResultList();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (Object innerList : data) {
				Object[] array = (Object[]) innerList;
				Map<String, Object> map = new HashMap<>();
				map.put("MENUID", array[0]);
				map.put("MENUNAME", array[1]);
				map.put("MENUDESC", array[2]);
				map.put("URL_REPO", array[3]);
				map.put("PRIORITY_TYPE", array[4]);
				map.put("STATUS", array[5]);
				map.put("CREATIONDATE", array[6]);	
				map.put("CREATIONBY", array[7]);	
				map.put("MTYPID", array[8]);	
				map.put("ISNEWTAB", array[9]);
			
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


	public AddMenuUpdateEntity findByMENUID(Long MENUID) {
		return addMenuUpdateRepository.findById(MENUID).get();
	}

	public AddMenuUpdateEntity save(AddMenuUpdateEntity addMenuUpdate) {
		return addMenuUpdateRepository.save(addMenuUpdate);
		
	}
	
	
	
}
	
	


