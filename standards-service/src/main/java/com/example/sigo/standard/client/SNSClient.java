package com.example.sigo.standard.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SNSClient {

    @Value("${sns.topic.arn}")
    private String topicArn;

    @Autowired
    private SnsClient snsClient;

    private final Logger logger = LoggerFactory.getLogger(SNSClient.class);

    public void publish(String message) {

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicArn)
                .build();

        PublishResponse response = snsClient.publish(request);

        logger.info("Mensagem enviada. Status Code: " + response.sdkHttpResponse().statusCode());
    }
}
