package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Asignatura;
import com.acainfo.backendAcainfoApp.service.AsignaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
@Tag(name = "Asignaturas", description = "CRUD de asignaturas")
public class AsignaturaController {

    private final AsignaturaService service;

    public AsignaturaController(AsignaturaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar asignaturas")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<Asignatura>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener asignatura por ID")
    @ApiResponse(responseCode = "200", description = "Asignatura encontrada")
    @ApiResponse(responseCode = "404", description = "No existe asignatura")
    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> getById(
            @Parameter(description = "ID de la asignatura") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear asignatura")
    @ApiResponse(responseCode = "201", description = "Asignatura creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Asignatura> create(
            @Valid @RequestBody Asignatura asignatura) {
        Asignatura created = service.create(asignatura);
        return ResponseEntity
                .created(URI.create("/api/asignaturas/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar asignatura")
    @ApiResponse(responseCode = "200", description = "Asignatura actualizada")
    @ApiResponse(responseCode = "404", description = "No existe asignatura")
    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> update(
            @Parameter(description = "ID de la asignatura") @PathVariable Long id,
            @Valid @RequestBody Asignatura asignatura) {
        return ResponseEntity.ok(service.update(id, asignatura));
    }

    @Operation(summary = "Eliminar asignatura")
    @ApiResponse(responseCode = "204", description = "Asignatura eliminada")
    @ApiResponse(responseCode = "404", description = "No existe asignatura")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la asignatura") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}