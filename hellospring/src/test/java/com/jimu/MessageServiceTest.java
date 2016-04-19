package com.jimu;

import com.jimu.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by bcl on 16/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HellospringApplication.class)
public class MessageServiceTest {
    @Resource
    private MessageService messageService;
    @Test
    public void testAdd(){
        messageService.add("333");
    }
}
