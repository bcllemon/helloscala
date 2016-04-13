package com.jimu.controller;

import com.jimu.dao.MessageDao;
import com.jimu.model.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bcl on 16/4/13.
 */
@RestController
@RequestMapping("/api/java")
public class JavaApiController {
    @Resource
    private MessageDao messageDao;
    @RequestMapping("/test")
    public Boolean test(){
        return messageDao==null;
    }

    @RequestMapping("/list")
    public List<Message> list(){
        return messageDao.list();
    }
}
