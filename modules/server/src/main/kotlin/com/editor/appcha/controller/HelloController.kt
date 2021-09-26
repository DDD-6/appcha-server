package com.editor.appcha.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @RequestMapping("/hello")
    fun hello() {
        println("hello world!")
    }
}
