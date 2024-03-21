package com.chatboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chatboot.dto.ChatGPTRequest;
import com.chatboot.dto.ChatGPTResponse;
import com.chatboot.dto.Response;

@RestController
@RequestMapping("/api/bot")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CustomBotController {
    
    Response<Object> res = new Response<Object>();
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String url;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public Response<Object> chat(@RequestParam("prompt") String prompt) {
        try{
            ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(url, request, ChatGPTResponse.class);
        res.setStatus(HttpStatus.OK.toString());
        res.setMessage("success");
        res.setPayload(chatGPTResponse.getChoices().get(0).getMessage().getContent());
    }catch(Exception e){
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("apiKey is expired, check openAI Api");
        res.setPayload(null);
    }
    return res;

    }
}
