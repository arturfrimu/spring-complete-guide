package com.arturfrimu.chatgpt.mysimpleversion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatGPTService.sendMessage(message);
    }
}
