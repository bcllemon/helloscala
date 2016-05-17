package com.bowen.spider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.core.MongoTemplate
import scala.collection.JavaConversions._
/**
  * Created by bcl on 16/5/17.
  */
//@SpringBootApplication
class TopicListLauncher extends CommandLineRunner {
    @Autowired
    private val mongoTemplate: MongoTemplate = null

    override def run(args: String*): Unit = {
        for (i <- 1 until 21) {
            val topicList = ZhihuSpider.fetchTopicList("车险", i)
            topicList.filter(p => p.title.indexOf("车险") != -1).foreach(p=>mongoTemplate.insert(p,"zhihu_topic_list"))
        }
    }
}
