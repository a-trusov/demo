package com.example.demo.controllers;

import com.example.demo.dao.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.controllers.CountryDto.toDto;
import static com.example.demo.controllers.CountryDto.toModelObject;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping(value = "/country")
    public List<CountryDto> getAll() {
        return countryRepository
                .findAll()
                .stream()
                .map(CountryDto::toDto)
                .collect(toList());
    }

    @GetMapping(value = "/country/{id}")
    public CountryDto get(@PathVariable("id") int id) throws NotFoundException {
        return countryRepository
                .findById(id)
                .map(CountryDto::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = "/country")
    public @ResponseBody CountryDto create(@RequestBody CountryDto dto) {
        return toDto(countryRepository.save(toModelObject(dto)));
    }

    @DeleteMapping("/country/{id}")
    public void delete(@PathVariable("id") int id) {
        countryRepository.deleteById(id);
    }

    @PutMapping("/country/{id}/name")
    public void changeName(
            @PathVariable("id") int id,
            @RequestParam("name") String name) throws NotFoundException {
        countryRepository
                .findById(id)
                .map(country -> {
                    country.setName(name);
                    countryRepository.save(country);
                    return country;
                }).orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotEnoughFunds(NotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body("Not found Country");
    }
}
