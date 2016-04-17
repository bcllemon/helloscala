package com.jimu.hello.controller

import java.util.concurrent.TimeUnit
import javax.annotation.Resource

import com.jimu.dao.MessageDao
import com.jimu.hello.model
import com.jimu.hello.model.Message
import com.jimu.model
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

/**
  * Created by bcl on 16/4/13.
  */
@RestController
@RequestMapping(Array("/api/scala"))
class ScalaApiController {
  @Resource private val messageDao: MessageDao = null

  @RequestMapping(value = Array("/hello"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello(): Message = {
    TimeUnit.SECONDS.sleep(6)
    val message = new Message()
    message.value = "Hello, Scala for Spring!"
    message
  }
  @RequestMapping(value = Array("/hello2"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello2(): model.Message = {
    TimeUnit.SECONDS.sleep(6)
    val message = new model.Message()
    message.value = "Hello, Scala for Spring!"
    message.my = "222"
    message
  }

  @RequestMapping(Array("/list"))
  def list: java.util.List[model.Message] = {
    return messageDao.list
  }
}
