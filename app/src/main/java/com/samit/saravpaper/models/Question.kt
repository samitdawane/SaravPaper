package com.samit.saravpaper.models

import java.io.Serializable

data class Question(
    var ans1:  String? = null,
    var ans1_Img:  String? = null,
    var ans2:  String? = null,
    var ans2_Img:  String? = null,
    var ans3:  String? = null,
    var ans3_Img:  String? = null,
    var ans4:  String? = null,
    var ans4_Img:  String? = null,
    var correctANS: Int =0,
    var paperCode:  String? = null,
    var qMain:  String? = null,
    var qMainOrder:  Int =0,
    var que:  String? = null,
    var queImg:  String? = null,
    var queOrder:  Int =0,
    var stdCode:  String? = null,
) : Serializable