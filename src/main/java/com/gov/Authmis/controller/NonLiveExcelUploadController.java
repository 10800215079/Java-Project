package com.gov.Authmis.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.dao.SendEmail;
import com.gov.Authmis.entity.MisNonLiveTrnLogsEntity;
import com.gov.Authmis.model.DSMTokanize;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.NonLiveUploadModel;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.NonLiveExcelUploadService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class NonLiveExcelUploadController {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	SSOLoginController ssoLoginController = new SSOLoginController();

	String SsoId = "";

	@Autowired
	NonLiveExcelUploadService service;

	@Autowired
	public SendEmail sendemail;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@GetMapping("/NonLiveExcelUpload_file")
	public String NonLiveExcelFileUpload(HttpServletRequest request, Model model) throws SQLException {
		// Validate SSO Login
		boolean sessionoutput = ssoLoginController.validateSSOSession(request);
		if (sessionoutput) {
			this.SsoId = (String) request.getSession().getAttribute("SSOID");
			// System.out.println("The SSOID is=="+this.SsoId);

			String ssoid1 = (String) request.getSession().getAttribute("SSOID");

			validateSSO.getValidateSSO(request);
			// Main Menu
			mainMenuUtil.getMainMenu(model, request);
			return "NonLiveExcelUpload_file";
		} else {
			request.getSession().invalidate();
			return "redirect:" + Constant.SSO_URL;
		}

	}

	@GetMapping("/NonLiveUnblockBySearching")
	public String NonLiveUnblockBySearching(HttpServletRequest request) {
		// Validate SSO Login
		boolean sessionoutput = ssoLoginController.validateSSOSession(request);
		if (sessionoutput) {
			return "NonLiveUnblockBySearching";

		} else {
			request.getSession().invalidate();
			return "redirect:" + Constant.SSO_URL;
		}

	}

	@GetMapping("/DownloadExcel_file")
	public String DownloadExcel_file() {
		System.out.println("Inside DownloadExcel_file Method");
		return "uidaiNonLivetrn";
	}

	@PostMapping(value = "/NonLiveExcelUpload")
	public String NonLiveExcelUpload(Model model, @ModelAttribute("NonLiveData") NonLiveUploadModel nonLiveUploadModel,
			HttpSession session, RedirectAttributes redirAttrs, HttpServletRequest request) throws SQLException {
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");

		validateSSO.getValidateSSO(request);
// Main Menu
		mainMenuUtil.getMainMenu(model, request);
		System.out.println("Entered into searchAvgResponseTimeData in AUTHMIS_NEW");
		// List<MisNonLiveTrnLogsEntity> searchResult = new
		// ArrayList<MisNonLiveTrnLogsEntity>();

		System.out.println("nonLiveUploadModel.getFromdate()==>>>" + nonLiveUploadModel.getFromdate());
		System.out.println("nonLiveUploadModel.getEnddate()==>>>>" + nonLiveUploadModel.getEnddate());
		System.out.println("nonLiveUploadModel.getEmailDate()==>>" + nonLiveUploadModel.getEmailDate());
		System.out.println("nonLiveUploadModel.getExcelFile()==>>" + nonLiveUploadModel.getExcelFile().getName());
		System.out.println("nonLiveUploadModel.getMailPdfFile()=>" + nonLiveUploadModel.getMailPdfFile().getName());

		try {

			// Get SSOID
			String ssoid = this.SsoId;

			// Generate New BartchID.
			String batchId = BatchIDGenaration();

			// PDF Upload to Server.
			uploadToLocalFileSystem(nonLiveUploadModel.getMailPdfFile(), batchId);

			InputStream inputStream = new BufferedInputStream(nonLiveUploadModel.getExcelFile().getInputStream());

			List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntityEntities = excelToEntity(inputStream, batchId,
					nonLiveUploadModel, ssoid);
			service.saveExcelDataToTemp(misNonLiveTrnLogsEntityEntities);

			misNonLiveTrnLogsEntityEntities = service.NonLiveTxnUpload(misNonLiveTrnLogsEntityEntities,
					nonLiveUploadModel.getFromdate(), nonLiveUploadModel.getEnddate(), batchId, ssoid,
					Constant.PDF_PATH + batchId + ".PDF", nonLiveUploadModel.getEmailDate());

			// Get AadhaarID from UUID
//			misNonLiveTrnLogsEntityEntities = getAadhaarFromUUID(misNonLiveTrnLogsEntityEntities);

//			for(MisNonLiveTrnLogsEntity misNonLiveTrnLogsEntity :misNonLiveTrnLogsEntityEntities) {
//				System.out.println(misNonLiveTrnLogsEntity.getUuid());
//				System.err.println(misNonLiveTrnLogsEntity.getAadhaarId());
//			}

			// Block aadhaar ID
			// misNonLiveTrnLogsEntityEntities =
			// service.BlockAadhaar(misNonLiveTrnLogsEntityEntities,nonLiveUploadModel.getFromdate(),nonLiveUploadModel.getEnddate(),batchId,this.SsoId);

			// Sent Email to SUBAUA
			try {
				sendemail.nonLiveSendEmailToSubaua(misNonLiveTrnLogsEntityEntities, nonLiveUploadModel.getFromdate(),
						nonLiveUploadModel.getEnddate(), nonLiveUploadModel.getEmailDate(), batchId);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			redirAttrs.addFlashAttribute("error", "Error In FileUpload.");
			e.printStackTrace();
			return "redirect:/NonLiveExcelUpload_file";
		}
		// model.addAttribute("result",searchResult.get("details"));

		/* redirAttrs.addFlashAttribute("success", "File uploaded successful."); */
		redirAttrs.addFlashAttribute("success", "The Uploaded Aadhaars are Blocked");
		return "redirect:/NonLiveExcelUpload_file";
		// return "/NonLiveExcelUpload_file";
	}

	@PostMapping(value = "/NonLiveUnblockBySearch")
	public String NonLiveUnblockBySearch(Model model,
			@ModelAttribute("NonLiveUnblockBySearch") NonLiveUploadModel nonLiveUploadModel, HttpSession session,
			RedirectAttributes redirAttrs) {

		System.out.println("Entered into searchAvgResponseTimeData in AUTHMIS_NEW");
		// List<MisNonLiveTrnLogsEntity> searchResult = new
		// ArrayList<MisNonLiveTrnLogsEntity>();

		System.out.println("nonLiveUploadModel.getFromdate()==>>>" + nonLiveUploadModel.getFromdate());
		System.out.println("nonLiveUploadModel.getEnddate()==>>>>" + nonLiveUploadModel.getEnddate());
		System.out.println("nonLiveUploadModel.getEmailDate()==>>" + nonLiveUploadModel.getEmailDate());
		System.out.println("nonLiveUploadModel.getExcelFile()==>>" + nonLiveUploadModel.getExcelFile().getName());
		System.out.println("nonLiveUploadModel.getMailPdfFile()=>" + nonLiveUploadModel.getMailPdfFile().getName());

		try {
			// Get SSOID
			String ssoid = "test";

			// Generate New BartchID.
			String batchId = BatchIDGenaration();

			// PDF Upload to Server.
			uploadToLocalFileSystem(nonLiveUploadModel.getMailPdfFile(), batchId);

			// Excel To Entity.
			hasExcelFormat(nonLiveUploadModel.getExcelFile());

			InputStream inputStream = new BufferedInputStream(nonLiveUploadModel.getExcelFile().getInputStream());
			List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntityEntities = excelToEntity(inputStream, batchId,
					nonLiveUploadModel, ssoid);

			// Save Excel to DB logs
			// ,String batchId,String ssoid,String pdfPath,String emailDate
			misNonLiveTrnLogsEntityEntities = service.NonLiveTxnUpload(misNonLiveTrnLogsEntityEntities,
					nonLiveUploadModel.getFromdate(), nonLiveUploadModel.getEnddate(), batchId, ssoid,
					Constant.PDF_PATH + batchId + ".PDF", nonLiveUploadModel.getEmailDate());

			// Get AadhaarID from UUID
			misNonLiveTrnLogsEntityEntities = getAadhaarFromUUID(misNonLiveTrnLogsEntityEntities);

//			for(MisNonLiveTrnLogsEntity misNonLiveTrnLogsEntity :misNonLiveTrnLogsEntityEntities) {
//				System.out.println(misNonLiveTrnLogsEntity.getUuid());
//				System.err.println(misNonLiveTrnLogsEntity.getAadhaarId());
//			}

			// Block aadhaar ID
			// misNonLiveTrnLogsEntityEntities =
			// service.BlockAadhaar(misNonLiveTrnLogsEntityEntities,nonLiveUploadModel.getFromdate(),nonLiveUploadModel.getEnddate(),batchId,this.SsoId);

			// Sent Email to SUBAUA
			try {
				sendemail.nonLiveSendEmailToSubaua(misNonLiveTrnLogsEntityEntities, nonLiveUploadModel.getFromdate(),
						nonLiveUploadModel.getEnddate(), nonLiveUploadModel.getEmailDate(), batchId);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			redirAttrs.addFlashAttribute("error", "Error In FileUpload.");
			e.printStackTrace();
			return "redirect:/NonLiveExcelUpload_file";
		}
		// model.addAttribute("result",searchResult.get("details"));

		redirAttrs.addFlashAttribute("success", "File uploaded successful.");
		return "redirect:/NonLiveExcelUpload_file";
		// return "/NonLiveExcelUpload_file";
	}

	public ResponseEntity uploadToLocalFileSystem(MultipartFile file, String batchId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		System.out.println("filename===>" + fileName);
		// Path path = Paths.get("G:\\upload\\" + batchId);
		Path path = Paths.get(Constant.PDF_PATH + batchId + ".PDF");
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/")
				.path(fileName).toUriString();
		System.out.println("filename===>" + fileDownloadUri);
		return ResponseEntity.ok(fileDownloadUri);
	}

	@Deprecated
	public static List<MisNonLiveTrnLogsEntity> getAadhaarFromUUID_OLD(List<MisNonLiveTrnLogsEntity> nonLivetrnlogs) {

		for (MisNonLiveTrnLogsEntity nonLivetrn : nonLivetrnlogs) {
			try {
				OkHttpClient client = new OkHttpClient().newBuilder().build();
				MediaType mediaType = MediaType.parse("application/xml");
				System.out.println("nonLivetrn.getUuid()" + nonLivetrn.getUuid());
				RequestBody body = RequestBody.create(mediaType, "<AuthRequest  uid=\"" + nonLivetrn.getUuid()
						+ "\" flagType=\" A\" subaua=\"0000440000\" ver=\"2.5\">\r\n</AuthRequest>");
				// RequestBody body = RequestBody.create(mediaType, "<AuthRequest
				// UUID=\""+nonLivetrn.getUuid()+"\" flagType=\" A\" subaua=\"0000440000\"
				// ver=\"2.5\">\r\n</AuthRequest>");
				Request request = new Request.Builder()
						.url("http://10.68.180.114:9081/doit-aadhaar-enc-dec/demo/hsm/auth/detokenizeV2")
						.method("POST", body).addHeader("Content-Type", "application/xml")
						.addHeader("Cookie", "NSC_BbeibsV-JQw4-443=ffffffff094eeffe45525d5f4f58455e445a4a423660")
						.build();

				Response response = client.newCall(request).execute();
				String responseString = response.body().string();
				System.out.println("response UID===" + responseString);

				JAXBContext jaxbContext = JAXBContext.newInstance(DSMTokanize.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(responseString);
				DSMTokanize dSMTokanize = (DSMTokanize) unmarshaller.unmarshal(reader);

				/*
				 * System.out.println("person.getAadhaarId()" + dSMTokanize.getAadhaarNo());
				 * System.out.println("person.getAadhaarId()" + dSMTokanize.getStatus());
				 */
				System.out.println("person.getAadhaarId()" + dSMTokanize.getStatusCode());
				nonLivetrn.setAadhaarId(dSMTokanize.getRefNo());

			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Exception==>>" + e);
			}
		}

		return nonLivetrnlogs;
	}

	public static List<MisNonLiveTrnLogsEntity> getAadhaarFromUUID(List<MisNonLiveTrnLogsEntity> nonLivetrnlogs) {
		CloseableHttpClient client1;
		StringEntity inputStr;
		for (MisNonLiveTrnLogsEntity nonLivetrn : nonLivetrnlogs) {
			try {
				System.out.println("httpClient started");
				client1 = HttpClients.custom()
						.setSSLContext(
								new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
						.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

				// HttpPost post1 = new
				// HttpPost("http://10.68.180.114:9081/doit-aadhaar-enc-dec/demo/hsm/auth/detokenizeV2");
				HttpPost post1 = new HttpPost(Constant.ENC_DEC_URL);

				inputStr = new StringEntity("<AuthRequest  UUID=\"" + nonLivetrn.getUuid()
						+ "\" flagType=\" A\" subaua=\"0000440000\" ver=\"2.5\">\r\n</AuthRequest>");

				post1.setEntity((HttpEntity) inputStr);
				post1.setHeader("Content-Type", "application/xml");
				org.apache.http.HttpResponse resp1 = (org.apache.http.HttpResponse) client1
						.execute((HttpUriRequest) post1);
				BufferedReader rd1 = new BufferedReader(
						new InputStreamReader(((org.apache.http.HttpResponse) resp1).getEntity().getContent()));
				String line1 = "";
				StringBuilder res1 = new StringBuilder();
				while ((line1 = rd1.readLine()) != null) {
					res1.append(line1);
				}
				System.out.println("res1==========>>" + res1);

				String responseString = res1.toString();
				System.out.println("response UID===" + responseString);

				JAXBContext jaxbContext = JAXBContext.newInstance(DSMTokanize.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(responseString);
				DSMTokanize dSMTokanize = (DSMTokanize) unmarshaller.unmarshal(reader);

				//System.out.println("person.getAadhaarId()" + dSMTokanize.getAadhaarNo());
				//System.out.println("person.getAadhaarId()" + dSMTokanize.getStatus());
				System.out.println("person.getAadhaarId()" + dSMTokanize.getStatusCode());
				nonLivetrn.setAadhaarId(dSMTokanize.getRefNo());

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return nonLivetrnlogs;
	}

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			System.out.println("flase");
			return false;
		}
		System.out.println("true");
		return true;
	}

	public List<MisNonLiveTrnLogsEntity> excelToEntity(InputStream is, String batchId,
			NonLiveUploadModel nonLiveUploadModel, String ssoId) {
		int cellIdx = 0;
		int rowNumber = 0;
		try {
			Workbook workbook = new XSSFWorkbook(is);

			// Sheet sheet = workbook.getSheet(SHEET);wb.getSheetAt(0)
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();

			List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntities = new ArrayList<MisNonLiveTrnLogsEntity>();

			// int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				MisNonLiveTrnLogsEntity misNonLiveTrnLogsEntity = new MisNonLiveTrnLogsEntity();

				// int cellIdx = 0;
				cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						misNonLiveTrnLogsEntity.setAuthResponseCode(currentCell.getStringCellValue());
						break;

					case 1:
						if (null != currentCell.getStringCellValue()
								&& currentCell.getStringCellValue().equalsIgnoreCase("440000")) {
							misNonLiveTrnLogsEntity.setSa("0000440000");
						} else {
							misNonLiveTrnLogsEntity.setSa(currentCell.getStringCellValue());
						}
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
//						uuid = service.getUuidFromTxn(currentCell.getStringCellValue());
//						misNonLiveTrnLogsEntity.setUuid(uuid);
						misNonLiveTrnLogsEntity.setFromDate(nonLiveUploadModel.getFromdate());
						misNonLiveTrnLogsEntity.setToDate(nonLiveUploadModel.getEnddate());
						misNonLiveTrnLogsEntity.setEmailDate(nonLiveUploadModel.getEmailDate());
						misNonLiveTrnLogsEntity.setBatchId(batchId);
						misNonLiveTrnLogsEntity.setSsoId(ssoId);
						misNonLiveTrnLogsEntity.setPdfPath(Constant.PDF_PATH + batchId + ".PDF");

						break;

					default:
						break;
					}

					cellIdx++;
				}
				// System.out.println("misNonLiveTrnLogsEntity==>>"+misNonLiveTrnLogsEntity);
				misNonLiveTrnLogsEntities.add(misNonLiveTrnLogsEntity);
			}

			workbook.close();

			return misNonLiveTrnLogsEntities;
		} catch (IOException e) {
			throw new RuntimeException("Error at Row==" + rowNumber + "and colom==" + cellIdx
					+ "fail to parse Excel file: " + e.getMessage());
		}
	}

	public String BatchIDGenaration() {
		Random random = new Random();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return "UIDRAJNL" + dateFormat.format(Calendar.getInstance().getTime());
	}

	public List<MisNonLiveTrnLogsEntity> saveRecordsInBatches(
			List<MisNonLiveTrnLogsEntity> misNonLiveTrnLogsEntityEntities, int batchSize) {
		int totalRecords = misNonLiveTrnLogsEntityEntities.size();
		int batches = (totalRecords + batchSize - 1) / batchSize; // Calculate the number of batches
		List<MisNonLiveTrnLogsEntity> currentBatch = null;
		for (int batchIndex = 0; batchIndex < batches; batchIndex++) {
			int fromIndex = batchIndex * batchSize;
			int toIndex = Math.min(fromIndex + batchSize, totalRecords);

			currentBatch = misNonLiveTrnLogsEntityEntities.subList(fromIndex, toIndex);

		}
		return currentBatch;
	}

}
