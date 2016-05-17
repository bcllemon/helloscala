package com.bowen.spider

import com.bowen.spider.model.Topic
import org.asynchttpclient._
import org.asynchttpclient.proxy.ProxyServer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.core.MongoTemplate

import scala.collection.JavaConversions._

/**
  * Created by bcl on 16/5/17.
  */
//@SpringBootApplication
class UrlFetchLauncher extends CommandLineRunner {
    val logger = LoggerFactory.getLogger(classOf[UrlFetchLauncher])
    val cf: AsyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder().setProxyServer(new ProxyServer.Builder("127.0.0.1", 8888)).setRequestTimeout(120 * 1000).setConnectTimeout(120 * 1000).setMaxConnections(5).build()
    val asyncHttpClient: AsyncHttpClient = new DefaultAsyncHttpClient(cf)
    @Autowired
    private val mongoTemplate: MongoTemplate = null

    override def run(args: String*): Unit = {
        val topicList = mongoTemplate.findAll(classOf[Topic], "zhihu_topic_list")
        topicList.foreach(topic => {
            val selfTopic = topic
            selfTopic.rawHtml = ZhihuSpider.fetchTopicRawHtml(topic.link)
            mongoTemplate.save(selfTopic, "zhihu_topic_list")
//            asyncHttpClient.prepareGet(topic.link).execute(new AsyncCompletionHandler[Response]() {
//                @throws[Exception]
//                def onCompleted(response: Response): Response = {
//                    logger.info(s"finish topic:${selfTopic.title}")
//                    selfTopic.rawHtml = response.getResponseBody
//                    selfTopic.status = 2
//                    mongoTemplate.save(selfTopic, "zhihu_topic_list")
//                    return response
//                }
//
//                override
//                def onThrowable(t: Throwable): Unit = {
//                    logger.error(t.getMessage, t)
//                }
//            })
        })
    }
}
