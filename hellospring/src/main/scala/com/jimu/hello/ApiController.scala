package com.jimu.hello

import java.util.concurrent.TimeUnit

import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

/**
  * Created by bcl on 16/4/13.
  */
@RestController
@RequestMapping(Array("/api"))
class ApiController {
  @RequestMapping(value = Array("/hello"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello(): Message = {
    TimeUnit.SECONDS.sleep(6)
    val message = new Message()
    message.value = "Hello, Scala for Spring!"
    message
  }
}
