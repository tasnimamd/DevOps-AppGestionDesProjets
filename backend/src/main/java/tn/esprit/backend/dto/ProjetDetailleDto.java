package tn.esprit.backend.dto;

import java.time.LocalDate;

public record ProjetDetailleDto(
        Long id,
        String description,
        String technologie,
        Double coutProvisoire,
        LocalDate dateDebut,
        ProjetDto projet
) {
}
