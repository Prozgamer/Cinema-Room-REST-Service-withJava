package cinema.Configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "cinema")
public record CinemaProps (
 Hall Hall,
 Price Price
){
    public record Hall(
            @DefaultValue("9") int rows,
            @DefaultValue("9") int columns){}
    public record Price(
            @DefaultValue("10") int firstRows,
            @DefaultValue("8") int lastRows,
            @DefaultValue("4") int nFirstRows){}
}
