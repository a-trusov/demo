package com.example.demo.dao;

import com.example.demo.model.Country;

import java.util.List;

public interface CountryRepository {
    List<Country> getCountryList();
    List<Country> getCountryListStartWith(String name);
    void updateCountryName(String codeName, String newCountryName);
    Country getCountryByCodeName(String codeName);
    Country getCountryByName(String name);
}
