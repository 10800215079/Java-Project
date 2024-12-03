package com.gov.Authmis.dao;

import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.gov.Authmis.entity.NonLiveUnblockEntity;


@Service
public class SendEmailUnblock {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MailProperties  mailProperties;

	private JavaMailSender javaMailSender;

	public SendEmailUnblock(JavaMailSender javaMailSender) 
	{
		this.javaMailSender = javaMailSender;
	}

	String auaCode;
	String auaName;
	String s1 = null;
	int status;
	String emailID;	
	String appVersion = "Authmis-0.1";
	String ipAddress;
	SendEmailDAOImpl SendEmailDAOImpl = new SendEmailDAOImpl();
	List<Map<String, Object>> subAUAData = null;	
	List<NonLiveUnblockEntity> newList = new ArrayList<NonLiveUnblockEntity>();
	List<NonLiveUnblockEntity> newListRICS = new ArrayList<NonLiveUnblockEntity>();
	List<NonLiveUnblockEntity> misNonliveList ;
	List<Map<String, Object>> subaualistdataforRICS;

	public void SendEmailUnblockbyBatchId(List<NonLiveUnblockEntity> finalList,String fromDate,String toDate,String batchId,String subaua) throws SQLException, UnknownHostException  
	{	

		//get subaua codes & email Ids
		//subAUAData = SendEmailDAOImpl.getSubAuaData();
		subAUAData = getSubAuaData();

		// get system IP address
		InetAddress ip = InetAddress.getLocalHost();
		ipAddress = ip.toString();

		if (subAUAData.size() != 0)
		{
			boolean flagRICS = false;

			for (int i = 0; i < subAUAData.size(); i++) 
			{
				auaCode = (String)(subAUAData.get(i)).get("SUB_AUA_CODE");				
				auaName = (String)(subAUAData.get(i)).get("SUB_AUA_NAME");		
				emailID = (String)(subAUAData.get(i)).get("POCEMAIL_T");			

				newList = getFilterOutput(finalList,auaCode);

				if(newList.size() > 0)
				{

					String subject = "Regarding suspected transaction data for fingerprint liveness score during aadhaar authentication " + fromDate + " to " + toDate + " || " + auaName;

					setEmailTemplateforOthers(fromDate,toDate);

					String [] to = (emailID).split(",");						
					//String[] to = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","aryanjain311031@gmail.com","SUPPORT.UID@rajasthan.gov.in"};
					try 
					{
						status= sendHtmlMail(to,subject,s1);
					} 
					catch (MessagingException e) 
					{		
						e.printStackTrace();							
						saveLogs(auaCode,auaName,"","","","","","","","","",status,"NonLive-101","Some Exception occured while mail sending","",fromDate,toDate,"",batchId);
					}				
					if(status == 1)
					{

						for (int i11=0; i11 < newList.size(); i11++)
						{
							String RESPONSE_CODE = newList.get(i11).getResponseCode();
							//String ENRREFID = newList.get(i11).getRefId();
							//String DPID = newList.get(i11).getDeviceProviderId();
							String DC = newList.get(i11).getDeviceCode();
							//String M_ID = newList.get(i11).getModelId();
							//String F_M_S = newList.get(i11).getFingerMatchScore();
							String TXNID = newList.get(i11).getTransactionId();
							String UU_ID = newList.get(i11).getRefId();
							//String APP_NAME = newList.get(i11).getAppname();


							saveLogs(auaCode,auaName,RESPONSE_CODE,"","",DC,"","",TXNID,UU_ID,"",status,"","",emailID,fromDate,toDate,"",batchId);
						}														
					}
					else 
					{
						saveLogs(auaCode,auaName,"","","","","","","","","",status,"NonLive-102","Failed to send email","",fromDate,toDate,"",batchId);		 // (String) subAUAData.get(i).get("EMAIL")	
					}												

				}
				
			}
		}
		else
		{
			saveLogs("","","","","","","","","","","",0,"NonLive-103","subaua Data not Found from table","",fromDate,toDate,"",batchId);
		}

	}

	private List<Map<String, Object>> getSubAuaData() {
		 String sql = "select sub_aua_code, sub_aua_name, POCEMAIL_T from aadhaar.subaua_ekyc_lk where status=1 and SUB_AUA_CODE is not null and POCEMAIL_T is not null order by SUB_AUA_NAME";
	        Query query = this.entityManager.createNativeQuery(sql);
	        
	        @SuppressWarnings("unchecked")
	        List<Object[]> resultList = query.getResultList();
	        List<Map<String, Object>> listofdetails = new ArrayList<>();

	        for (Object[] row : resultList) {
	                Map<String, Object> rowMap = new HashMap<>();
	                rowMap.put("sub_aua_code", row[0]);
	                rowMap.put("sub_aua_name", row[1]);
	                rowMap.put("POCEMAIL_T", row[2]);

	                listofdetails.add(rowMap);
	        }

	        return listofdetails;
	}
	

	public void SendEmailUnblockbyAadhaarId(String batchid, String adhaarid, String subauacode, String fromdate, String enddate, String dc, String uidresponsecode, String transactionid, String ssoid) throws SQLException, UnknownHostException  
	{	
		//get subaua codes & email Ids
		//subAUAData = SendEmailDAOImpl.getSingleSubAuaData(subauacode);
		//make changes
		subAUAData = getSingleSubAuaData(subauacode);
	
		
		System.out.println("subaua data for single subaua : ...."+subAUAData);

		// get system IP address
		InetAddress ip = InetAddress.getLocalHost();
		ipAddress = ip.toString();

		if (subAUAData.size() != 0)
		{		
			for (int i = 0; i < subAUAData.size(); i++) 
			{
				auaCode = (String)(subAUAData.get(i)).get("SUB_AUA_CODE");				
				auaName = (String)(subAUAData.get(i)).get("SUB_AUA_NAME");		
				emailID = (String)(subAUAData.get(i)).get("POCEMAIL_T");			
				
				String subject = "Regarding suspected transaction data for fingerprint liveness score during aadhaar authentication " + fromdate + " to " + enddate + " || " + auaName;

				s1 = "Dear Sir/Ma'am,<br/><br/><p>With reference to the above subject, it is requested that " + auaName +" is registered as Sub-AUA under AUA-DoIT&C for using Aadhaar based Authentication services In this reference, we have received a report from UIDAI, regarding Fake Non-Live Auth Transaction Data for the duration of " + fromdate +" to " + enddate +" According to this report:- Liveness score greater than 0.6 indicates that for such transactions, finger's match was a success but the liveness check has failed. This indicates the probable use of gummy fingers leading to fraudulent transactions. Detailed report of these transactions is as given below.</p>";
				s1 = s1 + "<br/>";
				s1 = s1 + "<div style='overflow-x: auto;'>";
				s1 = s1 + "<table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
				s1 = s1 + "<tr id = 'dateRow'>";
				s1 = s1 + "<td colspan='6' style='text-align:center; padding: 5px; border: 2px solid;'><b> SUBAUA Code : " + auaCode + ", SUBAUA Name : " + auaName + "</b></td>";
				s1 = s1 + "</tr>";
				s1 = s1 + "<tr>";
				s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Txn ID</td>";
				s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Ref ID</td>";
				//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Provider</td>";
				s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Code</td>";
				//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Model ID</td>";
				//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Finger Match Score</td>";
				s1 = s1 + "</tr>";

				s1 = s1 + "<tr>";
				s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + transactionid + "</td>";
				s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + adhaarid + "</td>";
				//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + + "</td>";
				s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + dc + "</td>";
				//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" +  + "</td>";
				//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" +  + "</td>";

				s1 = s1 + "</table>";
				s1 = s1 + "</div>";
				s1 = s1 + "<br/><p>These transactions were done through Sub-AUA code of <b>" + auaName +"</b> , therefore you are requested to conduct a thorough investigation based on transaction details provided by UIDAI, and provide Action Taken Report (ATR) regarding these transactions on our email <b> doitc.aua@rajasthan.gov.in </b> within 5 days of receipt of this email as we are required to share the same report to UIDAI, Govt. of India. It is requested to treat this on urgent basis as UIDAI may take action against Sub-AUA in case of non-submission of report as per their SOP dated   22-11-2022, responsibility of which lies with concern Sub-AUA only.</p>";
				s1 = s1 + "<br/><p><b>Further, DoIT&C has blocked the suspected Aadhaar numbers from which these transactions have been performed. These Aadhaar numbers shall be unblocked only upon receipt of Action Taken Report from your side.</b></p>";
				s1 = s1 + "<br/><p>Regards:<br/>UID Project Team (Aadhaar Authentication- AUA/ASA)<br/>Department of Information Technology & Communication<br/>I.T.Building, Yojana Bhawan, Jaipur (Rajasthan)<br/>IP Ext - 21354, 21606, 21381<br/>Ph: 0141-2921354, 2921606, 2921381</p>";
				s1 = s1 + "<br/>";
				s1 = s1 + "=============================================================================================================================================================================================================================================";
				s1 = s1 + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
				s1 = s1 + "=============================================================================================================================================================================================================================================";
				s1 = s1 + "<br/><p>Kindly Revert on : <b> doitc.aua@rajasthan.gov.in </b></p>";

				String [] to = (emailID).split(",");//String[] to = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","aryanjain311031@gmail.com","SUPPORT.UID@rajasthan.gov.in"};
				
											

			}
		}
		else
		{
			saveLogs("","","","","","","","","","","",0,"NonLive-103","subaua Data not Found from table","",fromdate,enddate,"",batchid);
		}

	}


	private List<Map<String, Object>> getSingleSubAuaData(String subauacode) {
		String sql = "select sub_aua_code, sub_aua_name, POCEMAIL_T from aadhaar.subaua_ekyc_lk where status=1 and SUB_AUA_CODE ='" + subauacode + "' order by SUB_AUA_NAME";

		Query query = this.entityManager.createNativeQuery(sql);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();
		List<Map<String, Object>> listofdetails = new ArrayList<>();

		for (Object[] row : resultList) {
			Map<String, Object> rowMap = new HashMap<>();
			rowMap.put("sub_aua_code", row[0]);
			rowMap.put("sub_aua_name", row[1]);
			rowMap.put("POCEMAIL_T", row[2]);

			listofdetails.add(rowMap);
		}

		return listofdetails;
	}

	private void setEmailTemplateforOthers(String fromDate,String toDate) 
	{
		s1 = "Dear Sir/Ma'am,<br/><br/><p>With reference to the above subject, it is requested that " + auaName +" is registered as Sub-AUA under AUA-DoIT&C for using Aadhaar based Authentication services In this reference, we have received a report from UIDAI, regarding Fake Non-Live Auth Transaction Data for the duration of " + fromDate +" to " + toDate +" According to this report:- Liveness score greater than 0.6 indicates that for such transactions, finger's match was a success but the liveness check has failed. This indicates the probable use of gummy fingers leading to fraudulent transactions. Detailed report of these transactions is as given below.</p>";
		s1 = s1 + "<br/>";
		s1 = s1 + "<div style='overflow-x: auto;'>";
		s1 = s1 + "<table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
		s1 = s1 + "<tr id = 'dateRow'>";
		s1 = s1 + "<td colspan='6' style='text-align:center; padding: 5px; border: 2px solid;'><b> SUBAUA Code : " + auaCode + ", SUBAUA Name : " + auaName + "</b></td>";
		s1 = s1 + "</tr>";
		s1 = s1 + "<tr>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Txn ID</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Ref ID</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Provider</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Code</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Model ID</td>";
		//s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Finger Match Score</td>";
		s1 = s1 + "</tr>";

		for (int i11=0; i11 < newList.size(); i11++)
		{			
			s1 = s1 + "<tr>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getTransactionId() + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getRefId() + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getDeviceProviderId()+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getDeviceCode() + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getModelId() + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getFingerMatchScore() + "</td>";

		}			
		s1 = s1 + "</table>";
		s1 = s1 + "</div>";
		s1 = s1 + "<br/><p>These transactions were done through Sub-AUA code of <b>" + auaName +"</b> , therefore you are requested to conduct a thorough investigation based on transaction details provided by UIDAI, and provide Action Taken Report (ATR) regarding these transactions on our email <b> doitc.aua@rajasthan.gov.in </b> within 5 days of receipt of this email as we are required to share the same report to UIDAI, Govt. of India. It is requested to treat this on urgent basis as UIDAI may take action against Sub-AUA in case of non-submission of report as per their SOP dated   22-11-2022, responsibility of which lies with concern Sub-AUA only.</p>";
		s1 = s1 + "<br/><p><b>Further, DoIT&C has blocked the suspected Aadhaar numbers from which these transactions have been performed. These Aadhaar numbers shall be unblocked only upon receipt of Action Taken Report from your side.</b></p>";
		s1 = s1 + "<br/><p>Regards:<br/>UID Project Team (Aadhaar Authentication- AUA/ASA)<br/>Department of Information Technology & Communication<br/>I.T.Building, Yojana Bhawan, Jaipur (Rajasthan)<br/>IP Ext - 21354, 21606, 21381<br/>Ph: 0141-2921354, 2921606, 2921381</p>";
		s1 = s1 + "<br/>";
		s1 = s1 + "=============================================================================================================================================================================================================================================";
		s1 = s1 + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
		s1 = s1 + "=============================================================================================================================================================================================================================================";
		s1 = s1 + "<br/><p>Kindly Revert on : <b> doitc.aua@rajasthan.gov.in </b></p>";			
	}

	private void setEmailTemplateforForRICS(String appName,String fromDate,String toDate, String emailDate)
	{
		s1 = "Dear Sir/Ma'am,<br/><br/><p>With reference to the above subject, it is requested that " + appName +" is registered as Sub-AUA under AUA-DoIT&C for using Aadhaar based Authentication services In this reference, we have received a report from UIDAI, Govt. of India via email dated " + emailDate +", regarding Fake Non-Live Auth Transaction Data for the duration of " + fromDate +" to " + toDate +" According to this report:- Liveness score greater than 0.6 indicates that for such transactions, finger's match was a success but the liveness check has failed. This indicates the probable use of gummy fingers leading to fraudulent transactions. Detailed report of these transactions is as given below.</p>";
		s1 = s1 + "<br/>";
		s1 = s1 + "<div style='overflow-x: auto;'>";
		s1 = s1 + "<table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
		s1 = s1 + "<tr id = 'dateRow'>";
		s1 = s1 + "<td colspan='6' style='text-align:center; padding: 5px; border: 2px solid;'><b> SUBAUA Code : " + auaCode + ", SUBAUA Name : " + appName + "</b></td>";
		s1 = s1 + "</tr>";
		s1 = s1 + "<tr>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Txn ID</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Ref ID</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Provider</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Code</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Model ID</td>";
		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Finger Match Score</td>";	
		s1 = s1 + "</tr>";

		for (int i11=0; i11 < newListRICS.size(); i11++)
		{			
			s1 = s1 + "<tr>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getTransactionId() + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getRefId()+ "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getDeviceProviderId()+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getDeviceCode() + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getModelId() + "</td>";
			//s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getFingerMatchScore() + "</td>";							
		}				
		s1 = s1 + "</table>";
		s1 = s1 + "</div>";
		s1 = s1 + "<br/><p>These transactions were done through Sub-AUA code of <b>" + appName +"</b> , therefore you are requested to conduct a thorough investigation based on transaction details provided by UIDAI, and provide Action Taken Report (ATR) regarding these transactions on our email <b> doitc.aua@rajasthan.gov.in </b> within 5 days of receipt of this email as we are required to share the same report to UIDAI, Govt. of India. It is requested to treat this on urgent basis as UIDAI may take action against Sub-AUA in case of non-submission of report as per their SOP dated   22-11-2022, responsibility of which lies with concern Sub-AUA only.</p>";
		s1 = s1 + "<br/><p><b>Further, DoIT&C has blocked the suspected Aadhaar numbers from which these transactions have been performed. These Aadhaar numbers shall be unblocked only upon receipt of Action Taken Report from your side.</b></p>";
		s1 = s1 + "<br/><p>Regards:<br/>UID Project Team (Aadhaar Authentication- AUA/ASA)<br/>Department of Information Technology & Communication<br/>I.T.Building, Yojana Bhawan, Jaipur (Rajasthan)<br/>IP Ext - 21354, 21606, 21381<br/>Ph: 0141-2921354, 2921606, 2921381</p>";
		s1 = s1 + "<br/>";
		s1 = s1 + "=============================================================================================================================================================================================================================================";
		s1 = s1 + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
		s1 = s1 + "=============================================================================================================================================================================================================================================";
		s1 = s1 + "<br/><p>Kindly Revert on : <b> doitc.aua@rajasthan.gov.in </b></p>";
	}

	private static ArrayList<NonLiveUnblockEntity> getFilterOutput(List<NonLiveUnblockEntity> lines, String filter) 
	{
		List<NonLiveUnblockEntity> newList = new ArrayList<NonLiveUnblockEntity>();

		for (int i = 0; i < lines.size(); i++) 
		{
			String s = lines.get(i).getSubAuaCode();

			if (s.equals(filter))
				newList.add(lines.get(i));
		}
		return (ArrayList<NonLiveUnblockEntity>) newList;
	}



	private static List<Map<String, Object>> getFilterOutputSubAuaCode(List<Map<String, Object>> lines, String filter) 
	{
		List<Map<String, Object>> result = new ArrayList<>();
		//System.out.println("filter :"+filter);
		for (int i = 0; i < lines.size(); i++) {		
			String s = (String) ((Map<?, ?>) lines.get(i)).get("SUB_AUA_CODE");
			//System.out.println("s :"+s);
			if (s.equals(filter))
				result.add(lines.get(i));
		}
		return result;
	}

	public void saveLogs(String Subaua,String SUBAUANAME, String RESPONSE_CODE, String ENRREFID, String DPID,String DC, String M_ID,String F_M_S, String TXNID, String UU_ID, String APP_NAME,Integer STATUS, String ERROR_CODE, String ERROR_MSG,String EMAILID,String FROMDATE, String ENDDATE,String RCV_DATE, String batchid) 
	{				 	 						
		StoredProcedureQuery sp = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_LIVENESS_EMAIL")

				.registerStoredProcedureParameter("Subaua", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SUBAUANAME", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("RESPONSE_CODE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ENRREFID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("DPID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("DC", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("M_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("F_M_S", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("TXNID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("UU_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("APP_NAME", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ERROR_CODE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ERROR_MSG", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("EMAILID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("IP", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("APP_VER", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("FROMDATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ENDDATE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("RCV_DATE", String.class, ParameterMode.IN)		
				.registerStoredProcedureParameter("batchid", String.class, ParameterMode.IN);

		sp.setParameter("Subaua", Subaua);	
		sp.setParameter("SUBAUANAME", SUBAUANAME);		
		sp.setParameter("RESPONSE_CODE", RESPONSE_CODE);
		sp.setParameter("ENRREFID", ENRREFID);
		sp.setParameter("DPID", DPID);
		sp.setParameter("DC", DC);
		sp.setParameter("M_ID", M_ID);
		sp.setParameter("F_M_S", F_M_S);
		sp.setParameter("TXNID", TXNID);
		sp.setParameter("UU_ID", UU_ID);
		sp.setParameter("APP_NAME", APP_NAME);
		sp.setParameter("STATUS", STATUS);
		sp.setParameter("ERROR_CODE", ERROR_CODE);
		sp.setParameter("ERROR_MSG", ERROR_MSG);
		sp.setParameter("EMAILID", EMAILID);
		sp.setParameter("IP", ipAddress);
		sp.setParameter("APP_VER", appVersion);
		sp.setParameter("FROMDATE", FROMDATE);
		sp.setParameter("ENDDATE", ENDDATE);
		sp.setParameter("RCV_DATE", RCV_DATE);
		sp.setParameter("batchid", batchid);


		sp.execute();
	}

	public Integer sendHtmlMail(String[] to, String subject, String content) throws MessagingException
	{
		int status=0;
		if(to !=null && to.length > 0)
		{
			//String[] toCC = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","SUPPORT.UID@rajasthan.gov.in"};
			//String[] toCC = {"pm.uid@rajasthan.gov.in"};

			MimeMessage mail = javaMailSender.createMimeMessage();
			//String body = templateEngine.process(templateName, context);
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			//helper.setFrom("donotreply.doitc-uid-support@raj.gov.in");
			helper.setFrom("donotreply.uidsupport@raj.gov.in");
			helper.setTo(to);
			//helper.setCc(toCC);
			helper.setSubject(subject);
			helper.setText(content, true);

			javaMailSender.send(mail);

			status = 1;		
		}
		else
		{			
			status = 0;		
		}
		return status;									
	}



}

