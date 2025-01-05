package animusic.security.oauth;

import lombok.Data;

@Data
public class GoogleUserInfo {
    public String sub;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String email;
}
