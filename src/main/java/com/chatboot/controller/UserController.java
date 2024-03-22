package com.chatboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatboot.Model.User;
import com.chatboot.Service.UserService;
import com.chatboot.dto.LoginUser;
import com.chatboot.dto.Response;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {
    
    @Autowired
    UserService userService;
    Response<Object> res = new Response<Object>();


    @PostMapping("/add")
    public Response<Object> createUser(@RequestBody User user) {
    Response<Object> res = new Response<>();

    try {
        // Check if the email already exists
        if (userService.isUserExists(user.getEmail())) {
            res.setStatus(HttpStatus.BAD_REQUEST.toString());
            res.setMessage("Email already exists");
            res.setPayload(null);
        } else {
            // If email is unique, proceed to add user
            userService.addUser(user);
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("Success");
            res.setPayload(user);
        }
    } catch (Exception e) {
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage(e.getMessage());
        res.setPayload(null);
    }

    return res;
}


    @PostMapping("/login")
    public Response<Object> loginUser(@RequestBody LoginUser loginRequest) {
        // return userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        try{
           User data = userService.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
           if(data != null){
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("success");
            res.setPayload(data);
           }else{
            res.setStatus(HttpStatus.NOT_FOUND.toString());
            res.setMessage("user not exist");
            res.setPayload(null);
           }
        }catch(Exception e ){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("error");
            res.setPayload(e.getMessage());
        }
        return res;
    }
}

