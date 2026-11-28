package tn.esprit.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EntrepriseDto;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

@RestController
@RequestMapping("/entreprise")
@RequiredArgsConstructor
public class EntrepriseController {
    private final IEntrepriseService entrepriseService;

    @PostMapping("/add")
    public EntrepriseDto addEntreprise(@RequestBody EntrepriseDto dto) {
        return toDto(entrepriseService.addEntreprise(toEntity(dto)));
    }

    @PutMapping("/update")
    public EntrepriseDto updateEntreprise(@RequestBody EntrepriseDto dto) {
        return toDto(entrepriseService.updateEntreprise(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEntreprise(@PathVariable Long id) {
        entrepriseService.deleteEntreprise(id);
    }

    @GetMapping("/get/{id}")
    public EntrepriseDto getEntrepriseById(@PathVariable Long id) {
        return toDto(entrepriseService.getEntrepriseById(id));
    }

    @GetMapping("/all")
    public List<EntrepriseDto> getAllEntreprises() {
        return entrepriseService.getAllEntreprises().stream().map(this::toDto).toList();
    }

    private Entreprise toEntity(EntrepriseDto dto) {
        return Entreprise.builder().id(dto.id()).nom(dto.nom()).adresse(dto.adresse()).build();
    }

    private EntrepriseDto toDto(Entreprise entreprise) {
        return entreprise == null ? null
                : new EntrepriseDto(entreprise.getId(), entreprise.getNom(), entreprise.getAdresse());
    }
}
