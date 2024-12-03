package com.gov.Authmis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.*;
import javax.net.ssl.SSLContext;

import org.apache.hc.core5.http.URIScheme;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class AuthmisApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(AuthmisApplication.class);
		SpringApplication.run(AuthmisApplication.class, args);
 
	      logger.debug("Debug Message Logged !!!");
	      logger.info("Info Message Logged !!!");
	      logger.error("Error Message Logged !!!");
	}
	
	
	@Bean
	public RestTemplate getRestTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				return true;
			}
		};
		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, acceptingTrustStrategy).build();

		Registry<ConnectionSocketFactory> socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext))
				.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory()).build();

		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
				.setConnectionManagerShared(true).build();

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(requestFactory);
	}
}
