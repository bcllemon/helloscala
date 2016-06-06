package com.jimu.feature

/**
  * Created by bcl on 16/6/6.
  */
object ImplicitDemo extends App{
    implicit def foo1[T](list: List[T]) = new {
        def join(s:String) = list.mkString(s)
    }
    println(List(1,2,3,4,5).join(" - "))
}
