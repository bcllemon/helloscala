package com.jimu.hello.translater
/**
  * Created by bcl on 16/4/22.
  */
object MainClass {
    def main(args: Array[String]): Unit = {
        val clazz = classOf[IntToStringTranslater]
        val translater = clazz.newInstance()
//        val translater:StringTranslater = new IntToStringTranslater
        println(translater.translate(1).getClass)
//        translater.asInstanceOf[IntToStringTranslater].tranlsate(11)
    }

}
