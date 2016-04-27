package com.jimu.hello.translater

/**
  * Created by bcl on 16/4/22.
  */
trait Translater[T,V] {
    def translate(t:T):V
}
