package person.zxccong.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.EnvironmentInfoContributor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Component
    public class CustomBuildInfoContributor implements InfoContributor{

        @Override
        public void contribute(Info.Builder builder) {
            builder.withDetail("my.build", Collections.singletonMap("timestamp", new Date()));
        }
    }

    @Component
    public class ConfigServerHealthIndicator implements HealthIndicator{

        @Override
        public Health health() {
            try {
                final URL url = new URL("http://localhost:8080/actuator/health");
                final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                final int responseCode = urlConnection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    return Health.up().build();
                } else {
                    return Health.down().withDetail("Http Status", responseCode).build();
                }
            } catch (IOException e) {
                return Health.down(e).build();
            }

        }
    }

}
