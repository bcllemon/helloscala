package com.jimu.qrcode.model

import java.util.Date

import scala.beans.BeanProperty

/**
  * Created by bcl on 16/4/18.
  */
class UrlView {
    @BeanProperty
    var oriUrl: String = _
    @BeanProperty
    var viewTime:Date = _
}
