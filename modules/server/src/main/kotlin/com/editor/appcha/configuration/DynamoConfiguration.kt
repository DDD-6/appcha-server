package com.editor.appcha.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
class DynamoConfiguration {

    @Bean
    fun dynamoDbAsynceClient(): DynamoDbAsyncClient {
        return DynamoDbAsyncClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .endpointOverride(URI.create(""))
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build()
    }
}
