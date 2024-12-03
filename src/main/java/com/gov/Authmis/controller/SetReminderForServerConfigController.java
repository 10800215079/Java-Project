package com.gov.Authmis.controller;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gov.Authmis.model.SetReminderForServerConfigModel;
import com.gov.Authmis.service.SetReminderForServerConfigService;
import com.gov.Authmis.util.MainMenuUtil;
import com.gov.Authmis.util.ValidateSSO;

@Controller
public class SetReminderForServerConfigController {

	Logger logger = LoggerFactory.getLogger(SetReminderForServerConfigController.class);

	@Autowired(required = true)
	ValidateSSO validateSSO;

	@Autowired(required = true)
	MainMenuUtil mainMenuUtil;

	@Autowired
	SetReminderForServerConfigService setRemainderForServerConfigService;

	@GetMapping({ "/addDetailsForRemainder" })
	public String getAllRemainderDetails(Model model, HttpServletRequest request) throws SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoId = (String) request.getSession().getAttribute("SSOID");

		logger.info("========>>>>>>SetReminderForServerConfigController");

		/*
		 * List<String> projectList =
		 * this.setRemainderForServerConfigService.GetProjectName();
		 * model.addAttribute("projectList", projectList);
		 */

		// get all data in table
		HashMap<String, Object> result = new HashMap<>();
		result = setRemainderForServerConfigService.getAllRemainders();
		model.addAttribute("remainders", result.get("remainders"));

		return "SetReminderForServerConfig";
	}

	@PostMapping("/saveProjectDetailsForRemainder")
	public String saveProjectDetailsForRemainder(
			@ModelAttribute("setRemainderForServerConfigModel") SetReminderForServerConfigModel setRemainderForServerConfigModel,
			HttpServletRequest request, RedirectAttributes redirectAttributes, Model model)
			throws ParseException, ServerException, SQLException {

		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");

		setRemainderForServerConfigModel.setIP(request.getRemoteHost());
		setRemainderForServerConfigModel.setCREATED_BY(ssoid);

		redirectAttributes.addFlashAttribute("success", " Remainder Set Successfully ");

		Object report = setRemainderForServerConfigService.save(setRemainderForServerConfigModel);
		if (report == null) {
			redirectAttributes.addFlashAttribute("error", "Inserted Failure.");
		}
		return "redirect:/addDetailsForRemainder";

	}

	@GetMapping("/deactivateRemainder/{SRNO}")
	public String deactivateRemainder(@PathVariable("SRNO") Long SRNO, @RequestParam("status") Long status, Model model,
			HttpServletRequest request) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}

		setRemainderForServerConfigService.deactivateRemainder(SRNO, status);

		return "redirect:/addDetailsForRemainder";
	}

	@PostMapping("/deactivateWithReason")
	@ResponseBody
	public String deactivateRemainderWithReason(@RequestParam Long srno, @RequestParam String reason, HttpServletRequest request,
			Model model) throws SQLException {
		if(request.getSession().getAttribute("SSOID") == null) {
			return "redirect:/BackToSSO";
		}
		String ssoid = (String) request.getSession().getAttribute("SSOID");
		try {
			setRemainderForServerConfigService.deactivateRemainderWithReason(srno, reason, ssoid);
			return "Success";
		} catch (Exception e) {

			e.printStackTrace();
			return "Error";
		}
	}

}
