package animusic.conf.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "timeweb.s3")
@Data
@Slf4j
public class S3Properties {

    private String url;

    private String publicUrl;

    private String bucket;

    private String region;

    @Value("access-key")
    private String accessKey;

    @Value("secret-key")
//    @ToString.Exclude
    private String secretKey;

    @PostConstruct
    public void init() {
        log.info("Using S3 properties: {}", this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("url", url)
                .append("publicUrl", publicUrl)
                .append("bucket", bucket)
                .append("region", region)
                .append("accessKey", accessKey)
                .append("secret", secretKey.substring(0, 5) + "***:size=" + secretKey.length())
                .toString();
    }
}
