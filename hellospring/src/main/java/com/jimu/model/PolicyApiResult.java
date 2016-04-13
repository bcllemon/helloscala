package com.jimu.model;

import java.util.Date;

/**
 * Created by bcl on 16/4/1.
 */
public class PolicyApiResult {
    private String signature;
    private String request;
    private String response;
    private Integer result;
    private Long diffTime;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(Long diffTime) {
        this.diffTime = diffTime;
    }

    protected Long id;
    protected Date created;
    protected Date updated;
    protected Integer state;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
