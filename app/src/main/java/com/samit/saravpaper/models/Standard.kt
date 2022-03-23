package com.samit.saravpaper.models

data class Standard(val stdCode: String? = null,val std_eng: String? = null,val std_mar: String? = null){
    override fun toString(): String = std_mar!!
}
