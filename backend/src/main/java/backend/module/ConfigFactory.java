package backend.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigFactory {
    private static Config instance;
    private static final Logger LOG = LoggerFactory.getLogger(ConfigFactory.class);

    public static synchronized Config getConfig() {
        if (instance == null) {
            InputStream inputStream = Config.class.getResourceAsStream(getName());
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            try {
                instance = objectMapper.readValue(inputStream, Config.class);
            } catch (IOException e) {
                LOG.error(e.toString());
                instance = new Config();
                instance.setDaoType("mem");
            }
        }
        LOG.info(instance.getDaoType());
        return instance;
    }

    private static String getName() {
        final StringBuilder sb = new StringBuilder("/application");
        String profile = System.getenv("PROFILE");
        if (profile != null && !profile.isEmpty()) {
            LOG.info("Detected profile {}", profile);
            sb.append('-').append(profile);
        }
        return sb.append(".yaml").toString();
    }
}
