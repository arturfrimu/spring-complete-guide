package com.arturfrimu.googledrive.dto;

import lombok.Builder;

@Builder
public record Response(
        int status,
        String message,
        String url
) {}
