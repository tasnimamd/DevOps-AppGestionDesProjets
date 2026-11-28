package tn.esprit.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EntrepriseDto;
import tn.esprit.backend.dto.EquipeDto;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@RequiredArgsConstructor
public class EquipeController {
    private final IEquipeService equipeService;

    @PostMapping("/add")
    public EquipeDto addEquipe(@RequestBody EquipeDto dto) {
        return toDto(equipeService.addEquipe(toEntity(dto)));
    }

    @PutMapping("/update")
    public EquipeDto updateEquipe(@RequestBody EquipeDto dto) {
        return toDto(equipeService.updateEquipe(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
    }

    @GetMapping("/get/{id}")
    public EquipeDto getEquipeById(@PathVariable Long id) {
        return toDto(equipeService.getEquipeById(id));
    }

    @GetMapping("/all")
    public List<EquipeDto> getAllEquipes() {
        return equipeService.getAllEquipes().stream().map(this::toDto).toList();
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public List<EquipeDto> getEquipesByEntreprise(@PathVariable Long entrepriseId) {
        return equipeService.getEquipesByEntreprise(entrepriseId).stream().map(this::toDto).toList();
    }

    @PutMapping("/assign-entreprise/{equipeId}/{entrepriseId}")
    public EquipeDto assignEquipeToEntreprise(@PathVariable Long equipeId, @PathVariable Long entrepriseId) {
        return toDto(equipeService.assignEquipeToEntreprise(equipeId, entrepriseId));
    }

    @PutMapping("/assign-projet/{equipeId}/{projetId}")
    public EquipeDto assignEquipeToProjet(@PathVariable Long equipeId, @PathVariable Long projetId) {
        return toDto(equipeService.assignEquipeToProjet(equipeId, projetId));
    }

    private Equipe toEntity(EquipeDto dto) {
        Equipe equipe = Equipe.builder().id(dto.id()).nom(dto.nom()).specialite(dto.specialite()).build();
        if (dto.entreprise() != null && dto.entreprise().id() != null) {
            equipe.setEntreprise(Entreprise.builder().id(dto.entreprise().id()).build());
        }
        return equipe;
    }

    private EquipeDto toDto(Equipe equipe) {
        if (equipe == null) return null;
        Entreprise entreprise = equipe.getEntreprise();
        EntrepriseDto entrepriseDto = entreprise == null ? null
                : new EntrepriseDto(entreprise.getId(), entreprise.getNom(), entreprise.getAdresse());
        return new EquipeDto(equipe.getId(), equipe.getNom(), equipe.getSpecialite(), entrepriseDto);
    }
}
