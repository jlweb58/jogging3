package com.webber.jogging.strava;


import com.webber.jogging.strava.service.StravaWebhookHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/jogging/strava-api")
public class StravaActivityWebhookController {

    private final StravaWebhookHandler webhookHandler;

    public StravaActivityWebhookController(StravaWebhookHandler webhookHandler) {
        this.webhookHandler = webhookHandler;
    }

    @GetMapping
    public ResponseEntity<WebhookValidationResponse> validateWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {
        log.info("Received webhook validation request. Mode: {}, Challenge: {}, Token: {}", mode, challenge, verifyToken);

        // Verify the mode and token if needed
        if (!"subscribe".equals(mode)) {
            return ResponseEntity.badRequest().build();
        }

        // Return the challenge in the required format
        return ResponseEntity.ok(new WebhookValidationResponse(challenge));
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhookEvent(@RequestBody StravaWebhookEvent event) {
        log.info("Received webhook event. Type: {}, Object Type: {}, Object ID: {}, Owner ID: {}",
                event.aspectType(), event.objectType(), event.objectId(), event.ownerId());
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
                webhookHandler.handleActivityCreated(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted while handling webhook event: {}", e.getMessage());
            }
        });
        return ResponseEntity.ok().build();
    }
}
