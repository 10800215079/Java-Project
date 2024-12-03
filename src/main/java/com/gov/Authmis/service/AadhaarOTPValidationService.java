package com.gov.Authmis.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.ResponseEntity;

import com.gov.Authmis.entity.AuthResponseEntity;
import com.gov.Authmis.entity.OtpRespose;
import com.gov.Authmis.model.OtpModel;

public interface AadhaarOTPValidationService {
	OtpRespose getOtp(String uuid);
	
	AuthResponseEntity authenticateOtp(OtpModel model);
	ResponseEntity<String> sendSms(String[] mobileno,String message);
	String encrypt(String data, String encrptionKey) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException;
	String decrypt(String encryptedData, String encrptionKey) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;

	void update();
}
