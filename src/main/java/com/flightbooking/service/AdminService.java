package com.flightbooking.service;

import com.flightbooking.dao.AdminDao;
import com.flightbooking.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin login(String username, String password) {
        return adminDao.findByUsernameAndPassword(username, password);
    }
}
