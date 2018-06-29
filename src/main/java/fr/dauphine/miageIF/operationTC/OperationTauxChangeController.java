package fr.dauphine.miageIF.operationTC;

import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OperationTauxChangeController {
	@Autowired
	private Environment environment;
	@Autowired
	private OperationTauxChangeRepository repository;

	@PostMapping("/operation-change")
	public OperationTauxChange saveOperationTauxChange(@RequestBody OperationTauxChange operationTauxChange)throws JSONException {
		final String uri = "http://localhost:8000/devise-change/source/" + operationTauxChange.getSource().toUpperCase()
				+ "/dest/" + operationTauxChange.getDest().toUpperCase();
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		System.out.println("resultat : " + result);
		// build a JSON object
		JSONObject obj = new JSONObject(result);
		double taux = obj.getDouble("taux");
		System.out.println("taux : " + taux);
		operationTauxChange.setTaux(taux);
		return repository.save(operationTauxChange);
	}

	@GetMapping("/operation-change")
	public List<OperationTauxChange> findAllOperationTauxChange() {
		return repository.findAll();
	}

	@GetMapping("/operation-change/source/{source}/dest/{dest}")
	public OperationTauxChange findOperationTauxChange(@PathVariable String source, @PathVariable String dest) {
		return repository.findBySourceAndDest(source.toUpperCase(), dest.toUpperCase());
	}

	@PostMapping("/operation-change/{id}")
	public OperationTauxChange updateTauxChange(@RequestBody OperationTauxChange operationTauxChange, @PathVariable Long id) {
		operationTauxChange.setId(id);
		//recuperation du taux si on ne l'a pas modifié
		Optional<OperationTauxChange> oldOperation = repository.findById(id);
		if(operationTauxChange.getTaux() == 0) {
			operationTauxChange.setTaux(oldOperation.get().getTaux());
		}
		return repository.saveAndFlush(operationTauxChange);
	}

	@DeleteMapping("/operation-change/{id}")
	public void deleteOperationTauxChange(@PathVariable Long id) {
		repository.deleteById(id);
	}
}