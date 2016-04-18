package com.jimu.qrcode

import javax.annotation.Resource

import com.jimu.QrcodeApplication
import com.jimu.qrcode.service.QrCodeService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
  * Created by bcl on 16/4/18.
  */
@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(Array(classOf[QrcodeApplication]))
class QrCodeServiceTest {
    @Resource
    val qrCodeService:QrCodeService = null

    @Test
    def testShortUrl():Unit = {
        println(qrCodeService.shortUrl("http://www.jimu.com"))
    }
    @Test
    def testGetUrl():Unit = {
        println(qrCodeService.getUrl("e2euay"))
    }
}
