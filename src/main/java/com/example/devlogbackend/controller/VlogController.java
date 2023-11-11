package com.example.devlogbackend.controller;

import com.example.devlogbackend.dto.VlogDTO;
import com.example.devlogbackend.service.VlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VlogController {

    private final VlogService vlogService;
    private static final Logger logger = LoggerFactory.getLogger(VlogController.class);

    @Autowired
    public VlogController(VlogService vlogService) {
        this.vlogService = vlogService;
    }

        @GetMapping("/user/{email}")
        public String home(@PathVariable String email){

            logger.debug("Received email: {}",email);

            VlogDTO dto = vlogService.getUserDTOById(email);
            System.out.println("안녕");
            return dto.getEmail();


        }
}
