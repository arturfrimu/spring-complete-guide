package com.arturfrimu.googleintegrations.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class GoogleOAuthController {

    private final GoogleOAuthService googleOAuthService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoogleOAuthController(GoogleOAuthService googleOAuthService) {
        this.googleOAuthService = googleOAuthService;
    }

    @GetMapping("/google/auth")
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        googleOAuthService.redirectToGoogleAuth(response);
    }

    @GetMapping("/google/callback")
    public ResponseEntity<String> googleCallback(@RequestParam("code") String code) {
        return googleOAuthService.exchangeCodeForTokens(code);
    }

    @GetMapping("/google/refresh")
    public ResponseEntity<String> refreshAccessToken() {
        return googleOAuthService.refreshAccessToken();
    }

    @SneakyThrows
    @PostMapping("/google/create-event")
    public ResponseEntity<String> createEvent(@RequestBody List<CreateEventRequest> eventData) {
        for (CreateEventRequest event : eventData) {
            EventDto eventDto = objectMapper.readValue(baseEventTemplate, EventDto.class);

            eventDto.setDescription(event.getDescription());
            eventDto.setSummary(event.getSummary());
            eventDto.getStart().setDateTime(event.getStartDateTime());
            eventDto.getEnd().setDateTime(event.getEndDateTime());

            googleOAuthService.createEvent(objectMapper.writeValueAsString(eventDto));
        }
        return ResponseEntity.ok().build();
    }

    private static final String baseEventTemplate = """
            {
              "summary": "Summary",
              "location": "",
              "description": "Type your description here",
              "start": {
                "dateTime": "2024-12-21T21:00:00",
                "timeZone": "Europe/Chisinau"
              },
              "end": {
                "dateTime": "2024-12-21T21:30:00",
                "timeZone": "Europe/Chisinau"
              },
              "recurrence": [
                "RRULE:FREQ=MONTHLY"
              ],
              "reminders": {
                "useDefault": false,
                "overrides": [
                  {
                    "method": "popup",
                    "minutes": 1440
                  },
                  {
                    "method": "popup",
                    "minutes": 720
                  },
                  {
                    "method": "popup",
                    "minutes": 360
                  },
                  {
                    "method": "popup",
                    "minutes": 120
                  }
                ]
              }
            }
            """;

    @Data
    public static class CreateEventRequest {
        private String summary;
        private String description;
        private String startDateTime;
        private String endDateTime;
    }

    @Data
    public static class EventDto {
        private String summary;
        private String location;
        private String description;
        private DateTimeDto start;
        private DateTimeDto end;
        private List<String> recurrence;
        private RemindersDto reminders;
    }

    @Data
    public static class DateTimeDto {
        private String dateTime;
        private String timeZone;
    }

    @Data
    public static class RemindersDto {
        private boolean useDefault;
        private List<ReminderOverrideDto> overrides;
    }

    @Data
    public static class ReminderOverrideDto {
        private String method;
        private int minutes;
    }
}
