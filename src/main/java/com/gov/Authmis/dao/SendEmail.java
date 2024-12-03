package com.gov.Authmis.dao;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.gov.Authmis.entity.MisNonLiveTrnLogsEntity;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class SendEmail {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MailProperties  mailProperties;

	private JavaMailSender javaMailSender;

	public SendEmail(JavaMailSender javaMailSender) 
	{
		this.javaMailSender = javaMailSender;
	}

	String auaCode;
	String auaName;
	String s1 = null;
	int status =1;
	String emailID;	
	String appVersion = "Authmis-0.1";
	String ipAddress;
	SendEmailDAOImpl SendEmailDAOImpl = new SendEmailDAOImpl();
	List<Map<String, Object>> subAUAData = null;	
	List<MisNonLiveTrnLogsEntity> newList = new ArrayList<MisNonLiveTrnLogsEntity>();
	List<MisNonLiveTrnLogsEntity> newListRICS = new ArrayList<MisNonLiveTrnLogsEntity>();
	List<MisNonLiveTrnLogsEntity> misNonliveList ;
	List<Map<String, Object>> subaualistdataforRICS;

	public void nonLiveSendEmailToSubaua(List<MisNonLiveTrnLogsEntity> misNonliveList,String fromDate,String toDate,String emailDate,String batchId) throws SQLException, UnknownHostException  
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

				newList = getFilterOutput(misNonliveList,auaCode);

				if(newList.size() > 0)
				{
					if(auaCode.equals("PRCIS22869") && flagRICS == true) // staging STGDOIT214  // prod PRCIS22869
					{
						continue;
						//System.out.println("in loop");
					}
					else if(auaCode.equals("PFCSD22861") && flagRICS == false)  // staging STGDOIT214  // prod  PRCIS22869
					{
						int count = 0;
						String appname;	
						String [] to;
						String emailRICS;													

						subaualistdataforRICS =  getFilterOutputSubAuaCode(subAUAData,auaCode);
						//System.out.println("subaualistdataforRICS ..." +subaualistdataforRICS);

						for(int n = 0; n < subaualistdataforRICS.size(); n++)
						{
							//System.out.println("subaualistdata.size() ..." +subaualistdata.size()+"at n="+n);
							appname =  (String) subaualistdataforRICS.get(n).get("SUB_AUA_NAME");
							//System.out.println("APPNAME .."+appname);

							newListRICS = getFilterOutputForRICS(newList,appname);								
							//System.out.println("resultOfWeeksForRICS .."+resultOfWeeksForRICS);

							if(newListRICS.size() > 0)
							{																		
								emailRICS = (String) subaualistdataforRICS.get(n).get("POCEMAIL_T");
								//System.out.println("Email id is : "+emailRICS);
								setEmailTemplateforForRICS(appname, fromDate, toDate, emailDate);
								to = (emailRICS).split(",");	

								//String[] to = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","aryanjain311031@gmail.com","SUPPORT.UID@rajasthan.gov.in"};

								String subject = "Regarding suspected transaction data for fingerprint liveness score during aadhaar authentication " + fromDate + "to" + toDate + " || " + appname;

								//System.out.println("MAIL SENT");
								try 
								{
									status= sendHtmlMail(to,subject,s1);
								} 
								catch (MessagingException e) 
								{		
									e.printStackTrace();
									saveLogs(auaCode,auaName,status,"NonLive-101","Some Exception occured while mail sending",emailRICS,batchId);
								}				
								if(status == 1)
								{
									//for (int i11=0; i11 < newListRICS.size(); i11++)
									//{
//										String RESPONSE_CODE = newListRICS.get(i11).getAuthResponseCode();
//										String ENRREFID = newListRICS.get(i11).getEnrRefId();
//										String DPID = newListRICS.get(i11).getDeviceProviderId();
//										String DC = newListRICS.get(i11).getDeviceCode();
//										String M_ID = newListRICS.get(i11).getModelId();
//										String F_M_S = newListRICS.get(i11).getFingerMatchScore();
//										String TXNID = newListRICS.get(i11).getTxn();
//										String UU_ID = newListRICS.get(i11).getUuid();
//										String APP_NAME = newListRICS.get(i11).getAppname();

										saveLogs(auaCode,auaName,status,"","", emailRICS,batchId);
									//}
								}
								else 
								{
									saveLogs(auaCode,auaName,status,"NonLive-102","Failed to send email", emailRICS, batchId);		 
								}	
							}
//							else 
//							{
//								saveLogs(auaCode,auaName,"","","","","","","","","",0,"NonLive-103","Data not Found for this subaua","",fromDate,toDate,emailDate,batchId);	
//							}
						}
						flagRICS = true;
					}
					else 
					{	
						//String subject = "Regarding suspected transaction data for fingerprint liveness score during aadhaar authentication " + fromDate + " to " + toDate + " || " + auaName;
						String subject = "Regarding Blocking of Aadhaar numbers based on suspected transaction data for fingerprint liveness score during aadhaar authentication " + fromDate + "to" + toDate + " || " + auaName;

						setEmailTemplateforOthers(fromDate,toDate,emailDate);

						String [] to = (emailID).split(",");						
						//String[] to = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","aryanjain311031@gmail.com","SUPPORT.UID@rajasthan.gov.in"};
						try 
						{
							status= sendHtmlMail(to,subject,s1);
						} 
						catch (MessagingException e) 
						{		
							e.printStackTrace();							
							saveLogs(auaCode,auaName,status,"NonLive-101","Some Exception occured while mail sending", emailID,batchId);
						}				
						if(status == 1)
						{

//							for (int i11=0; i11 < newList.size(); i11++)
//							{
//								String RESPONSE_CODE = newList.get(i11).getAuthResponseCode();
//								String ENRREFID = newList.get(i11).getEnrRefId();
//								String DPID = newList.get(i11).getDeviceProviderId();
//								String DC = newList.get(i11).getDeviceCode();
//								String M_ID = newList.get(i11).getModelId();
//								String F_M_S = newList.get(i11).getFingerMatchScore();
//								String TXNID = newList.get(i11).getTxn();
//								String UU_ID = newList.get(i11).getUuid();
//								String APP_NAME = newList.get(i11).getAppname();

								saveLogs(auaCode,auaName,status,"200","Email sent successfully",emailID,batchId);
//   						}														
						}
						else 
						{
							saveLogs(auaCode,auaName,status,"NonLive-102","Failed to send email",emailID,batchId);		 // (String) subAUAData.get(i).get("EMAIL")	
						}												
					}					
				}
//				else 
//				{
//					saveLogs(auaCode,auaName,"","","","","","","","","",0,"NonLive-103","Data not Found for this subaua","",fromDate,toDate,emailDate,batchId);
//				}
			}
		}
		else
		{
			saveLogs("","",0,"NonLive-103","subaua Data not Found from table","",batchId);
		}

	}

	private List<Map<String, Object>> getSubAuaData() {
		String sql = "select sub_aua_code, sub_aua_name, POCEMAIL_T from aadhaar.subaua_ekyc_lk where status=1 and SUB_AUA_CODE is not null and POCEMAIL_T is not null order by SUB_AUA_NAME ";
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

	private void setEmailTemplateforOthers(String fromDate,String toDate, String emailDate) 
	{
		s1 = "Dear Sir/Ma'am,<br/><br/><p>With reference to the above subject, it is requested that <b>" + auaName +"</b> is registered as Sub-AUA under AUA-DoIT&C for using Aadhaar based Authentication services In this reference, we have received a report from UIDAI, Govt. of India via email dated <b>" + emailDate +"</b>, regarding Fake Non-Live Auth Transaction Data for the duration of <b>" + fromDate +"</b> to <b> " + toDate +"</b> According to this report:- Liveness score greater than <b>0.6</b> indicates that for such transactions, finger's match was a success but the liveness check has failed. This indicates the probable use of gummy fingers leading to fraudulent transactions.</p>";
//		s1 = s1 + "<br/>";
//		s1 = s1 + "<div style='overflow-x: auto;'>";
//		s1 = s1 + "<table style='border: 2px solid #fff; width: 100%;border-collapse: collapse;' border='1'>";
//		s1 = s1 + "<tr id = 'dateRow'>";
//		s1 = s1 + "<td colspan='6' style='text-align:center; padding: 5px; border: 2px solid;'><b> SUBAUA Code : " + auaCode + ", SUBAUA Name : " + auaName + "</b></td>";
//		s1 = s1 + "</tr>";
//		s1 = s1 + "<tr>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Txn ID</td>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Ref ID</td>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Provider</td>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Device Code</td>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Model ID</td>";
//		s1 = s1 + "<td style='border: 2px solid #000000;font-weight: bold;background-color:#009933;color:#fff;padding: 10px;text-align: center'>Finger Match Score</td>";
//		s1 = s1 + "</tr>";
//		
////		List<MisNonLiveTrnLogsEntity> distinctRecords = getDisticntRecordSubAuaWise(newList.get(0).getBatchId(), newList.get(0).getSa());
//
//		for (int i11=0; i11 < newList.size(); i11++)
//		{			
//			s1 = s1 + "<tr>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getTxn() + "</td>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getUuid() + "</td>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getDeviceProviderId()+ "</td>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getDeviceCode() + "</td>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getModelId() + "</td>";
//			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newList.get(i11).getFingerMatchScore() + "</td>";
//					
//		}			
//		s1 = s1 + "</table>";
//		s1 = s1 + "</div>";
		s1 = s1 + "<br/><p>These transactions were done through Sub-AUA code of <b>" + auaName +"</b> , therefore you are requested to conduct a thorough investigation based on transaction details provided by UIDAI, and provide Action Taken Report (ATR) regarding these transactions on our email <b> doitc.aua@rajasthan.gov.in </b> within 5 days of receipt of this email as we are required to share the same report to UIDAI, Govt. of India. It is requested to treat this on urgent basis as UIDAI may take action against Sub-AUA in case of non-submission of report as per their SOP dated   22-11-2022, responsibility of which lies with concern Sub-AUA only.</p>";
		s1 = s1 + "<br/><p><b>Further, DoIT&C has blocked the suspected Aadhaar numbers from which these transactions have been performed. These Aadhaar numbers shall be unblocked only upon receipt of Action Taken Report from your side.</b></p>";
		s1 = s1 + "<p><b>Detailed report of these transaction is available in our Aadhaar MIS Application under Non-Liveness -> Non-Live Batchwise report option. You may search the report by using the date range <b>" + fromDate +"</b> to <b> " + toDate +"</b>.</p>";
		s1 = s1 + "<br/><p><b>Regards:<br/>UID Project Team (Aadhaar Authentication- AUA/ASA)<br/>Department of Information Technology & Communication<br/>I.T.Building, Yojana Bhawan, Jaipur (Rajasthan)<br/>IP Ext - 21354, 21606, 21381<br/>Ph: 0141-2921354, 2921606, 2921381</p>";
		s1 = s1 + "<br/>";
		s1 = s1 + "<br/>";
//		s1 = s1 + "=============================================================================================================================================================================================================================================";
		s1 = s1 + "<p>Disclaimer: This email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to which they are addressed. You may not share this message or any of its attachments to anyone.</p>";
//		s1 = s1 + "=============================================================================================================================================================================================================================================";
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
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getTxn() + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getUuid()+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getDeviceProviderId()+ "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getDeviceCode() + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getModelId() + "</td>";
			s1 = s1 + "<td style='border: 2px solid; padding-left: 5px;'>" + newListRICS.get(i11).getFingerMatchScore() + "</td>";							
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

	private static ArrayList<MisNonLiveTrnLogsEntity> getFilterOutput(List<MisNonLiveTrnLogsEntity> lines, String filter) 
	{
		List<MisNonLiveTrnLogsEntity> newList = new ArrayList<MisNonLiveTrnLogsEntity>();

		for (int i = 0; i < lines.size(); i++) 
		{
			String s = lines.get(i).getSa();

			if (s.equals(filter))
				newList.add(lines.get(i));
		}
		return (ArrayList<MisNonLiveTrnLogsEntity>) newList;
	}

	private static ArrayList<MisNonLiveTrnLogsEntity> getFilterOutputForRICS(List<MisNonLiveTrnLogsEntity> lines, String filter)
	{
		List<MisNonLiveTrnLogsEntity> newList = new ArrayList<MisNonLiveTrnLogsEntity>();

		for (int i = 0; i < lines.size(); i++)
		{
			String s = lines.get(i).getAppname();
			if (s.equals(filter))
				newList.add(lines.get(i));
		}
		return (ArrayList<MisNonLiveTrnLogsEntity>) newList;
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

	public void saveLogs(String Subaua,String SUBAUANAME,Integer STATUS, String ERROR_CODE, String ERROR_MSG,String EMAILID, String batchid) 
	{				 	 						
		StoredProcedureQuery sp = this.entityManager.createStoredProcedureQuery("AADHAAR.PROC_MIS_LIVENESS_EMAIL")

				.registerStoredProcedureParameter("Subaua", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SUBAUANAME", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("STATUS", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ERROR_CODE", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("ERROR_MSG", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("EMAILID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("IP", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("APP_VER", String.class, ParameterMode.IN)		
				.registerStoredProcedureParameter("batchid", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("fromDate", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("endDate",String.class,ParameterMode.IN)
				.registerStoredProcedureParameter("emailRcvDate",String.class, ParameterMode.IN);

		sp.setParameter("Subaua", Subaua);	
		sp.setParameter("SUBAUANAME", SUBAUANAME);		
		sp.setParameter("STATUS", STATUS);
		sp.setParameter("ERROR_CODE", ERROR_CODE);
		sp.setParameter("ERROR_MSG", ERROR_MSG);
		sp.setParameter("EMAILID", EMAILID);
		sp.setParameter("IP", ipAddress);
		sp.setParameter("APP_VER", appVersion);
		sp.setParameter("batchid", batchid);
		sp.setParameter("fromDate", newList.get(0).getFromDate());
		sp.setParameter("endDate",newList.get(0).getToDate());
		sp.setParameter("emailRcvDate", newList.get(0).getEmailDate());


		sp.execute();
	}

	public Integer sendHtmlMail(String[] to, String subject, String content) throws MessagingException
	{
		int status=0;
		if(to !=null && to.length > 0)
		{
			String[] toCC = {"ranveersingh.doit@rajasthan.gov.in","pankajjaldeep.doit@rajasthan.gov.in","pm.uid@rajasthan.gov.in","SUPPORT.UID@rajasthan.gov.in"};
			//String[] toCC = {"pm.uid@rajasthan.gov.in", "pankajjaldeep.doit@rajasthan.gov.in"};

			MimeMessage mail = javaMailSender.createMimeMessage();
			//String body = templateEngine.process(templateName, context);
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			//helper.setFrom("donotreply.doitc-uid-support@raj.gov.in");
			helper.setFrom("donotreply.uidsupport@raj.gov.in");
			helper.setTo(to);
			helper.setCc(toCC);
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

	public static List<MisNonLiveTrnLogsEntity> excelToEntity(InputStream is) {
		int cellIdx = 0;
		int rowNumber = 0;
		try {
			Workbook workbook = new XSSFWorkbook(is);

			//Sheet sheet = workbook.getSheet(SHEET);wb.getSheetAt(0)
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntities = new ArrayList<MisNonLiveTrnLogsEntity>();

			//int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				MisNonLiveTrnLogsEntity misNonLiveTrnLogsEntity = new MisNonLiveTrnLogsEntity();

				//int cellIdx = 0;
				cellIdx=0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						misNonLiveTrnLogsEntity.setAuthResponseCode(currentCell.getStringCellValue());
						break;

					case 1:
						misNonLiveTrnLogsEntity.setSa(currentCell.getStringCellValue());
						break;

					case 2:
						misNonLiveTrnLogsEntity.setEnrRefId(currentCell.getStringCellValue());
						break;

					case 3:
						misNonLiveTrnLogsEntity.setDeviceProviderId(currentCell.getStringCellValue());
						break;

					case 4:
						misNonLiveTrnLogsEntity.setDeviceCode(currentCell.getStringCellValue());
						break;

					case 5:
						misNonLiveTrnLogsEntity.setModelId(currentCell.getStringCellValue());
						break;

					case 6:
						misNonLiveTrnLogsEntity.setFingerMatchScore(String.valueOf(currentCell.getNumericCellValue()));
						break;

					case 7:
						misNonLiveTrnLogsEntity.setPidTs(currentCell.getStringCellValue());
						break;

					case 8:
						misNonLiveTrnLogsEntity.setReqDateTime(currentCell.getStringCellValue());
						break;

					case 9:
						misNonLiveTrnLogsEntity.setDt(currentCell.getStringCellValue());
						break;

					case 10:
						misNonLiveTrnLogsEntity.setTxn(currentCell.getStringCellValue());
						break;

					case 11:
						misNonLiveTrnLogsEntity.setAppname(currentCell.getStringCellValue());
						break;

					default:
						break;
					}

					cellIdx++;
				}
				//System.out.println("misNonLiveTrnLogsEntity==>>"+misNonLiveTrnLogsEntity);
				misNonLiveTrnLogsEntities.add(misNonLiveTrnLogsEntity);
			}

			workbook.close();

			return misNonLiveTrnLogsEntities;
		} catch (IOException e) {
			throw new RuntimeException("Error at Row=="+rowNumber+"and colom=="+cellIdx+"fail to parse Excel file: " + e.getMessage());
		}
	}


}
