package animusic.api.dto;

public record AuthRequest(
        String email,
        String password
) {
}
