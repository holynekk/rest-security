package com.holynekk.apisecurity.repository;

import com.holynekk.apisecurity.entity.JdbcCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerSafeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomer findCustomerByEmail(String email) {
        String sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer "
                + "WHERE email = ? AND email IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, this::mapToCustomer, email);
    }

    private JdbcCustomer mapToCustomer(ResultSet resultSet, long i) throws SQLException {
        JdbcCustomer customer = new JdbcCustomer();

        Optional.ofNullable(resultSet.getDate("birth_date")).ifPresent(b -> customer.setBirthDate(b.toLocalDate()));
        customer.setCustomerId(resultSet.getInt("customer_id"));
        customer.setEmail(resultSet.getString("email"));
        customer.setFullName(resultSet.getString("full_name"));
        customer.setGender(resultSet.getString("gender"));

        return customer;
    }

    public List<JdbcCustomer> findCustomersByGender(String genderCode) {
        String sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer "
                + "WHERE gender = ?";
        return jdbcTemplate.query(sql, this::mapToCustomer, genderCode);
    }

    public void createCustomer(JdbcCustomer newCustomer) {
        String sql = "INSERT INTO jdbc_customer(full_name, email, birth_date, gender) VALUES (:fullName, :email, :birthDate, :gender)";

        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("fullName", newCustomer.getFullName())
                .addValue("email", newCustomer.getEmail())
                .addValue("birthDate", newCustomer.getBirthDate())
                .addValue("gender", newCustomer.getGender());

        namedParameterJdbcTemplate.update(sql, sqlParameters);
    }

    public void updateCustomerFullName(int customerId, String newFullName) {
        String sql = "CALL update_jdbc_customer(:customerId, :newFullName)";

        MapSqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("customerId", customerId)
                .addValue("newFullName", newFullName);

        namedParameterJdbcTemplate.update(sql, sqlParameters);
    }
}
