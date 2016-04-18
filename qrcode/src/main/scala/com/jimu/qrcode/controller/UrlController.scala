package com.jimu.qrcode.controller

import javax.annotation.Resource

import com.jimu.qrcode.service.QrCodeService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping}

/**
  * Created by bcl on 16/4/18.
  */
@Controller
@RequestMapping(Array("/url"))
class UrlController {

    @Resource
    val qrcodeService:QrCodeService = null


    @RequestMapping(Array("/{shortUrl}"))
    def redirect(@PathVariable shortUrl: String): String = {
        return "redirect:" + qrcodeService.getUrl(shortUrl)
    }
}
