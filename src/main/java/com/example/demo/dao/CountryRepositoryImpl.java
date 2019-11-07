package com.example.demo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepositoryImpl {
    protected final JdbcTemplate template;
    protected final NamedParameterJdbcTemplate namedTemplate;

    public CountryRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        namedTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
    }
}
