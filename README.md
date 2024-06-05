# Music Discovery

package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SlackController {

    private final WebClient webClient;

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    public SlackController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(slackWebhookUrl).build();
    }

    @PostMapping("/send-to-slack")
    public ResponseEntity<String> sendToSlack(@RequestBody MessageRequest messageRequest) {
        String message = messageRequest.getMessage();
        return webClient.post()
                .uri(slackWebhookUrl)
                .body(Mono.just(new SlackMessage(message)), SlackMessage.class)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>("Error sending message to Slack", HttpStatus.INTERNAL_SERVER_ERROR)))
                .block();
    }

    static class SlackMessage {
        private String text;

        public SlackMessage(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    static class MessageRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
## Environment

- wsl2
- PostgreSQL
- Spring Boot
- Vue.js
