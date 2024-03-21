package com.chatboot.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chatboot.Model.Prompt;

@Service
public class PrompService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createPromt(Prompt prompt){
        String sql = "INSERT INTO prompt (user_id,result,prompt) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, prompt.getUserId(),prompt.getResult(),prompt.getPrompt());
    }
    public List<Prompt> getPrompts(String userId) {
        String sql = "SELECT prompt.id, prompt.prompt, prompt.result FROM user INNER JOIN prompt ON user.id = prompt.user_id WHERE user.id = ?";
        
        @SuppressWarnings("deprecation")
        List<Prompt> prompts = jdbcTemplate.query(
            sql,
            new Object[]{userId},
            (rs, rowNum) -> new Prompt(
                rs.getLong("id"),
                rs.getString("prompt"),
                rs.getString("result"),
                userId
            )
        );
        return prompts;
    }

    public void deletePromt(String userId){
        String sql = "DELETE FROM `prompt` WHERE id = ?";
        jdbcTemplate.update(sql,userId);
    }

    public List<Prompt> getspecificprompt(String Pid){
        String sql = "SELECT * FROM prompt WHERE id = ?";

        @SuppressWarnings("deprecation")
        List<Prompt> prompts = jdbcTemplate.query(sql,
        new Object[]{Pid},(rs, rowNum)->new Prompt(
            rs.getLong("id"),
            rs.getString("prompt"),
            rs.getString("result"),
            rs.getString("user_id")
        ));
        return prompts;
    }
    
    
    
    
}
