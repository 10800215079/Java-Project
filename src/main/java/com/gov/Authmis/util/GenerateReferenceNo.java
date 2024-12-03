package com.gov.Authmis.util;

import okhttp3.*;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.gov.Authmis.model.DSMTokanize;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
public class GenerateReferenceNo {
	public static String getReferenceNumber(String aadhaarNumber)
			throws IOException, JAXBException, NoSuchAlgorithmException, KeyManagementException {
		String refno = "";
		try {
			// Create a custom TrustManager that trusts all certificates
			TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) {
				}
			} };

			// Create an SSL context that uses the custom TrustManager
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

			// Create an SSLSocketFactory with the custom SSL context
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient client = new OkHttpClient.Builder()
					.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0])
					.hostnameVerifier((hostname, session) -> true) // Allow all hostnames
					.build();

			MediaType mediaType = MediaType.parse("application/xml");
			RequestBody body = RequestBody.create(mediaType, "<AuthRequest  uid=\"" + aadhaarNumber
					+ "\" flagType=\"A\" subaua=\"0000440000\" ver=\"2.5\">\r\n</AuthRequest>");

			Request request = new Request.Builder().url(
					"https://api.sewadwaar.rajasthan.gov.in/app/live/Aadhaar/Prod/tokenizeV2/doitAadhaar/encDec/demo/hsm/auth?client_id=977c74a2-672b-4c2c-b8d3-4bbe1368d195")
					.method("POST", body).addHeader("Content-Type", "application/xml")
					.addHeader("Cookie", "NSC_BbeibsV-JQw4-443=ffffffff094eeffe45525d5f4f58455e445a4a423660").build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			System.out.println("response UID===" + responseString);

			JAXBContext jaxbContext = JAXBContext.newInstance(DSMTokanize.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(responseString);
			DSMTokanize dSMTokanize = (DSMTokanize) unmarshaller.unmarshal(reader);

			System.out.println("person.getAadhaarId()" + dSMTokanize.getRefNo());
			refno = dSMTokanize.getRefNo();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception==>>" + e);
		}
		return refno;
	}
	
	
	public static StringBuilder getOData(StringEntity inputStr, String url) {
		CloseableHttpClient client1;
		try {
			System.out.println("getUserProfile started...");

			// Create a custom SSL context
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build();

			client1 = HttpClients.custom().setSSLContext(sslContext)
					.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

			HttpPost post1 = new HttpPost(url);

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

			return res1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}