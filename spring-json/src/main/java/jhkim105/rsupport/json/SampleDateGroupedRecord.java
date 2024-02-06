package jhkim105.rsupport.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SampleDateGroupedRecord(
    LocalDate date,
    LocalDateTime dateTime,
    List<Sample> samples
) {
}


