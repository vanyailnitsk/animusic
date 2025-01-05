package animusic.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.security.AuthProvidersService;
import animusic.security.AuthService;
import animusic.security.oauth.GithubUserInfo;

@RestController
@RequestMapping("/api/oauth2/github")
@RequiredArgsConstructor
@Profile("!prod")
public class GithubAuthController {
    private final AuthService authService;
    private final AuthProvidersService authProvidersService;

    @GetMapping("/token")
    public GithubUserInfo getToken(@RequestParam("code") String code, @RequestParam("state") String state) {
        GithubUserInfo userInfo = authProvidersService.getGitHubUserInfo(code);
        return userInfo;
    }

    @GetMapping("/test")
    public void securedUrl() {
        System.out.println("Hello World");
    }
}
