package com.jimu.service;

import com.jimu.dao.MessageDao;
import com.jimu.model.PolicyApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bcl on 16/4/19.
 */
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    @Transactional
    public Integer add(String sig){
        PolicyApiResult apiResult = new PolicyApiResult();
        apiResult.setSignature(sig);
        String aa = null;
        if(messageDao.insert(apiResult)>0){
            aa.equals("asaa");
        }
        return 0;
    }
}
