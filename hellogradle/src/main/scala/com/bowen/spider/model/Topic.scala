package com.bowen.spider.model

import java.util.Date

import org.springframework.data.annotation.Id

/**
  * Created by bcl on 16/5/17.
  */
class Topic{
    @Id
    var id: String = _
    var link: String = _
    var title: String = _
    var status: Int = 1
    var content:String = _
    var rawHtml:String = _
    var createAt:Date = _
}
