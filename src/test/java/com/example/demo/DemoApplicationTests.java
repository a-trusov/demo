package com.example.demo;

import com.example.demo.dao.CountryRepository;
import com.example.demo.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class DemoApplicationTests {
    @Autowired
    private CountryRepository countryRepository;

    private static final List<Country> EXPECTED_COUNTRY_LIST = new ArrayList<>();
    private static final List<Country> EXPECTED_COUNTRY_LIST_STARTS_WITH_A = new ArrayList<>();
    private static final Country COUNTRY_WITH_CHANGED_NAME = new Country(1, "Russia", "RU");

    private static final String[][] COUNTRY_INIT_DATA = {
            {"Australia", "AU"},
            {"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
            {"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
            {"Russian Federation", "RU"}, {"Sweden", "SE"},
            {"Switzerland", "CH"}, {"United Kingdom", "GB"},
            {"United States", "US"}
    };

    static {
        for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[0], countryInitData[1]);
            EXPECTED_COUNTRY_LIST.add(country);
            if (country.getName().startsWith("A")) {
                EXPECTED_COUNTRY_LIST_STARTS_WITH_A.add(country);
            }
        }
    }

    @Test
    public void testCountryList() throws InterruptedException {
        List<Country> countryList = countryRepository.getCountryList();
        assertNotNull(countryList);
        assertEquals(EXPECTED_COUNTRY_LIST.size(), countryList.size());
        for (int i = 0; i < EXPECTED_COUNTRY_LIST.size(); i++) {
            assertEquals(EXPECTED_COUNTRY_LIST.get(i), countryList.get(i));
        }

//        org.hsqldb.util.DatabaseManager.main(new String[]{});
//        Thread.sleep(90000);
    }

    @Test
    public void testCountryListStartsWithA() {
        List<Country> countryList = countryRepository.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(EXPECTED_COUNTRY_LIST_STARTS_WITH_A.size(), countryList.size());
        for (int i = 0; i < EXPECTED_COUNTRY_LIST_STARTS_WITH_A.size(); i++) {
            assertEquals(EXPECTED_COUNTRY_LIST_STARTS_WITH_A.get(i), countryList.get(i));
        }
    }

    @Test
    public void testCountryChange() {
        countryRepository.updateCountryName("RU", "Russia");
        assertEquals(COUNTRY_WITH_CHANGED_NAME, countryRepository.getCountryByCodeName("RU"));
    }
}
