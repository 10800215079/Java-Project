package com.gov.Authmis.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gov.Authmis.constant.Constant;
import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.model.OtpModel;
import com.gov.Authmis.service.AadhaarOTPValidationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AadhaarOTPValidationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AadhaarOTPValidationController.class);
	
	@Autowired
	AadhaarOTPValidationService aadhaarOTPValidationService; 
  
	@GetMapping("/getOTP")
    public ResponseEntity<?> generateOtp(HttpServletRequest request, @RequestParam("UUID") String uuid, Model model) {
        try {
            OtpRespose otpRespose = aadhaarOTPValidationService.getOtp(uuid);
            HttpSession session = request.getSession();
            session.setAttribute("txn", otpRespose.getTxn());
            return ResponseEntity.ok(otpRespose);
        } catch (Exception e) {
            logger.error("Unexpected error occurred during OTP generation for UUID: {}", uuid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
	
//	@PostMapping({"/OTPAuthentication"})
//	public AuthResponseEntity validateOTP(HttpServletRequest request,@RequestParam("OTP") Long otp,  Model model)  {
//		 try {
//			 
//		 }
//		OtpModel otpModel = new OtpModel(); 
//		String txn = (String) request.getSession().getAttribute("txn");
//		otpModel.setOtp(otp);
//		otpModel.setTxn(txn);
//		otpModel.setUuid("609237480396");
//		
//		return aadhaarOTPValidationService.authenticateOtp(otpModel);
//		
//	}
	
	
	@PostMapping("/OTPAuthentication")
    public ResponseEntity<?> validateOTP(HttpServletRequest request, @RequestParam("OTP") Long otp, Model model) {
        try {
            OtpModel otpModel = new OtpModel();
            String txn = (String) request.getSession().getAttribute("txn");
            if (txn == null) {
                logger.error("Transaction ID is missing in session");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction ID is missing");
            }
            otpModel.setOtp(otp);
            otpModel.setTxn(txn);
            otpModel.setUuid("609237480396");

            AuthResponseEntity authResponse = aadhaarOTPValidationService.authenticateOtp(otpModel);
            return ResponseEntity.ok(authResponse);

        } catch (Exception e) {
            logger.error("Unexpected error occurred during OTP validation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
	
	
	@GetMapping("/sendSMS")
    public ResponseEntity<?> sendSMS(HttpServletRequest request) {
		String[] mobileno = { "9779839816", "9602663260" };  // get mobile no from database 
		String message = "Dear Sir/Madam, A document has been shared on your email- pankajsharma.doit@XXXXXXX from Aadhaar Authentication Project, DoIT&C. Please use this Password to open the document- isa6tp3p. Please do not share the password with any unauthorized person to avoid misuse of the document. - DoIT&C (AUA), GoR";
        try {
        	ResponseEntity<String> respose = aadhaarOTPValidationService.sendSms(mobileno,message);
            return ResponseEntity.ok(respose);
        } catch (Exception e) {
            logger.error("Unexpected error occurred during sending SMS for: "+mobileno+"  ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
	
	@GetMapping("/getPassword")
    public ResponseEntity<?> getPassword(HttpServletRequest request) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		 String encrptionKey =Constant.key;
	        System.out.println("Key: " + encrptionKey);

	        // Encrypt data
	        String data = "Hello, World!";
	        String encryptedData = aadhaarOTPValidationService.encrypt(data, encrptionKey);
	        System.out.println("Encrypted Data: " + encryptedData);

	        // Decrypt data
	        String decryptedData = aadhaarOTPValidationService.decrypt(encryptedData, encrptionKey);
	        System.out.println("Decrypted Data: " + decryptedData);
			return null;
    }
	
	@PostMapping("/deleteAadhaar")
    public void deleteAadhaar(HttpServletRequest request) {
		
		aadhaarOTPValidationService.update();
	}

		 
	
	
	
}
	

