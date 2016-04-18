package com.jimu.qrcode.dao

import com.jimu.qrcode.model.{ShortUrl, UrlView}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.stereotype.Component
import java.util.List
/**
  * Created by bcl on 16/4/17.
  */
@Component
class ShortUrlRepository {
    @Autowired
    val mongoTemplate:MongoTemplate = null

    def save(shortUrl:ShortUrl):Unit = {
        mongoTemplate.save(shortUrl)
    }
    def findUrl(url:String):List[ShortUrl] = {
        mongoTemplate.find(Query.query(Criteria.where("oriUrl").is(url)),classOf[ShortUrl])
    }
    def findShortUrl(shortUrl:String):List[ShortUrl] = {
        mongoTemplate.find(Query.query(Criteria.where("shortUrl").is(shortUrl)),classOf[ShortUrl])
    }
    def viewUrl(urlView:UrlView):Unit = {
        mongoTemplate.save(urlView)
    }
}
