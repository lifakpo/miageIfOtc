package fr.dauphine.miageIF.operationTC;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationTcApplicationTests {

	@Test
	public void testFindAllOperationChange() {
		System.out.println("\n*****************************************Find all OperationChange*****************************************\n");
		final String uri = "http://localhost:8080/operation-change";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		assertNotNull(result);
		System.out.println(result);
		System.out.println("\n**********************************************************************************\n");
	}

	@Test
	public void testFindOneOperationChange() {
		System.out.println("\n*****************************************Find one OperationChange*****************************************\n");
		final String uri = "http://localhost:8080/operation-change/{id}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "2");
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class, params);
		assertNotNull(result);
		System.out.println(result);
		System.out.println("\n**********************************************************************************\n");
	}

	@Test
	public void testCreateOperationChange() {
		System.out.println("\n*****************************************Create one transaction*****************************************\n");
		final String uri = "http://localhost:8080/operation-change";
		RestTemplate restTemplate = new RestTemplate();
		OperationTauxChange operation = new OperationTauxChange("EUR", "USD", 16463, new Date(), 0);
		OperationTauxChange result = restTemplate.postForObject(uri, operation, OperationTauxChange.class);
		assertNotNull(result);
		System.out.println(result);
		System.out.println("\n**********************************************************************************\n");
	}

	@Test
	public void testUpdateOperationChange() {
		System.out.println("\n*****************************************Update a transaction*****************************************\n");
		final String uri = "http://localhost:8080/operation-change/{id}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		OperationTauxChange operation = new OperationTauxChange("EUR", "USD", 16463, new Date(), 0);
		RestTemplate restTemplate = new RestTemplate();
		OperationTauxChange result = restTemplate.postForObject(uri, operation, OperationTauxChange.class, params);
		assertNotNull(result);
		System.out.println(result);
		System.out.println("\n**********************************************************************************\n");
	}

	@Test
	public void testDeleteOperationChange() {
		System.out.println("\n*****************************************Delete transaction id : "+ 1 +"*****************************************\n");
		final String uri = "http://localhost:8080/operation-change/{id}";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "3");
		restTemplate.delete(uri, params);
		testFindAllOperationChange();
		System.out.println("\n**********************************************************************************\n");
	}
}
