package com.gov.Authmis.controller;

import com.google.gson.Gson;
import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.dao.SendEmail;
import com.gov.Authmis.entity.SsoUser;
import com.gov.Authmis.entity.SubAuaAdmin;
import com.gov.Authmis.model.AverageResponseTimeModel;
import com.gov.Authmis.model.DashBoardEntity;
import com.gov.Authmis.model.ModuleMaster;
import com.gov.Authmis.model.ResultSetToListConverter;
import com.gov.Authmis.service.DashBoardService;
import com.gov.Authmis.service.SubAuaAdminService;
import com.gov.Authmis.service.SubauaWiseTransactionService;
import com.gov.Authmis.util.DepartmentWiseTransaction;
import com.gov.Authmis.util.DeviceWisePercentage;
import com.gov.Authmis.util.ErrorCodeAvgResp;
import com.gov.Authmis.util.HoursWiseTrans;
import com.gov.Authmis.util.UniqueSuccessFailGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashBoardController {
	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	public SendEmail sendemail;
	@Autowired
	SubAuaAdminService subAuaAdminService;
	SSOLoginController ssoLoginController = new SSOLoginController();
	Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	
	public String getSSOSession(String token) {
		CloseableHttpClient client1;
		StringEntity inputStr;
		try {
			System.out.println("httpClient started");
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
			System.out.println("res1==========>>" + res1);

			Gson g = new Gson();
			SsoUser s = g.fromJson(res1.toString(), SsoUser.class);
			System.out.println("s.getsAMAccountName()==========>>" + s.getsAMAccountName());
			System.out.println("s.getRoles()==========>>" + s.getRoles());
			System.out.println("s.getUserType()==========>>" + s.getUserType());
			System.out.println("s.getOldSSOIDs()==========>>" + s.getOldSSOIDs());

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

	@PersistenceContext
	EntityManager entityManager;

	@RequestMapping({ "/" })
	// @PostMapping({"/"})
	@Transactional
	public String index(Model model, HttpServletRequest request,HttpSession session) throws SQLException {

		// Validate SSO Login

		logger.info("DashBoardController==> Inside the method1");
		boolean sessionoutput = ssoLoginController.validateSSOSession(request);
		if (!sessionoutput) {
			logger.info("DashBoardController==> Inside the method2");
			request.getSession().invalidate();
			return "redirect:" + Constant.SSO_URL;
		}

		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		model.addAttribute("SSOID", ssoid1);
		String str = "select role_name from aadhaar.MIS_ROLE_MASTER rm inner join  aadhaar.MIS_USER_DETAILS ud on rm.role_id = ud.role_id where lower(ssoid) =lower('"
				+ ssoid1 + "') and ud.status = 1 fetch first rows only";
		Query query1 = entityManager.createNativeQuery(str);
		@SuppressWarnings("unchecked")
		List<String> role_id1 = query1.getResultList();
		String str1 = role_id1.toString();
		String str2 = str1.replaceAll("[\\[\\]]", "");
		model.addAttribute("role_id", str2);
		String str3 = "select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where lower(sso_id)=lower('" + ssoid1
				+ "') and status=1";
		Query query2 = entityManager.createNativeQuery(str3);
		@SuppressWarnings("unchecked")
		List<String> role_id3 = query2.getResultList();
		String str5 = role_id3.toString().replaceAll("[\\[\\]]", "");
		model.addAttribute("role", str5);
		session.setAttribute("roleId", str5);
		HashMap<String, Object> result = new HashMap<>();

		/*
		 * DashBoardEntity objDashBoardEntity = new DashBoardEntity();
		 * 
		 * ArrayList<String> dListORG = new ArrayList<>(); ArrayList<Integer> dListCount
		 * = new ArrayList<>(); ArrayList<String> dListHours = new ArrayList<>();
		 * ArrayList<Integer> dListHourCount = new ArrayList<>(); ArrayList<String>
		 * dListRequestDesc = new ArrayList<>(); ArrayList<Integer> dListTotal = new
		 * ArrayList<>(); ArrayList<Double> dListPerce = new ArrayList<>();
		 * ArrayList<String> dListSForg = new ArrayList<>(); ArrayList<Integer>
		 * dListtSuccess = new ArrayList<>(); ArrayList<Integer> dListFailed = new
		 * ArrayList<>();
		 * 
		 * this.dashBoardService.callStoredProcedure(ssoid1, str5);
		 * List<DepartmentWiseTransaction> departmentWiseTransaction =
		 * this.dashBoardService.GetDashBoardResult(); List<DeviceWisePercentage>
		 * deviceWisePercentageResult =
		 * this.dashBoardService.GetDeviceWisePercentResult(); List<HoursWiseTrans>
		 * hoursWiseTransResult = this.dashBoardService.GetHoursWiseTransResult();
		 * List<ErrorCodeAvgResp> ErrorCodeAvgRespResult =
		 * this.dashBoardService.GetErrorCodeAvgRespResult(); List<ErrorCodeAvgResp>
		 * AvgRespResult = this.dashBoardService.GetAvgRespResult();
		 * List<UniqueSuccessFailGraph> UniqueSuccessFailGraphResult =
		 * this.dashBoardService .GetUniqueSuccessFailGraphResult(); int i; for (i = 0;
		 * i < departmentWiseTransaction.size(); i++) { DepartmentWiseTransaction
		 * dptWiseTranObj = departmentWiseTransaction.get(i); if (i <
		 * departmentWiseTransaction.size() - 1) {
		 * dListCount.add(Integer.valueOf(Integer.parseInt(dptWiseTranObj.
		 * getCurentOrgDay()))); dListORG.add("[\"" + dptWiseTranObj.getOrgName() +
		 * "\"]"); } else {
		 * dListCount.add(Integer.valueOf(Integer.parseInt(dptWiseTranObj.
		 * getCurentOrgDay()))); dListORG.add("[\"" + dptWiseTranObj.getOrgName() +
		 * "\"]"); } } model.addAttribute("dListORG", dListORG);
		 * model.addAttribute("dListCount", dListCount); for (i = 0; i <
		 * deviceWisePercentageResult.size(); i++) { DeviceWisePercentage
		 * deviceWisePerObj = deviceWisePercentageResult.get(i); if (i <
		 * deviceWisePercentageResult.size() - 1) { dListRequestDesc.add("\"" +
		 * deviceWisePerObj.getReqtDescDevice() + "\"");
		 * dListTotal.add(Integer.valueOf(Integer.parseInt(deviceWisePerObj.
		 * getTotalReqtDevice())));
		 * dListPerce.add(Double.valueOf(Double.parseDouble(deviceWisePerObj.
		 * getPerceReqtDevice()))); } else { dListRequestDesc.add("\"" +
		 * deviceWisePerObj.getReqtDescDevice() + "\"");
		 * dListTotal.add(Integer.valueOf(Integer.parseInt(deviceWisePerObj.
		 * getTotalReqtDevice())));
		 * dListPerce.add(Double.valueOf(Double.parseDouble(deviceWisePerObj.
		 * getPerceReqtDevice()))); } } model.addAttribute("dListRequestDesc",
		 * dListRequestDesc); model.addAttribute("dListTotal", dListTotal);
		 * model.addAttribute("dListPerce", dListPerce); for (i = 0; i <
		 * hoursWiseTransResult.size(); i++) { HoursWiseTrans hourTransObj =
		 * hoursWiseTransResult.get(i); if (i < hoursWiseTransResult.size() - 1) {
		 * dListHours.add(hourTransObj.getHourReecod());
		 * dListHourCount.add(Integer.valueOf(Integer.parseInt(hourTransObj.
		 * getCountHourRecord()))); } else {
		 * dListHours.add(hourTransObj.getHourReecod());
		 * dListHourCount.add(Integer.valueOf(Integer.parseInt(hourTransObj.
		 * getCountHourRecord()))); } } model.addAttribute("dListHours", dListHours);
		 * model.addAttribute("dListHourCount", dListHourCount);
		 * System.out.println("dListHours" + dListHours);
		 * System.out.println("dListHourCount" + dListHourCount); ArrayList<String>
		 * dListTime = new ArrayList<>(); ArrayList<Integer> dListtTotalTrans = new
		 * ArrayList<>(); ArrayList<Integer> dListError = new ArrayList<>();
		 * ArrayList<String> dListAvergTime = new ArrayList<>(); int j; for (j = 0; j <
		 * AvgRespResult.size(); j++) { ErrorCodeAvgResp AvgRespResultObj =
		 * AvgRespResult.get(j); dListTime.add(AvgRespResultObj.getTime());
		 * dListAvergTime.add(AvgRespResultObj.getAvergTime()); } for (j = 0; j <
		 * ErrorCodeAvgRespResult.size(); j++) { ErrorCodeAvgResp
		 * ErrorCodeAvgResprTransObj = ErrorCodeAvgRespResult.get(j);
		 * dListtTotalTrans.add(Integer.valueOf(Integer.parseInt(
		 * ErrorCodeAvgResprTransObj.getTotalTrans())));
		 * dListError.add(Integer.valueOf(Integer.parseInt(ErrorCodeAvgResprTransObj.
		 * getError()))); } int count = 0; String tempVal = ""; int k; for (k =
		 * dListTime.size() - 1; k >= 0; k--) { if (k == dListTime.size() - 1) {
		 * count++; tempVal = dListTime.get(k); } else if
		 * (!tempVal.equalsIgnoreCase(dListTime.get(k))) { count++; tempVal =
		 * dListTime.get(k); } } for (k = 0; k < UniqueSuccessFailGraphResult.size();
		 * k++) { UniqueSuccessFailGraph UniqueSuccessFailGraphrTransObj =
		 * UniqueSuccessFailGraphResult.get(k); if (k <
		 * UniqueSuccessFailGraphResult.size() - 1) { dListSForg.add("[\"" +
		 * UniqueSuccessFailGraphrTransObj.getOrgNameGra() + "\"]"); dListtSuccess
		 * .add(Integer.valueOf(Integer.parseInt(UniqueSuccessFailGraphrTransObj.
		 * getUniSuccOrgGra()))); dListFailed.add(Integer.valueOf(Integer.parseInt(
		 * UniqueSuccessFailGraphrTransObj.getUniFailOrgGra()))); } else {
		 * dListSForg.add(UniqueSuccessFailGraphrTransObj.getOrgNameGra());
		 * dListtSuccess
		 * .add(Integer.valueOf(Integer.parseInt(UniqueSuccessFailGraphrTransObj.
		 * getUniSuccOrgGra()))); dListFailed.add(Integer.valueOf(Integer.parseInt(
		 * UniqueSuccessFailGraphrTransObj.getUniFailOrgGra()))); } }
		 * model.addAttribute("dListSForg", dListSForg);
		 * model.addAttribute("dListtSuccess", dListtSuccess);
		 * model.addAttribute("dListFailed", dListFailed);
		 * System.out.println("dListSForg" + dListSForg);
		 * System.out.println("dListtSuccess" + dListtSuccess);
		 * System.out.println("dListFailed" + dListFailed);
		 */

		result = this.dashBoardService.GetResult(ssoid1, str5);
		JSONObject jsonobj = new JSONObject(result);
		try {
		    JSONArray jsonarray1 = jsonobj.getJSONArray("Details");
		    //JSONArray jsonarray2 = jsonobj.getJSONArray("details2");
		    
		    // Assuming objDashBoardEntity is properly initialized
		    DashBoardEntity objDashBoardEntity1 = new DashBoardEntity();
		    
		    // Handle the first array (details1)
//		    if (jsonarray1.length() > 0) {
//		        JSONObject jsonobject1 = jsonarray1.getJSONObject(0);
//		        objDashBoardEntity1.setCNT_SUBAUA(jsonobject1.optString("CNT_SUBAUA"));
//		    }
		    
		    // Handle the second array (details2)
		    if (jsonarray1.length() > 0) {
		        
		        objDashBoardEntity1.setCURENT_DAY_TRANSACTION(jsonarray1.getInt(2));
		        objDashBoardEntity1.setCURENT_MONTH_TRANSACTION(jsonarray1.getInt(1));
		        objDashBoardEntity1.setCURENT_YEAR_TRANSACTION(jsonarray1.getInt(0));
		    }
		    
		    model.addAttribute("dashBoardResult", objDashBoardEntity1);
		    
		    @SuppressWarnings("unused")
			List<String> result1 = new ArrayList<>();

			result1 = this.dashBoardService.getsubauadata(ssoid1,str5);
			String result2 = result1.toString().replaceAll("[\\[\\]]", "");
			System.out.println(result2);
			model.addAttribute("result2", result2);
		    
		} catch (JSONException e) {
		    logger.info("DashBoardController==> Exception.... " + e);
		}
		return "index";
	}

	@RequestMapping("/getsubmenuname")
	public @ResponseBody Object submenuname(@RequestParam("menu_id") String menu_id, HttpServletRequest request,
			Model model) throws SQLException {

		String ssoid1 = (String) request.getSession().getAttribute("SSOID");
		String str3 = "select role_id from AADHAAR.SSO_USER_ROLES_MAPPING where sso_id=lower('" + ssoid1
				+ "') and status=1";
		Query query2 = entityManager.createNativeQuery(str3);
		@SuppressWarnings("unchecked")
		List<String> role_id3 = query2.getResultList();
		String str5 = role_id3.toString().replaceAll("[\\[\\]]", "");

		List<String> data = new ArrayList<>();
		List<String> data1 = new ArrayList<>();
		StoredProcedureQuery sql = this.entityManager.createStoredProcedureQuery("AADHAAR.GET_SUBMENU")
				.registerStoredProcedureParameter("p_role_id", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("p_menuid", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("P_RECORDSET", ResultSet.class, ParameterMode.REF_CURSOR);
		sql.setParameter("p_role_id", str5).setParameter("p_menuid", menu_id);
		sql.execute();

		ResultSet DetailsObj = (ResultSet) sql.getOutputParameterValue("P_RECORDSET");
		ModuleMaster listResult = new ModuleMaster();
	
		Map<String, String> submenuMap = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order
		while (DetailsObj.next()) {
			String submenuName = DetailsObj.getString(2);
			String submenuUrl = DetailsObj.getString(4);
			submenuMap.put(submenuUrl, submenuName);
		}

		
		model.addAttribute("listofsubmenu", submenuMap);
		
		System.out.println(submenuMap + "hhjhjjjgfhjdfhjdhjgdgjgj");
		

		return submenuMap;

	}

}
