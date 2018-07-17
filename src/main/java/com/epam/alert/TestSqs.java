package com.epam.alert;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Created by Stanislav_Pimenov on 14.05.2018.
 */
public class TestSqs {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestSqs.class);
    public static final String INPUT_QUEUE_NAME = "InputMessages";
private static  AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    public static void main(String[] args) {

        ListQueuesResult lq_result = sqs.listQueues();
        System.out.println("Your SQS Queue URLs:");
        for (String url : lq_result.getQueueUrls()) {
            LOGGER.info("Found Queue: {}", url);
//            if (url.contains(INPUT_QUEUE_NAME)) {
//                inputQueueUrl = url;
//            }
        }

//        List<Message> messages = receive();

    }

//    private static List<Message> receive() {
//        List<Message> messages = sqs.receiveMessage(inputQueueUrl).getMessages();
//        messages.forEach(e -> LOGGER.info("message with id: {} and payload: {}", e.getMessageId(), e.getBody()));
//        return messages;
//    }
}
