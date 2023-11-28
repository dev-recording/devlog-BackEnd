package com.example.devlogbackend.service;

import com.example.devlogbackend.model.VlogDTO;
import com.example.devlogbackend.mapper.VlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VlogService {
    private final VlogMapper vlogMapper;

    public VlogService(VlogMapper vlogMapper){
        this.vlogMapper=vlogMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(VlogService.class);

    public VlogDTO getUserDTOById(String email){
        System.out.println("service:"+email);

        logger.debug("getUserDTOById 호출됨");

        return vlogMapper.selectUserDTOById(email);
    }

}


