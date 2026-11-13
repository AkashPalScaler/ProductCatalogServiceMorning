package com.scaler.productcatalogservice.services;

import com.scaler.productcatalogservice.dtos.GroqChatResponseDto;
import com.scaler.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PitchService {
    private final static String API = "https://api.groq.com/openai/v1/chat/completions";
    private final static String MODEL = "openai/gpt-oss-20b";
    private final static String REASONING_EFFORT = "low";
    private final static Integer MAX_TOKEN = 300;
    private final static String SYSTEM_PROMPT = "You are a marketing copywriter for ShopNest, an online store. You write short push notifications that make people want to open the app.";

    private final RestTemplate restTemplate;
    private final String apiKey;

    public PitchService(RestTemplateBuilder builder, @Value("${groq.api-key}") String apiKey) {
        this.restTemplate = builder.build();
        this.apiKey = apiKey;
    }

    public String generatePitch(Product product){
        try{
            //Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = Map.of(
                    "model",  MODEL,
                    "reasoning_effort", REASONING_EFFORT,
                    "max_completion_tokens",  MAX_TOKEN,
                    "messages" , List.of(
                            Map.of(
                                    "role", "system",
                                    "content", SYSTEM_PROMPT

                            ),
                            Map.of(
                                    "role", "user",
                                    "content", promptBuilder(product)

                            )));

            ResponseEntity<GroqChatResponseDto> responseEntity =
                    restTemplate.postForEntity(API, new HttpEntity<>(body, headers), GroqChatResponseDto.class);
            String pitch = "Dummy pitch";
            if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()){
                pitch = responseEntity.getBody().getChoices().getFirst().getMessage().getContent();
                return pitch;
            }
            return pitch;
        }catch (Exception e){
            System.out.println("PitchService generatePitch Exception: " + e.getMessage());
            return "Dummy pitch";
        }
    }

    private String promptBuilder(Product product){
        return """
                Write ONE push notification for this product.
                Rules:
                - Maximum 25 words.
                - Mention the price.
                - At most one emoji.
                - No hashtags.
                - Reply with only the notification text.
                
                Product: %s
                Category: %s
                Price: %s
                Details: %s
                """.formatted(product.getName(), product.getCategory().getName(),product.getPrice(), product.getDescription());
    }

}
