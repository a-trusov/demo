package com.example.demo.dao;

import com.example.demo.model.Country;
import lombok.var;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryRepositoryImpl implements CountryRepository {
    private static final String GET_ALL_COUNTRIES_SQL = "select * from country";

    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

    public CountryRepositoryImpl(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
        this.template = template;
        this.namedTemplate = namedTemplate;
    }

    @Override
    public List<Country> getCountryList() {
        List<Country> countryList = template.query(GET_ALL_COUNTRIES_SQL, this::mapRow);
        return countryList;
    }

    private Country mapRow(ResultSet resultSet, int row) throws SQLException {
        return new Country(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("code_name"));
    }

    @Override
    public List<Country> getCountryListStartWith(String name) {
        var sqlParameterSource = new MapSqlParameterSource("name", name + '%');
        return namedTemplate.query("select * from country where name like :name",
                sqlParameterSource, this::mapRow);

    }

    @Override
    public void updateCountryName(String codeName, String newCountryName) {
        template.update("update country SET name = ? where code_name = ?",
                newCountryName, codeName);
    }

    @Override
    public Country getCountryByCodeName(String codeName) {
        return template.queryForObject(
                "select * from country where code_name = ?", this::mapRow, codeName);
    }

    @Override
    public Country getCountryByName(String name) {
        return template.queryForObject(
                "select * from country where name = ?", this::mapRow, name);
    }
}
