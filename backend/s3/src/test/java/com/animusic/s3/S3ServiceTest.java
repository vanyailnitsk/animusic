package com.animusic.s3;

import com.animusic.common.S3Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import software.amazon.awssdk.services.s3.S3Client;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({S3Config.class})
class S3ServiceTest {

    @Autowired
    S3Properties s3Properties;

    @MockBean
    S3Client s3Client;

    @Autowired
    S3Service s3Service;

    @Test
    void s3PropertiesAreNotNull() {
        assertThat(s3Properties.getUrl()).isNotNull();
        assertThat(s3Properties.getBucket()).isNotNull();
        assertThat(s3Properties.getRegion()).isNotNull();
        assertThat(s3Properties.getAccessKey()).isNotNull();
        assertThat(s3Properties.getSecretKey()).isNotNull();
    }

}