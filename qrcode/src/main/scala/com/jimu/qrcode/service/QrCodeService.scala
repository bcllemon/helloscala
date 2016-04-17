package com.jimu.qrcode.service

import java.io.{File, FileInputStream, OutputStream}
import javax.annotation.Resource

import com.jimu.qrcode.dao.ShortUrlRepository
import com.jimu.qrcode.model.ShortUrl
import com.jimu.util.ShortUrlUtil
import net.glxn.qrgen.javase.QRCode
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service

/**
  * Created by bcl on 16/4/17.
  */
@Service
class QrCodeService {

    @Resource
    val shortUrlRepository:ShortUrlRepository = null

    def make(url:String,output:OutputStream):Unit = {
        val file:File = QRCode.from(url).file()
        IOUtils.copy(new FileInputStream(file),output)
    }
    def shortUrl(url:String):String = {
        val shortUrl = ShortUrlUtil.ShortText(url)
        val shortUrlBean = new ShortUrl()
        shortUrlBean.oriUrl = url
        shortUrlBean.shortUrl = shortUrl(0)
        shortUrlRepository.save(shortUrlBean)
        shortUrl(0)
    }
}
