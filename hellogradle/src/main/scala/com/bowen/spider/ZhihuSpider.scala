package com.bowen.spider

import java.util.Date

import com.bowen.spider.model.{Answer, Topic}
import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.util.ProxyUtils

import scala.collection.JavaConversions._
/**
  * Created by bcl on 16/5/17.
  */
object ZhihuSpider {
    val browser = JsoupBrowser()

    def fetchTopicList(keyWord: String, page: Int): java.util.List[Topic] = {
        val doc = browser.get(s"http://zhihu.sogou.com/zhihu?query=${keyWord}&sut=1184&lkt=1%2C1463465883695%2C1463465883695&sst0=1463465883799&page=${page}&ie=utf8")
        println(doc.location)
        val items: List[Element] = doc >> elementList(".result-about-list h4 a")
        val topicList = items.map(f => {
            val topic = new Topic()
            topic.title = f.text
            topic.link = f.attr("href")
            topic.createAt = new Date()
            topic
        })
        return topicList
    }
    def fetchTopicRawHtml(link:String): String ={
        return browser.get(link).toHtml
    }
    def extractTopicContent(rawHtml:String):String = {

        return browser.parseString(rawHtml) >> text(".zm-item-rich-text .zm-editable-content")
    }
    def extractAnswer(rawHtml: String): java.util.List[Answer] = {
        val doc = browser.parseString(rawHtml)
        val items: List[Element] = doc >> elementList(".zm-item-answer .zm-item-rich-text")
        val answerList = items.map(f => {
            val answer = new Answer
            answer.author = f.attr("data-author-name")
            answer.content = f >> text(".zm-editable-content")
            answer
        })
        return answerList
    }
}
