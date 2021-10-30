package com.editor.appcha.repository

import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

@Repository
class UserRepository(
    private val dbClient: DynamoDbAsyncClient
) {
    fun findById(id: Int) {
        // TODO
    }
}
