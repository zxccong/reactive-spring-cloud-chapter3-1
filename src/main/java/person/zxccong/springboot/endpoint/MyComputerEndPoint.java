package person.zxccong.springboot.endpoint;

import org.apache.tomcat.util.net.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxccong
 * @date 2022/3/18
 */
@Component
@Endpoint(id = "mycomputer")
public class MyComputerEndPoint {

    private Map<String, String> features = new ConcurrentHashMap<>();

    @ReadOperation
    public Map<String, String> features() {
        return features;
    }

    @ReadOperation
    public String feature(@Selector String name) {
        return features.get(name);
    }

    @WriteOperation
    public void configureFeature(@Selector String name, String feature) {
        features.put(name, feature);
    }

    @DeleteOperation
    public void deleteFeature(@Selector String name) {
        features.remove(name);
    }



}
