package animusic.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import animusic.api.controller.AuthController;
import animusic.api.exception.InvalidTokenException;
import animusic.api.exception.UserAlreadyExistsException;
import animusic.core.db.model.RefreshToken;
import animusic.core.db.model.User;
import animusic.core.db.table.RefreshTokenRepository;
import animusic.security.oauth.GithubUserInfo;
import animusic.security.oauth.GoogleUserInfo;
import animusic.service.security.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationProvider authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final AuthProvidersService authProvidersService;

    @Transactional
    public JwtResponse register(AuthController.RegisterRequest request) {
        String email = request.getEmail();
        if (userService.userExistsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email %s already exists".formatted(email));
        }
        User user = userService.createUserData(request.getUsername(), email, request.getPassword());
        userService.saveUser(user);
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }

    @Transactional
    public JwtResponse loginByPassword(@NotNull String email, @NotNull String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        User user = userService.findByEmailOrThrow(email);
        return authenticateUser(user);
    }

    @Transactional
    public JwtResponse externalLogin(String provider, String code, String state, String redirectUri) {
        if ("github".equals(provider)) {
            GithubUserInfo userInfo = authProvidersService.getGitHubUserInfo(code);
            User user = userService.findOrCreateUserByOAuth(
                    provider,
                    userInfo.id.toString(),
                    userInfo.email,
                    userInfo.name,
                    userInfo.avatar_url);
            return authenticateUser(user);
        } else if ("google".equals(provider)) {
            GoogleUserInfo userInfo = authProvidersService.getGoogleUserInfo(code, redirectUri);
            User user = userService.findOrCreateUserByOAuth(
                    provider,
                    userInfo.sub,
                    userInfo.email,
                    userInfo.name,
                    userInfo.picture);
            return authenticateUser(user);
        }
        throw new IllegalArgumentException("Unsupported provider: " + provider);
    }

    @Transactional
    public JwtResponse authenticateUser(User user) {
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }

    @Transactional
    public JwtResponse updateToken(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getRefreshFromCookie(request);
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh-токен не найден!"));
        refreshTokenService.verifyExpiration(token);
        User user = token.getUser();
        refreshTokenRepository.deleteById(token.getId());
        String jwt = jwtService.createToken(user);
        ResponseCookie responseCookie = refreshTokenService.generateRefreshCookie(user);
        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshTokenCookie(responseCookie)
                .user(user)
                .build();
    }
}
