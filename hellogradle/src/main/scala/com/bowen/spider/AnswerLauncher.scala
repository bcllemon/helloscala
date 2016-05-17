package com.bowen.spider

import com.bowen.spider.model.Topic
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.core.MongoTemplate

import scala.collection.JavaConversions._
/**
  * Created by bcl on 16/5/17.
  */
@SpringBootApplication
class AnswerLauncher extends CommandLineRunner{
    @Autowired
    private val mongoTemplate: MongoTemplate = null
    override def run(args: String*): Unit = {
        val topicList = mongoTemplate.findAll(classOf[Topic],"zhihu_topic_list")
        topicList.filter(p=>StringUtils.isNotEmpty(p.rawHtml)).foreach(topic=>{
            println(s"${topic.title},${topic.id}")
            topic.content = ZhihuSpider.extractTopicContent(topic.rawHtml)
            mongoTemplate.save(topic,"zhihu_topic_list")
            ZhihuSpider.extractAnswer(topic.rawHtml).foreach(answer=>{
                answer.topicId = topic.id
                mongoTemplate.save(answer,"zhihu_answer_list")
            })
        })
    }
}
