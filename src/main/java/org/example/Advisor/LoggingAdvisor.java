package org.example.Advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.stereotype.Service;

@Service
public class LoggingAdvisor implements CallAroundAdvisor {

    private final static Logger logger = LoggerFactory.getLogger(LoggingAdvisor.class);
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.logBeforeRequest(advisedRequest);

        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        logAfterResponse(advisedResponse);
        return advisedResponse;
    }

    private AdvisedRequest logBeforeRequest(AdvisedRequest advisedRequest) {
        logger.info("User Prompt: " + advisedRequest.userText());
        return advisedRequest;
    }

    private void logAfterResponse(AdvisedResponse advisedResponse) {
        logger.info("AI Response: " + advisedResponse.response()
                .getResult()
                .getOutput());
    }

    @Override
    public String getName() {
        return "LoggingAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
