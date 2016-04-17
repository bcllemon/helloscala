package com.jimu.qrcode.controller

import javax.annotation.Resource
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

import com.jimu.qrcode.service.QrCodeService
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * Created by bcl on 16/4/17.
  */
@RestController
@RequestMapping(Array("/qrcode"))
class QrCodeController {
    @Resource
    val qrcodeService:QrCodeService = null

    @RequestMapping(Array("/show"))
    def show(response:HttpServletResponse,url:String):Unit={
        val op:ServletOutputStream = response.getOutputStream();
        qrcodeService.make(qrcodeService.shortUrl(url),op);
    }
}
