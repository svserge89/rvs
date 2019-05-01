package sergesv.rvs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static org.springframework.format.annotation.DateTimeFormat.*;

@Component
@ConfigurationProperties(prefix = "rvs")
@Getter
@Setter
public class RvsPropertyResolver {
    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime maxVoteTime;
}
