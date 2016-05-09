package com.jimu.controller;

import com.github.pagehelper.PageHelper;
import com.jimu.dao.MessageDao;
import com.jimu.model.Message;
import com.jimu.model.PolicyApiResult;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by bcl on 16/4/13.
 */
@RestController
@RequestMapping("/api/java")
public class JavaApiController {
    @Autowired
    private MessageDao messageDao;

    @RequestMapping("/test")
    public Boolean test() {
        return messageDao == null;
    }

    @RequestMapping("/list")
    public List<Message> list() {
        return messageDao.list();
    }

    @RequestMapping("/listApiResult")
    public List<PolicyApiResult> listApiResult() {
        PageHelper.startPage(1, 10);
        return messageDao.listApiResult();
    }

    @RequestMapping("/add/{sig}")
    public Integer add(@PathVariable String sig) throws Exception {
        PolicyApiResult policyApiResult = new PolicyApiResult();
        policyApiResult.setSignature(sig);
        if (messageDao.insert(policyApiResult) > 0) {
            String aa = null;
            aa.equals("aaa");
        }
        return 0;
    }

    @RequestMapping("/testScala")
    public com.jimu.hello.model.Message testScala() {
        com.jimu.hello.model.Message message = new com.jimu.hello.model.Message();
        return message;
    }

    @RequestMapping("/callSlow")
    public String callSlow() {
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        asyncHttpClient.prepareGet("http://localhost:8080/api/scala/slow").execute(new AsyncCompletionHandler<Response>(){

            @Override
            public Response onCompleted(Response response) throws Exception{
                System.out.println(response.getResponseBody());
                return response;
            }

            @Override
            public void onThrowable(Throwable t){
                t.printStackTrace();
            }
        });
        return "hello,callSlow";
    }

}
