package com.jimu.qrcode.service

import java.io.{File, FileInputStream, OutputStream}
import java.util.Date
import javax.annotation.Resource

import com.jimu.qrcode.dao.ShortUrlRepository
import com.jimu.qrcode.model.{ShortUrl, UrlView}
import com.jimu.util.ShortUrlUtil
import net.glxn.qrgen.javase.QRCode
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service

import scala.util.control.Breaks._
/**
  * Created by bcl on 16/4/17.
  */
@Service
class QrCodeService {

    @Resource
    val shortUrlRepository: ShortUrlRepository = null

    def make(url: String, output: OutputStream): Unit = {
        val file: File = QRCode.from(url).file()
        IOUtils.copy(new FileInputStream(file), output)
    }

    def shortUrl(url: String): String = {
        val urlList = shortUrlRepository.findUrl(url)
        if (!urlList.isEmpty()) {
            return urlList.get(0).shortUrl
        }
        val shortUrl = ShortUrlUtil.ShortText(url)
        var i: Int = 0
        breakable{
            while (i < shortUrl.size) {
                if (shortUrlRepository.findShortUrl(shortUrl(i)).isEmpty()) {
                    val shortUrlBean = new ShortUrl()
                    shortUrlBean.oriUrl = url
                    shortUrlBean.shortUrl = shortUrl(i)
                    shortUrlRepository.save(shortUrlBean)
                    break
                }
                i += 1
            }
        }
        shortUrl(i)
    }
    def getUrl(shortUrl: String): String ={
        val urlList = shortUrlRepository.findShortUrl(shortUrl)
        if(urlList.isEmpty()){
            return ""
        }
        return urlList.get(0).oriUrl
    }
    def viewUrl(url: String):Unit ={
        val urlView:UrlView = new  UrlView()
        urlView.oriUrl = url
        urlView.viewTime = new Date()
        shortUrlRepository.viewUrl(urlView)
    }

}
