package com.flightbooking.dao;

import com.flightbooking.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Admin findByUsernameAndPassword(String username, String password) {
        List<Admin> list = jdbcTemplate.query(
                "SELECT * FROM admins WHERE username = ? AND password = ?",
                (rs, rowNum) -> {
                    Admin a = new Admin();
                    a.setId(rs.getInt("id"));
                    a.setUsername(rs.getString("username"));
                    return a;
                },
                username, password);
        return list.isEmpty() ? null : list.get(0);
    }
}
