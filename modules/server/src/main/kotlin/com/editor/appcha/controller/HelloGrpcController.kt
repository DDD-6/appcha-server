package com.editor.appcha.controller

import com.editor.appcha.grpc.GreeterServiceGrpcKt
import com.editor.appcha.grpc.GreeterServiceOuterClass.HelloResponse
import com.editor.appcha.grpc.GreeterServiceOuterClass.HelloRequest
import com.editor.appcha.protobuf.HelloProto
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory

@GrpcService
class HelloGrpcController : GreeterServiceGrpcKt.GreeterServiceCoroutineImplBase() {
    private val logger = LoggerFactory.getLogger(HelloGrpcController::class.java)

    override suspend fun sayHello(request: HelloRequest): HelloResponse {
        logger.info("sayHello() {}", request.name)
        val helloProto = HelloProto.Hello.newBuilder().setMessage("Hello, ${request.name}").build()
        return HelloResponse.newBuilder().setHello(helloProto).build()
    }
}
