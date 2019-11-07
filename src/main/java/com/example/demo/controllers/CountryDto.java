package com.example.demo.controllers;

import com.example.demo.model.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private int id = -1;
    private String name;
    private String codeName;

    static Country toModelObject(CountryDto countryDto) {
        return new Country(countryDto.getId(),
                countryDto.getName(),
                countryDto.getCodeName());
    }

    static CountryDto toDto(Country country) {
        return new CountryDto(country.getId(), country.getName(), country.getCodeName());
    }
}
