package com.pitty.service.impl;

//import com.pitty.domain.Ingrediente;
import com.pitty.domain.Postre;
import com.pitty.domain.RecetaDetalle;
import com.pitty.dto.receta.RecetaDetalleRequestDTO;
import com.pitty.dto.receta.RecetaResponseDTO;
import com.pitty.exception.NotFoundException;
import com.pitty.repository.IngredienteRepository;
import com.pitty.repository.PostreRepository;
import com.pitty.repository.RecetaDetalleRepository;
import com.pitty.service.RecetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaDetalleRepository recetaRepo;
    private final PostreRepository postreRepo;
    private final IngredienteRepository ingredienteRepo;

    public RecetaServiceImpl(RecetaDetalleRepository recetaRepo,
                             PostreRepository postreRepo,
                             IngredienteRepository ingredienteRepo) {
        this.recetaRepo = recetaRepo;
        this.postreRepo = postreRepo;
        this.ingredienteRepo = ingredienteRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public RecetaResponseDTO getByPostre(Long postreId) {
        var postre = postreRepo.findById(postreId)
                .orElseThrow(() -> new NotFoundException("Postre id=" + postreId + " no encontrado"));

        var detalles = recetaRepo.findByPostreId(postreId);
        return toResponse(postre, detalles);
    }

    @Override
    @Transactional
    public RecetaResponseDTO replaceRecipe(Long postreId, List<RecetaDetalleRequestDTO> items) {
        var postre = postreRepo.findById(postreId)
                .orElseThrow(() -> new NotFoundException("Postre id=" + postreId + " no encontrado"));

        // borrar receta actual
        var actuales = recetaRepo.findByPostreId(postreId);
        recetaRepo.deleteAllInBatch(actuales);

        // crear nuevos
        for (var dto : items) {
            var ing = ingredienteRepo.findById(dto.getIngredienteId())
                    .orElseThrow(() -> new NotFoundException("Ingrediente id=" + dto.getIngredienteId() + " no encontrado"));

            var det = new RecetaDetalle();
            det.setPostre(postre);
            det.setIngrediente(ing);
            det.setCantidadPorPostre(dto.getCantidadPorPostre());
            det.setMermaPct(dto.getMermaPct());
            recetaRepo.save(det);
        }

        var nuevos = recetaRepo.findByPostreId(postreId);
        return toResponse(postre, nuevos);
    }

    @Override
    @Transactional
    public RecetaResponseDTO addOrUpdateItem(Long postreId, RecetaDetalleRequestDTO item) {
        var postre = postreRepo.findById(postreId)
                .orElseThrow(() -> new NotFoundException("Postre id=" + postreId + " no encontrado"));

        var ing = ingredienteRepo.findById(item.getIngredienteId())
                .orElseThrow(() -> new NotFoundException("Ingrediente id=" + item.getIngredienteId() + " no encontrado"));

        // buscar si ya existe postre+ingrediente
        var existente = recetaRepo.findByPostreIdAndIngredienteId(postreId, item.getIngredienteId()).orElse(null);

        if (existente == null) {
            existente = new RecetaDetalle();
            existente.setPostre(postre);
            existente.setIngrediente(ing);
        }
        existente.setCantidadPorPostre(item.getCantidadPorPostre());
        existente.setMermaPct(item.getMermaPct());
        recetaRepo.save(existente);

        var detalles = recetaRepo.findByPostreId(postreId);
        return toResponse(postre, detalles);
    }

    @Override
    @Transactional
    public void deleteItem(Long postreId, Long ingredienteId) {
        var existente = recetaRepo.findByPostreIdAndIngredienteId(postreId, ingredienteId)
                .orElseThrow(() -> new NotFoundException("Detalle de receta no encontrado (postre=" + postreId + ", ingrediente=" + ingredienteId + ")"));

        recetaRepo.delete(existente);
    }

    private RecetaResponseDTO toResponse(Postre postre, List<RecetaDetalle> detalles) {
        var dto = new RecetaResponseDTO();
        dto.setPostreId(postre.getId());
        dto.setPostreNombre(postre.getNombre());

        var items = detalles.stream().map(d -> {
            var it = new RecetaResponseDTO.Item();
            it.setIngredienteId(d.getIngrediente().getId());
            it.setIngredienteNombre(d.getIngrediente().getNombre());
            it.setUnidad(d.getIngrediente().getUnidad());
            it.setCantidadPorPostre(d.getCantidadPorPostre());
            it.setMermaPct(d.getMermaPct());
            return it;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}