package com.gov.Authmis.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.rmi.ServerException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.SubAuaAdmin;
import com.gov.Authmis.model.AddLicencyForSubAuaSystem;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.model.UsersRoleMapping;
import com.gov.Authmis.service.SubAuaAdminService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class UsersRoleMappingController {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SubAuaAdminService subauaadminservice;
	/*
	 * @Autowired private RestTemplate restTemplate;
	 */
	@Autowired(required = true)
	ValidateSSO validateSSO;
	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;
	String uri = "https://sso.rajasthan.gov.in:4443/SSOREST/GetUserDetailJSON";
	SSOLoginController ssoLoginController = new SSOLoginController();

	@RequestMapping({ "/subauaadminpage" })
	public String subauaadminpage(HttpServletRequest request, Model model,
			@ModelAttribute("subauaadminpage") UsersRoleMapping subAuaAdmin, RedirectAttributes redirectAttributes) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		model.addAttribute("subauaadminpage", new UsersRoleMapping());

		redirectAttributes.addFlashAttribute("success", "Inserted Successfully ");
		return "userrolemapping";

	}

	@RequestMapping(value = "/user")
	public String getUserProfile(@RequestParam("SSOID") String ssoid, HttpServletRequest request,
			@ModelAttribute("subauaadminpage") UsersRoleMapping ssoUserProfile, Model model)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, SQLException {
		
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		CloseableHttpClient client1;
		StringEntity inputStr;
		try {
			System.out.println("getUserProfile started...");

			client1 = HttpClients.custom()
					.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

			HttpPost post1 = new HttpPost(Constant.SSO_GET_USER_PROFILE);
			inputStr = new StringEntity(
					"{ \"SSOID\":\"" + ssoid + "\",\"WSUSERNAME\":\"UMS.TEST\",\"WSPASSWORD\":\"UmFqYXN0aGFuQDE5\" }");
			System.out.println("inputStr===>>" + inputStr);

			post1.setEntity(inputStr);
			post1.setHeader("Content-Type", "application/json");
			System.out.println("Going to call service.....1");
			HttpResponse resp1 = client1.execute(post1);
			BufferedReader rd1 = new BufferedReader(new InputStreamReader(resp1.getEntity().getContent()));
			System.out.println("Going to call service.....2resp1===>" + resp1);
			String line1 = "";
			StringBuilder res1 = new StringBuilder();
			while ((line1 = rd1.readLine()) != null) {
				res1.append(line1);
			}
			System.out.println("res1==========>>" + res1);

			Gson g = new Gson();
			ssoUserProfile = g.fromJson(res1.toString(), UsersRoleMapping.class);

			System.out.println("SsoUserProfile==>>>>1" + ssoUserProfile);
			System.out.println("SsoUserProfile==>>>>1" + ssoUserProfile.getAadhaarId());

			model.addAttribute("aadhaar", ssoUserProfile.getAadhaarId());
			model.addAttribute("displayname", ssoUserProfile.getDisplayName());
			model.addAttribute("designation", ssoUserProfile.getDesignation());
			model.addAttribute("employeenumber", ssoUserProfile.getEmployeeNumber());
			model.addAttribute("mailoff", ssoUserProfile.getMailOfficial());
			model.addAttribute("mobile", ssoUserProfile.getMobile());

//			@SuppressWarnings("unchecked")
//			String token = (String) request.getSession().getAttribute("TOKEN");
//			System.out.println("Enterecd into process=========ssoid   " + ssoid1);
			model.addAttribute("ssoid", ssoid);
			System.out.println("Enterecd into process=========ssoid1   " + ssoid);

			List<Map<String, Object>> subAuaList = this.subauaadminservice.GetSubauaCodeName();
			model.addAttribute("subAuaList", subAuaList);
			List<Map<String, Object>> subAuaList1 = this.subauaadminservice.getDataBySubaAua();
			model.addAttribute("subAuaList1", subAuaList1);

			// ssoUserProfile.setJpegPhoto("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");
			return "userrolemapping2";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String res11 = res1.toString();
		return "userrolemapping2";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/subauaaaa")
	@Transactional
	public String createTutorial1(@ModelAttribute("subauaadminpage") UsersRoleMapping ssoUserProfile, BindingResult bb,
			Model m, RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServerException, SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		List<Object> list = new ArrayList<Object>();
		String ssoid1 = (String) request.getSession().getAttribute("SSOID");

	//	validateSSO.getValidateSSO(request);
			String sql="select * from AADHAAR.SSO_USER_ROLES_MAPPING where SSO_ID='"+ssoUserProfile.getSSOID()+"' and status=1";
			Query query1 = this.entityManager.createNativeQuery(sql); 
	 		list =	query1.getResultList();
	 		if(!list.isEmpty()) {
	 		redirectAttributes.addFlashAttribute("error", "SSOID  already exists");
		}
		else {
			m.addAttribute("subauaadminpage", ssoUserProfile);
			//redirectAttributes.addFlashAttribute("success", "User Mapping done successfully");
			Object tutorial = subauaadminservice.save(ssoUserProfile,ssoid1);
			if (tutorial == null) {
	            redirectAttributes.addFlashAttribute("error", "Server Side Error");
	        } else {
	            redirectAttributes.addFlashAttribute("success", "User Mapping done successfully");
	        }
		} 
		return "redirect:/subauaadminpage";

	}
}
