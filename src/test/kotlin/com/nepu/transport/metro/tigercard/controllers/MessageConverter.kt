package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.utils.ObjectMapperUtil
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

object MessageConverter {

    fun jacksonDateTimeConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = ObjectMapperUtil.getObjectMapper()
        return converter
    }

}
