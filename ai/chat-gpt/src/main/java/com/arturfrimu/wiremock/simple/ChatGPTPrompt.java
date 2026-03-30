package com.arturfrimu.wiremock.simple;

import lombok.Data;

@Data
public class ChatGPTPrompt {

    private String prompt;
    private String max_tokens;
}
