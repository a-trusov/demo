package com.example.demo.dao;

import com.example.demo.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    default List<Country> getCountryList(){
        return findAll();
    };
    default List<Country> getCountryListStartWith(String name) {
        return findByNameLike(name);
    };

    @Query("select c from Country c where c.name like :name%")
    List<Country> findByNameLike(String name);

    @Modifying
    @Query("update Country c set c.name = ?2 where c.codeName = ?1")
    void updateCountryName(String codeName, String newCountryName);
    Country getCountryByCodeName(String codeName);
    Country getCountryByName(String name);
}
