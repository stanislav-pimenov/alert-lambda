package com.epam.alert;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AlertRequestHandler implements RequestHandler<Map<String, Object>, String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(AlertRequestHandler.class);

    public String handleRequest(Map<String, Object> input, Context context) {
        LOGGER.info("Request: {}", input);
        return "Response Ok";
    }
}
