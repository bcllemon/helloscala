package com.bowen.spider.model

import java.util.Date

import org.springframework.data.annotation.Id

/**
  * Created by bcl on 16/5/17.
  */
class Answer {
    @Id
    var id:String = _
    var topicId:String = _
    var content:String = _
    var author:String = _
    var createAt:Date = _
}
