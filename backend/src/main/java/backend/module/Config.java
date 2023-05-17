package backend.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Slf4j
public class Config {
    private String daoType;
    private String jdbcUser;
    private String jdbcPassword;
    private String jdbcDatabase;
    private String jdbcUrl;
    private String jdbcDriver;
    private int jdbcPoolSize;
}


