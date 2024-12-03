package com.gov.Authmis.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.SendEmailToSubAuaModel;
import com.gov.Authmis.service.SendMailToSubAuaService;
import com.gov.Authmis.util.FileStorageException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class SendMailToSubAuaServiceImpl implements SendMailToSubAuaService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<SendEmailToSubAuaModel> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> GetSubauaCodeName() {
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.GET_MIS_SUBAUACODE_AND_NAME")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> subauaList = ResultSetToListConverter.getListFromResultSet(rs1);
		return subauaList;
	}

	public List<Map<String, Object>> GetServiceType() {
		StoredProcedureQuery query1 = this.em.createStoredProcedureQuery("AADHAAR.GET_MIS_REQUEST_TYPE")
				.registerStoredProcedureParameter("PRC", ResultSet.class, ParameterMode.REF_CURSOR);
		ResultSet rs1 = (ResultSet) query1.getOutputParameterValue("PRC");
		List<Map<String, Object>> servieTypeList = ResultSetToListConverter.getListFromResultSet(rs1);
		return servieTypeList;
	}

	public List<SendEmailToSubAuaModel> storeFile(MultipartFile file) throws SerialException, SQLException {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String fileType = StringUtils.cleanPath(file.getContentType());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			byte[] data = file.getBytes();

			SendEmailToSubAuaModel dbFile = new SendEmailToSubAuaModel();
			dbFile.setFileName(new String[] { fileName });
			// dbFile.setFileType(file.getContentType());
			dbFile.setFileType(new String[] { fileType });
			// dbFile.setData(data);
			Blob my_blob = new SerialBlob(data);
			List<SendEmailToSubAuaModel> fileList = new ArrayList<>();
			fileList.add(dbFile);

			return fileList;

		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Transactional
	public Object save(SendEmailToSubAuaModel sendEmailToSubAuaModel, List<SendEmailToSubAuaModel> uploadedFiles)
			throws IOException, SerialException, SQLException, DocumentException {

		String authData = null;
		String ip = "";
		InetAddress Ip = InetAddress.getLocalHost();
		ip = Ip.toString();
		System.out.println("========================>>>>>>>>>>>>>>>" + uploadedFiles);

		// ===========================================content converted into PDF==================================
		String content = sendEmailToSubAuaModel.getContent();
		byte[] certificateBytes_Content = null;

	

		if(!content.isEmpty()) {
		//code for encode
		byte[] contentBytes = content.getBytes();
		String d = Base64.getEncoder().encodeToString(contentBytes);
		certificateBytes_Content = d.getBytes();
		System.out.println("Encode====================>>>>>>>>>>>" + d);
		
		
		
		 
		}
		
		
		// ====================================================================================================

		String query1 = "INSERT INTO AADHAAR.SEND_MAILS_To_SUBAUA_LOGS_DATA( SUB_AUA_CODE, TO_EMAIL, TO_CC, SUBJECT, SEND_EMAIL_DATE, IP,STATUS, EMAIL_CONTENT)"
				+ " VALUES ( :a, :b, :c, :d, :e, :f, :g, :h)";

		Object o1 = em.createNativeQuery(query1).setParameter("a", sendEmailToSubAuaModel.getSubAuaCode())
				.setParameter("b", sendEmailToSubAuaModel.getTo()).setParameter("c", sendEmailToSubAuaModel.getCc())
				.setParameter("d", sendEmailToSubAuaModel.getSubject())
				.setParameter("e", sendEmailToSubAuaModel.getSend_email_date()).setParameter("f", ip)
				.setParameter("g", "1").setParameter("h", certificateBytes_Content)

				.executeUpdate();

		String sql = "SELECT MAX(SRNO) FROM AADHAAR.SEND_MAILS_TO_SUBAUA_LOGS_DATA";
		Query q = em.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Long> sr = q.getResultList();
		

		//check file data is null or not
		if (uploadedFiles.size() == 0) {

			String query2 = "INSERT INTO AADHAAR.EMAIL_FILE_DATA( SUB_AUA_CODE, SRNO, FILENAME, FILETYPE, FILE_DATA, URI)"
					+ " VALUES ( :a, :b, :c, :d, :e, :f)";

			Object o2 = em.createNativeQuery(query2)
					    .setParameter("a", sendEmailToSubAuaModel.getSubAuaCode())
					    .setParameter("b", sr)
					    .setParameter("c", null)
					    .setParameter("d", null)
					    .setParameter("e", null)
					    .setParameter("f", null)
					    .executeUpdate();
		}

		else {
			for (int i = 0; i < uploadedFiles.size(); i++) {

				String str2 = "";
				String str3 = "";
				String str34 = "";
				String str10 = "";
				String str11 = "";

				// SerialBlob certificateBlob = null;
				Blob certificateBlob = null;
				if (uploadedFiles.get(i).getFileName().length != 0) {
					// file name
					String[] s = uploadedFiles.get(i).getFileName();
					String str1 = String.join(",", s);
					str2 = str1.replaceAll("[\\[\\]]", "");
					str3 = str2.toString();
				}

				if (uploadedFiles.get(i).getUri().length != 0) {
					// file URI
					String[] a = uploadedFiles.get(i).getUri();
					String str = String.join(",", a);
					str2 = str.replaceAll("[\\[\\]]", "");
					str34 = str2.toString();
				}

				if (uploadedFiles.get(i).getFileType().length != 0) {
					// file Type
					String[] e = uploadedFiles.get(i).getFileType();
					String str9 = String.join(",", e);
					str10 = str9.replaceAll("[\\[\\]]", "");
					str11 = str10.toString();
				}

				if (uploadedFiles.get(i).getData().length != 0) {
					// file data
					byte[] fileData = uploadedFiles.get(i).getData();
					String b = Base64.getEncoder().encodeToString(fileData);
					authData = new String(fileData);
					byte[] certificateBytes = b.getBytes();

					try (InputStream inputStream = new ByteArrayInputStream(certificateBytes)) {
						// Create a Blob object from the ByteArrayInputStream
						certificateBlob = new SerialBlob(certificateBytes);
					}

				}

				String query2 = "INSERT INTO AADHAAR.EMAIL_FILE_DATA( SUB_AUA_CODE, SRNO, FILENAME, FILETYPE, FILE_DATA, URI)"
						+ " VALUES ( :a, :b, :c, :d, :e, :f)";

				Object o2 = em.createNativeQuery(query2)
						.setParameter("a", sendEmailToSubAuaModel.getSubAuaCode())
						.setParameter("b", sr)
						.setParameter("c", str3)
						.setParameter("d", str11)
						.setParameter("e", certificateBlob)

						.setParameter("f", str34)

						.executeUpdate();
			}
		}

		return o1;

	}

//----------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public String[] getSubauaCode1(List<String> SUBAUA_CODE) {

		String sql = null;
		List<String> da = new ArrayList<String>();

		String temp = "";
		for (int i = 0; i <= SUBAUA_CODE.size() - 1; i++) {
			if (i == 0) {
				temp = temp + "'" + SUBAUA_CODE.get(i) + "'";
			} else {
				temp = temp + "," + "'" + SUBAUA_CODE.get(i) + "'";
			}
		}
		sql = " select EMAIL from AADHAAR.subaua WHERE SUBAUA_CODE IN(" + temp + ") ";
		Query query = this.em.createNativeQuery(sql);

		da = query.getResultList();
		String k[] = da.toArray(new String[da.size()]);
		return k;
	}

	@Override
	public List<Map<String, Object>> GetEmail() {
		// TODO Auto-generated method stub
		return null;
	}

}
