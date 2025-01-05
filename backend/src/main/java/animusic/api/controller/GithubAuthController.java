package animusic.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import animusic.security.AuthProvidersService;
import animusic.security.AuthService;

@RestController
@RequestMapping("/api/oauth2/github")
@RequiredArgsConstructor
@Profile("!prod")
public class GithubAuthController {
    private final AuthService authService;
    private final AuthProvidersService authProvidersService;

    @GetMapping("/token")
    public String getToken(@RequestParam("code") String code, @RequestParam("state") String state) {
        /**
         * Запрос на получение кода выглядит так
         * https://github.com/login/oauth/authorize?response_type=code&client_id=Ov23liKJHg2I3blUU0Nx&scope=read:user&state=vLTrqxBeBzhqc0-TqR7CJW98knpQFBn6jLSpSOknB_Y%3D&redirect_uri=http://localhost:8080/api/oauth2/github/token
         */
//        String tokenGithub = authProvidersService.exchangeCodeForTokenGithub(code);
        return code;
    }

    @GetMapping("/test")
    public void securedUrl() {
        System.out.println("Hello World");
    }
}
