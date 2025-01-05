package animusic.security;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import animusic.security.oauth.GithubUserInfo;
import animusic.security.oauth.GoogleUserInfo;

@Service
public class AuthProvidersService {

    private final OAuth2ClientProperties clientProperties;
    private final OAuth2ClientProperties.Registration githubRegistration;
    private final OAuth2ClientProperties.Registration googleRegistration;
    private final OAuth2ClientProperties.Provider githubProvider;
    private final OAuth2ClientProperties.Provider googleProvider;

    public AuthProvidersService(OAuth2ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
        this.githubRegistration = clientProperties.getRegistration().get("github");
        this.googleRegistration = clientProperties.getRegistration().get("google");
        this.githubProvider = clientProperties.getProvider().get("github");
        this.googleProvider = clientProperties.getProvider().get("google");
    }

    public String exchangeCodeForTokenGithub(String code) {
        String url = githubProvider.getTokenUri();
        WebClient webClient = WebClient.create(url);
        var clientID = githubRegistration.getClientId();
        var clientSecret = githubRegistration.getClientSecret();
        String accessToken = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("client_id", clientID)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .build())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .<String>handle((response, sink) -> {
                    try {
                        sink.next(new ObjectMapper().readTree(response).get("access_token").asText());
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                }).block();
        return accessToken;
    }

    public GithubUserInfo getGitHubUserInfo(String code) {
        String accessToken = exchangeCodeForTokenGithub(code);
        WebClient client = WebClient.create(githubProvider.getUserInfoUri());
        return client.get()
                .header("Authorization", "Bearer " + accessToken)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .retrieve()
                .bodyToMono(GithubUserInfo.class)
                .block();
    }

    public Map<String, Object> exchangeCodeForTokenGoogle(String code, String redirectUri) {
        WebClient webClient = WebClient.create();

        String tokenEndpoint = googleProvider.getTokenUri();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", googleRegistration.getClientId());
        formData.add("client_secret", googleRegistration.getClientSecret());
        formData.add("code", code);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        Map<String, Object> response = webClient.post()
                .uri(tokenEndpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        return response;
    }

    public GoogleUserInfo getGoogleUserInfo(String code, String redirectUri) {
        String accessToken = exchangeCodeForTokenGoogle(code, redirectUri).get("access_token").toString();
        String userInfoEndpoint = googleProvider.getUserInfoUri();
        WebClient webClient = WebClient.create();

        GoogleUserInfo userInfo = webClient.get()
                .uri(userInfoEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GoogleUserInfo.class)
                .block();

        return userInfo;
    }
}
