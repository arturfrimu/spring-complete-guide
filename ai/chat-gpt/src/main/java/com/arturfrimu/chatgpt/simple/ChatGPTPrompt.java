package com.arturfrimu.chatgpt.simple;

import lombok.Data;

@Data
public class ChatGPTPrompt {

    private String prompt;
    private String max_tokens;
}
