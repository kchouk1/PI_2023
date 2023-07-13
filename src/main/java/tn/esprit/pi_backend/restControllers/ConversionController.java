package tn.esprit.pi_backend.restControllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi_backend.entities.Conversion;
import tn.esprit.pi_backend.entities.StatusOfDemand;
import tn.esprit.pi_backend.repositories.ConversionRepository;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/conversions")
public class ConversionController {
	@Autowired
	ConversionRepository conversionRepository;

	@GetMapping
	public ResponseEntity<List<Conversion>> getAllConversions() {
		return new ResponseEntity<>(conversionRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/current")
	public ResponseEntity<Conversion> getCurrentUserConversion(@PathVariable Long userId) {
		Optional<Conversion> optionalConversion = conversionRepository
				.findByStatusAndUserId(StatusOfDemand.NOT_YET_TREATED, userId);
		if (!optionalConversion.isPresent()) {
			return new ResponseEntity<>(optionalConversion.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/create")
	public ResponseEntity<Conversion> createConversion(@RequestBody Conversion conversion) {
		Optional<Conversion> optionalConversion = conversionRepository
				.findByStatusAndUserId(StatusOfDemand.NOT_YET_TREATED, conversion.getUser().getId());
		if (optionalConversion.isPresent()) {
			conversion.setStatus(StatusOfDemand.NOT_YET_TREATED);
			return new ResponseEntity<>(conversionRepository.save(conversion), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Conversion> updateConversion(@PathVariable Long id, @RequestBody Conversion conversion) {
		Optional<Conversion> optionalConversion = conversionRepository.findById(id);
		if (optionalConversion.isPresent()) {
			Conversion conversionUpdate = optionalConversion.get();
			if (conversion.getConversionType() != null) {
				conversionUpdate.setConversionType(conversion.getConversionType());
			}
			if (conversion.getStatus() != null) {
				conversionUpdate.setStatus(conversion.getStatus());
			}
			return new ResponseEntity<>(conversionRepository.save(conversionUpdate), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteConversion(@PathVariable("id") Long id) {
		conversionRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
