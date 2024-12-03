package com.gov.Authmis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SendMailReportModel;
import com.gov.Authmis.service.SendMailReportService;

@Service
public class SendMailReportServiceImpl implements SendMailReportService {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<SendMailReportModel> GetResult(SendMailReportModel sendMailReportModel) throws SQLException {
		
		System.out.println(sendMailReportModel.getFromdate());
		System.out.println(sendMailReportModel.getEnddate());
		List<SendMailReportModel> data = new ArrayList<>();

		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_Mis_MailReport")
				.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR)
				.setParameter("FROM_DATE", sendMailReportModel.getFromdate())
				.setParameter("End_DATE", sendMailReportModel.getEnddate()).setParameter("TRANSACTION_ID", null)
				.setParameter("status", 1);
		query.execute();

		ResultSet DetailsObj = (ResultSet) query.getOutputParameterValue("prc");
	
		while (DetailsObj.next()) {
			SendMailReportModel listResult = new SendMailReportModel();
			listResult.setSrno(DetailsObj.getLong(1));
			listResult.setSubAuaCode(DetailsObj.getString(2));
			listResult.setTo(DetailsObj.getString(3));
			listResult.setCc(DetailsObj.getString(4));
			listResult.setSubject(DetailsObj.getString(5));
			listResult.setSend_email_date(DetailsObj.getDate(6));
			listResult.setIp(DetailsObj.getString(7));
			listResult.setStatus(DetailsObj.getInt(8));
			data.add(listResult);

		}
		return data;
	}

	/**
	@Override
	public List<SendMailReportModel> GetResult(SendMailReportModel sendMailReportModel) throws SQLException {
		
		System.out.println(sendMailReportModel.getFromdate());
		System.out.println(sendMailReportModel.getEnddate());
		List<SendMailReportModel> data = new ArrayList<>();

		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_Mis_MailReport")
				.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR)
				.setParameter("FROM_DATE", sendMailReportModel.getFromdate())
				.setParameter("End_DATE", sendMailReportModel.getEnddate()).setParameter("TRANSACTION_ID", null)
				.setParameter("status", 1);
		query.execute();

		ResultSet DetailsObj = (ResultSet) query.getOutputParameterValue("prc");
	
		while (DetailsObj.next()) {
			SendMailReportModel listResult = new SendMailReportModel();
			listResult.setSrno(DetailsObj.getLong(1));
			listResult.setSubAuaCode(DetailsObj.getString(2));
			listResult.setTo(DetailsObj.getString(3));
			listResult.setCc(DetailsObj.getString(4));
			listResult.setSubject(DetailsObj.getString(5));
			listResult.setSend_email_date(DetailsObj.getDate(6));
			listResult.setIp(DetailsObj.getString(7));
			listResult.setStatus(DetailsObj.getInt(8));
			data.add(listResult);

		}
		return data;
	}

	**/
	@Override
	public HashMap<String, Object> getTransactionIdMappingDetails(String tRANSACTION_ID, String fROM_DATE,
			String end_DATE) {
		HashMap<String, Object> memberData = new HashMap<>();

		System.out.println("E_AADHAAR_ID   is service impl----------------------->>>>>>>>>>>>>  " + tRANSACTION_ID);
		try {
			StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_Mis_MailReport")
					.registerStoredProcedureParameter("FROM_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("End_DATE", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
			System.out.println("TRANSACTION_ID+++++++++++++++++++++" + query.toString());

			query.setParameter("FROM_DATE", fROM_DATE).setParameter("End_DATE", end_DATE)
					.setParameter("TRANSACTION_ID", tRANSACTION_ID).setParameter("status", 2);

			query.execute();

			ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
			System.out.println("Get_Mis_MailReport___________________>" + rs.toString());
			List<Map<String, Object>> obj = ResultSetToListConverter.getListFromResultSet(rs);
			System.out.println("Get_Mis_MailReport    :" + obj);

			memberData.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return memberData;
	}

	
	// -----------------------------------------------------for getting file
	
	@Override
	public  @ResponseBody HashMap<String, Object> TransactionIdMappingDetailsForAttchment(String TRANSACTION_ID) {

		HashMap<String, Object> result = new HashMap<>();
		SendMailReportModel emailData = new SendMailReportModel();
		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.Get_Mis_Report_Of_Mail")
				.registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter("STATUS", String.class, ParameterMode.IN);
		query.setParameter("TRANSACTION_ID", TRANSACTION_ID).setParameter("STATUS", "2");
		query.execute();
		
		  ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
		  
		  List<Map<String, Object>> obj =
		  ResultSetToListConverter.getListFromResultSet(rs);
		  
		  result.put("data", obj); 
		  
		  return result;
		 
		
			/*
			 * List<SendMailReportModel> emailDataList = new ArrayList<>(); ResultSet
			 * detailsObj = (ResultSet) query.getOutputParameterValue("prc");
			 * 
			 * List<SendMailReportModel> files = new ArrayList<>(); ResultSet DetailsObj =
			 * (ResultSet) query.getOutputParameterValue("prc");
			 * 
			 * try { while (DetailsObj.next()) {
			 * 
			 * emailData.setFileId(DetailsObj.getString(1));
			 * emailData.setFileName(DetailsObj.getString(2));
			 * emailData.setFileType(DetailsObj.getString(3)); //convert file data in
			 * orignal firmat Blob base64Content = DetailsObj.getBlob(4);
			 * 
			 * emailData.setUri(DetailsObj.getString(5)); } } catch (SQLException e) {
			 * e.printStackTrace(); }
			 * 
			 * try { while (detailsObj.next()) { String fileId = detailsObj.getString(1);
			 * String fileName = detailsObj.getString(2); String fileType =
			 * detailsObj.getString(3); Blob fileBlob = detailsObj.getBlob(4); String uri =
			 * detailsObj.getString(5);
			 * 
			 * DownloadableFile file = new DownloadableFile(fileId, fileName, fileType,
			 * fileBlob, uri); files.add(file); } } catch (SQLException e) {
			 * e.printStackTrace(); } emailDataList.add(emailData); return emailDataList;
			 */

		
		/*
		 * ResultSet rs = (ResultSet) query.getOutputParameterValue("prc");
		 * 
		 * List<Map<String, Object>> obj =
		 * ResultSetToListConverter.getListFromResultSet(rs);
		 * 
		 * result.put("data", obj); return result;
		 */

	}

}
