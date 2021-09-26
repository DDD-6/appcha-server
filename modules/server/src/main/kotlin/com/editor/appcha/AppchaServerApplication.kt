package com.editor.appcha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppchaServerApplication

fun main(args: Array<String>) {
    runApplication<AppchaServerApplication>(*args)
}
