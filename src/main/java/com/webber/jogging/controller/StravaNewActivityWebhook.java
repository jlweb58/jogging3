package com.webber.jogging.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jogging/strava-api")
public class StravaNewActivityWebhook {

    private static final Logger LOG = LoggerFactory.getLogger(StravaNewActivityWebhook.class);

    @GetMapping
    public ResponseEntity<WebhookValidationResponse> validateWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {

        LOG.info("Received webhook validation request. Mode: {}, Challenge: {}, Token: {}", mode, challenge, verifyToken);

        // Verify the mode and token if needed
        if (!"subscribe".equals(mode)) {
            return ResponseEntity.badRequest().build();
        }

        // Return the challenge in the required format
        return ResponseEntity.ok(new WebhookValidationResponse(challenge));
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhookEvent(@RequestBody StravaWebhookEvent event) {
        LOG.info("Received webhook event. Type: {}, Object Type: {}, Object ID: {}, Owner ID: {}",
                event.aspectType(), event.objectType(), event.objectId(), event.ownerId());

        return ResponseEntity.ok().build();
    }
}
