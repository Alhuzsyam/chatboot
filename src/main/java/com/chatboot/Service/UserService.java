package com.chatboot.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chatboot.Model.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void addUser(User user) throws DataAccessException {
        String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }

    public boolean isUserExists(String email) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }
    

    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        
        @SuppressWarnings("deprecation")
        List<User> users = jdbcTemplate.query(
            sql,
            new Object[]{email, password},
            (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
            )
        );

        return users.isEmpty() ? null : users.get(0);
    }
}
