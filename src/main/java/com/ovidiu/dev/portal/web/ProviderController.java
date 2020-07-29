package com.ovidiu.dev.portal.web;

import com.ovidiu.dev.portal.model.Provider;
import com.ovidiu.dev.portal.model.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
class ProviderController {

    private final Logger log = LoggerFactory.getLogger(ProviderController.class);
    private ProviderRepository providerRepository;

    public ProviderController(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @GetMapping("/providers")
    Collection<Provider> providers() {
        return providerRepository.findAll();
    }

    @GetMapping("/provider/{id}")
    ResponseEntity<?> getProvider(@PathVariable Long id) {
        Optional<Provider> provider = providerRepository.findById(id);
        return provider.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/provider")
    ResponseEntity<Provider> createProvider(@Valid @RequestBody Provider provider) throws URISyntaxException {
        log.info("Request to create provider: {}", provider);
        Provider result = providerRepository.save(provider);
        return ResponseEntity.created(new URI("/api/provider/" + result.getId()))
                .body(result);
    }

    @PutMapping("/provider/{id}")
    ResponseEntity<Provider> updateProvider(@Valid @RequestBody Provider provider) {
        log.info("Request to update provider: {}", provider);
        Provider result = providerRepository.save(provider);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/provider/{id}")
    public ResponseEntity<?> deleteProvider(@PathVariable Long id) {
        log.info("Request to delete provider: {}", id);
        providerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}