package com.gov.Authmis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.gov.Authmis.model.SendMailReportModel;

import com.gov.Authmis.service.SendMailReportService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class SendMailReportController {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	SendMailReportService sendMailReportService;

	SSOLoginController ssoLoginController = new SSOLoginController();
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	@GetMapping({ "/send_mail_details" })
	public String send_mail_details(Model model, HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		return "SendMailReport";
	}

	@GetMapping({ "/getEmailData" })
	public String searchEmailData(Model model,
			@ModelAttribute("sendMailReportModel") SendMailReportModel sendMailReportModel, HttpSession session,HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		
		/**
		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
		Date frDate = format.parse(sendMailReportModel.getFromdate());
		System.out.println("===========> sendMailReportModel.getFromdate() :" + sendMailReportModel.getFromdate());
		Date toDate = format.parse(sendMailReportModel.getEnddate());
		System.out.println("===========> sendMailReportModel.getEnddate() :" + sendMailReportModel.getEnddate());
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String frdate = formatter.format(frDate);
		String todate = formatter.format(toDate);
		sendMailReportModel.setFromdate(frdate);
		sendMailReportModel.setEnddate(todate);
		**/
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String toDate = "";
		String from_Date = "";
		try {
			String dateTimeFromStr = sendMailReportModel.getFromdate();
			String dateTimeToStr = sendMailReportModel.getEnddate();
			// Parse the input date string into a Date object

			Date inputDateFromDate = inputDateFormat.parse(dateTimeFromStr);
			Date inputDateToDate = inputDateFormat.parse(dateTimeToStr);
			// Set the time portion to 23:59:59
			inputDateToDate.setHours(23);
			inputDateToDate.setMinutes(59);
			inputDateToDate.setSeconds(59);

			// Format the modified date into the desired output format
			toDate = outputDateFormat.format(inputDateToDate);
			from_Date = outputDateFormat.format(inputDateFromDate);

			// Print the result
			System.out.println("Formatted Date: " + toDate);
			System.out.println("Formatted Date: " + from_Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		sendMailReportModel.setFromdate(from_Date);
		sendMailReportModel.setEnddate(toDate);
		
		List<SendMailReportModel> searchResult = new ArrayList<>();
		try {
			searchResult = this.sendMailReportService.GetResult(sendMailReportModel);
			String FROM_DATE = sendMailReportModel.getFromdate();
			System.out.println(" from date in model " + FROM_DATE);
			model.addAttribute("FROM_DATE", FROM_DATE);
			String End_DATE = sendMailReportModel.getEnddate();
			model.addAttribute("End_DATE", End_DATE);
			System.out.println(" end date in model " + End_DATE);

			List<Long> srnoList = new ArrayList<>();
			for (int i = 0; i < searchResult.size(); i++) {
				System.out.print("searchResult>>>>> :" + searchResult.get(i).getSrno());
				Long srno = searchResult.get(i).getSrno();
				srnoList.add(srno);
			}
			System.out.println("==========>>>>" + srnoList);
			model.addAttribute("SRNO", srnoList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("result", searchResult);
		// model.addAttribute("SUB_AUA_CODE", suspectedAadhaar.getSubAuaCode());

		return "SendMailReport";
	}
	
	//old code
	/**
	@GetMapping({ "/getEmailData" })
	public String searchEmailData(Model model,
			@ModelAttribute("sendMailReportModel") SendMailReportModel sendMailReportModel, HttpSession session,HttpServletRequest request) throws SQLException {
		try {
			validateSSO.getValidateSSO(request);
			// Main Menu
			mainMenuUtil.getMainMenu(model, request);
			DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
			Date frDate = format.parse(sendMailReportModel.getFromdate());
			System.out.println("===========> sendMailReportModel.getFromdate() :" + sendMailReportModel.getFromdate());
			Date toDate = format.parse(sendMailReportModel.getEnddate());
			System.out.println("===========> sendMailReportModel.getEnddate() :" + sendMailReportModel.getEnddate());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String frdate = formatter.format(frDate);
			String todate = formatter.format(toDate);
			sendMailReportModel.setFromdate(frdate);
			sendMailReportModel.setEnddate(todate);
			List<SendMailReportModel> searchResult = new ArrayList<>();
			try {
				searchResult = this.sendMailReportService.GetResult(sendMailReportModel);
				String FROM_DATE = sendMailReportModel.getFromdate();
				System.out.println(" from date in model " + FROM_DATE);
				model.addAttribute("FROM_DATE", FROM_DATE);
				String End_DATE = sendMailReportModel.getEnddate();
				model.addAttribute("End_DATE", End_DATE);
				System.out.println(" end date in model " + End_DATE);

				List<Long> srnoList = new ArrayList<>();
				for (int i = 0; i < searchResult.size(); i++) {
					System.out.print("searchResult>>>>> :" + searchResult.get(i).getSrno());
					Long srno = searchResult.get(i).getSrno();
					srnoList.add(srno);
				}
				System.out.println("==========>>>>" + srnoList);
				model.addAttribute("SRNO", srnoList);

			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("result", searchResult);
			// model.addAttribute("SUB_AUA_CODE", suspectedAadhaar.getSubAuaCode());

			return "SendMailReport";
		} catch (ParseException e1) {
			e1.printStackTrace();
			return "/SendMailReport";
		}
	}
	
	**/

	@RequestMapping(value = "/TransactionIdMappingDetails", method = RequestMethod.GET)
	public @ResponseBody List<SendMailReportModel> TransactionIdMappingDetails(Model model,
			@RequestParam("TRANSACTION_ID") String TRANSACTION_ID,HttpServletRequest request) throws ParseException, SQLException, IOException {
		// ...
		validateSSO.getValidateSSO(request);
		// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.Get_Mis_Report_Of_Mail")

				.registerStoredProcedureParameter("TRANSACTION_ID", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("STATUS", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("prc", ResultSet.class, ParameterMode.REF_CURSOR);
		System.out.println("TRANSACTION_ID+++++++++++++++++++++" + query1.toString());

		query1.setParameter("TRANSACTION_ID", TRANSACTION_ID).setParameter("STATUS", "1");
		query1.execute();
		List<SendMailReportModel> emailDataList = new ArrayList<>();
		ResultSet DetailsObj = (ResultSet) query1.getOutputParameterValue("prc");
		while (DetailsObj.next()) {
			SendMailReportModel emailData = new SendMailReportModel();
			emailData.setTo(DetailsObj.getString(1));
			emailData.setCc(DetailsObj.getString(2));
			emailData.setSubject(DetailsObj.getString(3));
			emailData.setSend_email_date(DetailsObj.getDate(4));

			Blob base64Content = DetailsObj.getBlob(5);
			if (base64Content != null) {
				byte[] contentBytes = base64Content.getBytes(1, (int) base64Content.length());
				byte[] decodedBytes = Base64.getDecoder().decode(contentBytes);
				String originalData5 = new String(decodedBytes, StandardCharsets.UTF_8);
				emailData.setContent(originalData5);

			}
			emailDataList.add(emailData);

			model.addAttribute("maildata", DetailsObj.getString(1));
			System.out.println(DetailsObj.getString(1));

		}

		return emailDataList;
	}

	@RequestMapping(value = "/TransactionIdMappingDetailsForAttchment", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody Object TransactionIdMappingDetailsForAttchment(Model model,
			@RequestParam("TRANSACTION_ID") String TRANSACTION_ID,HttpServletRequest request) throws ParseException, SQLException {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		HashMap<String, Object> result = new HashMap();
		result = sendMailReportService.TransactionIdMappingDetailsForAttchment(TRANSACTION_ID);
		JSONObject jsonobj = new JSONObject(result);

		System.out.println("JsonObject >>>>>>>>>>>>>>>>>>>>> " + jsonobj);

		return result.get("data");

	}

	@GetMapping("/downloadFile")
	public StreamingResponseBody getStreamingFile(HttpServletResponse response, @RequestParam String path)
			throws IOException {
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + getFileNameFromPath(path) + "\"");

		File file = new File(path);

		InputStream inputStream = new FileInputStream(file);

		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
			inputStream.close();
		};
	}

	private String getFileNameFromPath(String path) {
		int lastIndex = path.lastIndexOf('/');
		if (lastIndex != -1) {
			return path.substring(lastIndex + 1);
		}
		return path;
	}

}
