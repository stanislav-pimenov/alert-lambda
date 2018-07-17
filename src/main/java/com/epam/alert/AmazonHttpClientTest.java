package com.epam.alert;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;

public class AmazonHttpClientTest {

    static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public static void main(String[] args) {

        //Instantiate the request
        Request<Void> request = new DefaultRequest<Void>("es");
        request.setHttpMethod(HttpMethodName.PUT);
        request.getHeaders().put("Content-Type", "application/json");
        request.setEndpoint(
                URI.create("https://vpc-a205027-mces-test-dev-euw1-q6n4gqxmlz2vshuvq2hlc7t5jm.eu-west-1.es.amazonaws.com/mctest"));

        String json = "{\n"
                + "    \"mappings\": {\n"
                + "        \"_doc\": {\n"
                + "            \"properties\": {\n"
                + "                \"message\": {\n"
                + "                    \"type\": \"text\"\n"
                + "                },\n"
                + "                \"query\": {\n"
                + "                    \"type\": \"percolator\"\n"
                + "                }\n"
                + "            }\n"
                + "        }\n"
                + "    }\n"
                + "}";

        request.setContent(new ByteArrayInputStream(json.getBytes()));

        //Sign it...
        AWS4Signer signer = new AWS4Signer();
        signer.setRegionName("eu-west-1");
        signer.setServiceName(request.getServiceName());
        signer.sign(request, credentialsProvider.getCredentials());

        // Create Amazon Http Client
        AmazonHttpClient client = new AmazonHttpClient(new ClientConfiguration());

        //Execute it and get the response...
        client
                .requestExecutionBuilder()
                .executionContext(new ExecutionContext(true))
                .request(request)
                .errorResponseHandler(new DummyHandler<>(new AmazonServiceException("oops")))
                .execute(new DummyHandler<>(
                        new AmazonWebServiceResponse<Void>()));
    }

    public static class DummyHandler<T> implements HttpResponseHandler<T> {
        private final T preCannedResponse;

        public DummyHandler(T preCannedResponse) {
            this.preCannedResponse = preCannedResponse;
        }

        @Override
        public T handle(HttpResponse response) throws Exception {
            System.out.println(IOUtils.toString(response.getContent()));
            return preCannedResponse;
        }

        @Override
        public boolean needsConnectionLeftOpen() {
            return false;
        }
    }
}
