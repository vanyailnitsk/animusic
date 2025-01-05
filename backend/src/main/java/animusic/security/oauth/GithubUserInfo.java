package animusic.security.oauth;

import lombok.Data;

@Data
public class GithubUserInfo {
    public String login;
    public String name;
    public Long id;
    public String email;
    public String html_url;
    public String avatar_url;
}
