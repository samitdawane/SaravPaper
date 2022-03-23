package com.samit.saravpaper.models

import java.io.Serializable

data class Paper(val stdCode: String? = null,
                 val paperCode: String? = null,
                 val testType_eng: String? = null,
                 val testType_mar: String? = null,
                 val paper_eng: String? = null,
                 val paper_mar: String? = null):Serializable
