package com.chatboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chatboot.Model.Prompt;
import com.chatboot.Service.PrompService;
import com.chatboot.dto.Response;

@RestController
@RequestMapping("/api/prompt")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PromptController {
    
    @Autowired
    PrompService prompService;
    Response<Object> res = new Response<Object>();
    
    @PostMapping("/add")
    public Response<Object> createPromt(@RequestBody Prompt prompt){

        try{
            prompService.createPromt(prompt);
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("succes");
            res.setPayload(prompt);
        }catch(Exception e){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("error");
            res.setPayload(e.getMessage());
        }
        return res;
    }

    @GetMapping("/{userId}")
    public Response<Object> getPrompt(@PathVariable String userId) {
       try{
        List<Prompt> list = prompService.getPrompts(userId);
        if (list.isEmpty()) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("data not found");
            res.setPayload(null);
        }
       else{
        res.setStatus(HttpStatus.OK.toString());
        res.setMessage("success");
        res.setPayload(list);
       }
        return res;
        
       }catch(Exception e){
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("error");
        res.setPayload(e.getMessage());
        return res;
       }
    }

    
    @GetMapping("/delete/{userId}")
    public Response<Object> deletePrompt(@PathVariable String userId) {
       try{
        prompService.deletePromt(userId); 
        res.setStatus(HttpStatus.OK.toString());
        res.setMessage("seuccess");
        res.setPayload(userId);
       }catch(Exception e){
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("error");
        res.setPayload(e.getMessage());
       }
       return res;
    }

    @GetMapping("/data/{Pid}")
    public Response<Object> getSpecificPrompt(@PathVariable String Pid) {
        List<Prompt> prompts =  prompService.getspecificprompt(Pid);
        try{
            if(prompts.isEmpty()){
                res.setStatus(HttpStatus.NOT_FOUND.toString());
                res.setMessage("error");
                res.setPayload(null);
            }else{
                res.setStatus(HttpStatus.OK.toString());
                res.setMessage("success");
                res.setPayload(prompts);
            }
        }catch(Exception e){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("error");
            res.setPayload(e.getMessage());
        }
        return res;
    }

    
    
}
