package ro.sd.a2.dto;

import lombok.*;
import ro.sd.a2.entity.BeautySalon;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

        private String id;

        private LocalDateTime dayHour;

        private Boolean available;

}
