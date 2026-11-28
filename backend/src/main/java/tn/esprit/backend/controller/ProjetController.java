package tn.esprit.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDto;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

@RestController
@RequestMapping("/projet")
@RequiredArgsConstructor
public class ProjetController {
    private final IProjetService projetService;

    @PostMapping("/add")
    public ProjetDto addProjet(@RequestBody ProjetDto dto) {
        return toDto(projetService.addProjet(toEntity(dto)));
    }

    @PutMapping("/update")
    public ProjetDto updateProjet(@RequestBody ProjetDto dto) {
        return toDto(projetService.updateProjet(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }

    @GetMapping("/get/{id}")
    public ProjetDto getProjetById(@PathVariable Long id) {
        return toDto(projetService.getProjetById(id));
    }

    @GetMapping("/all")
    public List<ProjetDto> getAllProjets() {
        return projetService.getAllProjets().stream().map(this::toDto).toList();
    }

    private Projet toEntity(ProjetDto dto) {
        return Projet.builder().id(dto.id()).sujet(dto.sujet()).build();
    }

    private ProjetDto toDto(Projet projet) {
        return projet == null ? null : new ProjetDto(projet.getId(), projet.getSujet());
    }
}
