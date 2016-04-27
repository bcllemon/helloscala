package com.jimu.hello.translater

import javax.validation.constraints.NotNull

/**
  * Created by bcl on 16/4/23.
  */
class IntToStringTranslater extends StringTranslater[Int]{
    @NotNull
    override def translate(t: Int): String = {
        return t toString
    }
}
