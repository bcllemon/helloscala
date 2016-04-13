package com.jimu.dao;

import com.jimu.model.Message;
import com.jimu.model.PolicyApiResult;

import java.util.List;

/**
 * Created by bcl on 16/4/13.
 */
public interface MessageDao {
    List<Message> list();
    List<PolicyApiResult> listApiResult();
}
