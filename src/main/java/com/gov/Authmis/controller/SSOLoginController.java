package com.gov.Authmis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.SsoUser;
import com.gov.Authmis.model.SSOIDUserDetails;

@Controller
public class SSOLoginController {
	static Logger logger = LoggerFactory.getLogger(SSOLoginController.class);
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("null")
	@PostMapping(value = "/ssoLogin")
	public String ssoLogin(@RequestBody String requestbody, HttpSession session) throws SQLException {
		 RedirectAttributes redirectAttributes = null;
		/* ; P_RECORDSET ; */
		List<SSOIDUserDetails> data = new ArrayList<>();
		System.out.println("Inside ssoLogin Method");
		System.out.println("requestBody==" + requestbody);
		String[] params = requestbody.split("=");
		System.out.println("SSO Token==" + params[1]);
		String ssoId = getSSOSession(params[1]);
		System.out.println("Enterecd into process=========ssoId" + ssoId);
		StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_ALL_USER_DETAILS")
				.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
		query.execute();

		@SuppressWarnings("unused")
		ResultSet DetailsObj = (ResultSet) query.getOutputParameterValue("P_RECORDSET");
		String auth = "";
		  while (DetailsObj.next()) {
			  if (ssoId.equalsIgnoreCase( DetailsObj.getString(4).toString())) {
				  auth = ssoId;
				  break;
			  }
	      } 
		  
		  if(auth != null  && auth != "") {
			  
					// HttpSession session = new HttpSession();
					List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
					System.out.println("Enterecd into process=========1");

					if (messages == null) {
						messages = new ArrayList<>();
						session.setAttribute("SSOID", ssoId);
						session.setAttribute("TOKEN", params[1]);
						return "redirect:/";
						// return "index";
					}
					
					
			}else {
				/*
				 * redirectAttributes.addFlashAttribute("error",
				 * "Authentication Error..User is not Mapped");
				 */
				return "redirect:/BackToSSO";
			}
			
		  
		  
		  
		/* P_RECORDSET */

		/*
		 * System.out.println("Inside ssoLogin Method");
		 * System.out.println("requestBody==" + requestbody); String[] params =
		 * requestbody.split("="); System.out.println("SSO Token==" + params[1]); String
		 * ssoId = getSSOSession(params[1]);
		 * System.out.println("Enterecd into process=========ssoId" + ssoId);
		 */

		/*if (null != ssoId && ssoId != "") {
			// HttpSession session = new HttpSession();
			List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
			System.out.println("Enterecd into process=========1");

			if (messages == null) {
				messages = new ArrayList<>();
				session.setAttribute("SSOID", ssoId);
				session.setAttribute("TOKEN", params[1]);
				return "redirect:/";
				// return "index";
			}

		} */
		  return "NonLiveExcelUpload_file";

	}

	// @GetMapping("/BackToSSO")
	// public RedirectView redirect(@RequestParam Map<String,String>
	// input,HttpServletRequest request){
	// System.out.println(input);
	// String token = (String) request.getSession().getAttribute("TOKEN");
	// System.out.println("In if block and token="+token);
	// //request.getSession().invalidate();
	// RedirectView redirectView = new RedirectView();
	// redirectView.setUrl(Constant.BACK_TO_SSO);
	// redirectView.setAttributes("userdetails="+token);
	// return redirectView;
	// }

	@RequestMapping(value = "/BackToSSO", method = RequestMethod.GET)
	// @GetMapping("/BackToSSO")
	public String BackToSSO(HttpServletRequest request) {
		logger.info("SSOLoginController===>Entered into BackTOSSO block.");
		String token = (String) request.getSession().getAttribute("TOKEN");
		if (null != token && token != "") {
			logger.info("SSOLoginController===>In if block and token=" + token);
			request.getSession().invalidate();
			String redirecturl = "redirect:" + Constant.BACK_TO_SSO + "?userdetails=" + token;
			logger.info("SSOLoginController===>redirecturl" + redirecturl);
			return redirecturl;
		} else {
			System.out.println("In else  block token==" + token);
			request.getSession().invalidate();
			return "redirect:" + Constant.SSO_LOGOUT;
		}
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String Logout(HttpServletRequest request) {
		logger.info("SSOLoginController===>Entered into Logout block.");
		String token = (String) request.getSession().getAttribute("TOKEN");
		if (null != token && token != "") {
			String redirecturl = "redirect:" + Constant.SSO_LOGOUT + "?userdetails=" + token;
			
			logger.info("SSOLoginController===>redirecturl" + redirecturl);
			request.getSession().invalidate();
			return redirecturl;
		} else {
			System.out.println("In else  block token==" + token);
			request.getSession().invalidate();
			return "redirect:" + Constant.SSO_LOGOUT;
		}
	}

	public boolean validateSSOSession(HttpServletRequest request) {

		@SuppressWarnings("unchecked")
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		String token = (String) request.getSession().getAttribute("TOKEN");
		logger.info("SSOLoginController===>Enterecd into process=========2");

		if (token != null) {
			logger.info("SSOLoginController===>token is not null");
			String ssoId = getSSOSession(token);
			request.getSession().setAttribute("SSOID", ssoId);
			if (null != ssoid && ssoid != "") {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	@Deprecated
	public String getSSOSession_OLD(String token) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<SsoUser> responseEntity = restTemplate.getForEntity(Constant.SSO_GET_TOKEN_DETAILS_URL + token,
				SsoUser.class);
		SsoUser responseBody = responseEntity.getBody();
		logger.info("SSOLoginController===>responseBody.getsAMAccountName()==>" + responseBody.getsAMAccountName());
		return responseBody.getsAMAccountName();
	}

	public String getSSOSession(String token) {
		CloseableHttpClient client1;
		StringEntity inputStr;
		try {
			logger.info("SSOLoginController===>httpClient started");
			client1 = HttpClients.custom()
					.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

			HttpGet get1 = new HttpGet(Constant.SSO_GET_TOKEN_DETAILS_URL + token);
			org.apache.http.HttpResponse resp1 = (org.apache.http.HttpResponse) client1.execute((HttpUriRequest) get1);
			BufferedReader rd1 = new BufferedReader(
					new InputStreamReader(((org.apache.http.HttpResponse) resp1).getEntity().getContent()));
			String line1 = "";
			StringBuilder res1 = new StringBuilder();
			while ((line1 = rd1.readLine()) != null) {
				res1.append(line1);
			}
			logger.info("SSOLoginController===>res1==========>>" + res1);
			Gson g = new Gson();
			SsoUser s = g.fromJson(res1.toString(), SsoUser.class);
			
			logger.info("SSOLoginController===>s.getsAMAccountName()==========>>" + s.getsAMAccountName());
			logger.info("SSOLoginController===>s.getRoles()==========>>" + s.getRoles());
			logger.info("SSOLoginController===>s.getUserType()==========>>" + s.getUserType());
			logger.info("SSOLoginController===>s.getOldSSOIDs()==========>>" + s.getOldSSOIDs());
			return s.getsAMAccountName();
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
		}
		// String res11 = res1.toString();
		return null;
	}

}
