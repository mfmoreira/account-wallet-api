package com.picpay.wallet.component;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class IntegrationRest {
	private static final Logger log = LoggerFactory.getLogger(IntegrationRest.class);

	@Autowired
	@Qualifier("restConfiguration")
	private RestTemplate restTemplate;

	private <T> ResponseEntity<T> execute(HttpMethod httpMethod, UriComponentsBuilder builder, Object body,
			Class<T> klass) throws UnsupportedEncodingException {

		StopWatch watch = new StopWatch();

		ResponseEntity<T> response;

		try {
			log.info("[IntegrationRest-execute] Solicitando API {}",
					java.net.URLDecoder.decode(builder.toUriString(), "UTF-8"));

			watch.start();

			response = (ResponseEntity<T>) restTemplate
					.getForEntity(builder.toUriString(), klass);

			watch.stop();

			log.info("[IntegrationRest-execute] Solicitacao bem sucedida, executado em {} segundos.",

					watch.getTotalTimeSeconds());

		} catch (HttpClientErrorException e) {

			HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());

			log.error("[IntegrationRest-execute] Solicitacao nao respondida: {} - Message: {}",
					status.getReasonPhrase(), e.getMessage());

			response = new ResponseEntity<>(status);

		} catch (RestClientException e) {

			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

			log.error(
					"[IntegrationRest-execute] Ocorreu um erro na conexao com a API. Status: {} - {}. Executado em {} segundos. Na API {}",

					status.getReasonPhrase(), e.getMessage(), watch.getTotalTimeSeconds(), builder.toUriString());

			response = new ResponseEntity<>(status);
		}
		return response;

	}

	private <T> ResponseEntity<T> post(HttpMethod httpMethod, String payload, Class<T> klass, String url) {

		StopWatch watch = new StopWatch();

		ResponseEntity<T> response;

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(payload, httpHeaders);

		log.info("[IntegrationRest - post] - Enviando solicitacao para endpoint: {} body : {}", url,
				payload);

		watch.start();

		try {

			response = restTemplate.postForEntity(url, entity, klass);

			watch.stop();

			log.info("Solicitacao bem sucedida, executado em {} segundos. Na API {}",

					watch.getTotalTimeSeconds(), httpMethod);

		} catch (HttpClientErrorException e) {

			HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());

			log.error("Solicitacao nao respondida: {} - Message: {}", status.getReasonPhrase(), e.getMessage());

			response = new ResponseEntity<>(status);

		} catch (RestClientException e) {

			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

			log.error("Ocorreu um erro na conexao com a API. Status: {} - {}. Executado em {} segundos. Na API {}",

					status.getReasonPhrase(), e.getMessage(), watch.getTotalTimeSeconds(), httpMethod);

			response = new ResponseEntity<>(status);

		}

		return response;

	}

	public <T> ResponseEntity<T> get(String url, UriComponentsBuilder builder, Class<T> klass)
			throws UnsupportedEncodingException {
		ResponseEntity<T> response = execute(HttpMethod.GET, builder, null, klass);

		return response;

	}

	public <T> ResponseEntity<T> post(String url, String payload, Class<T> klass) {

		ResponseEntity<T> response = this.post(HttpMethod.POST, payload, klass, url);

		return response;

	}

}
