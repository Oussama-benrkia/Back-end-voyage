package org.backend.voyage.dto.Rese;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.voyage.model.Reservation;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResevationResponse {
    private Long id;
    private LocalDateTime date;
    private Long User;
    private Long voyage;
    private String generator;

    public ResevationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.date=reservation.getCreatedAt();
        this.User=reservation.getUser().getId();
        this.voyage=reservation.getVoyage().getId();
        this.generator=reservation.getGenerator();

    }
}
