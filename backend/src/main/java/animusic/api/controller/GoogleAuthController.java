package animusic.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.security.AuthProvidersService;
import animusic.security.oauth.GoogleUserInfo;

@RestController
@RequestMapping("/api/oauth2/google")
@RequiredArgsConstructor
@Profile("!prod")
public class GoogleAuthController {

    private final AuthProvidersService authProvidersService;
    private final OAuth2ClientProperties properties;

    @GetMapping("/token")
    public ResponseEntity<?> exchangeCodeForAccessToken(@RequestParam("code") String code) {
        GoogleUserInfo userInfo = authProvidersService.getGoogleUserInfo(
                code, "http://localhost:8080/api/oauth2/google/token"
        );
        return ResponseEntity.ok(userInfo);
    }

}
