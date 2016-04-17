package com.jimu.qrcode.dao

import com.jimu.qrcode.model.ShortUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

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
}
