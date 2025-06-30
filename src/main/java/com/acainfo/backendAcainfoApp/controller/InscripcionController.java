package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Inscripcion;
import com.acainfo.backendAcainfoApp.service.InscripcionService;
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
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "CRUD de inscripciones de alumnos")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @Operation(summary = "Listar inscripciones")
    @ApiResponse(responseCode = "200", description = "Listado OK")
    @GetMapping
    public ResponseEntity<List<Inscripcion>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener inscripción por ID")
    @ApiResponse(responseCode = "200", description = "Inscripción encontrada")
    @ApiResponse(responseCode = "404", description = "No existe inscripción")
    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> getById(
            @Parameter(description = "ID de la inscripción") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear inscripción")
    @ApiResponse(responseCode = "201", description = "Inscripción creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Inscripcion> create(
            @Valid @RequestBody Inscripcion inscripcion) {
        Inscripcion created = service.create(inscripcion);
        return ResponseEntity
                .created(URI.create("/api/inscripciones/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar inscripción")
    @ApiResponse(responseCode = "200", description = "Inscripción actualizada")
    @ApiResponse(responseCode = "404", description = "No existe inscripción")
    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> update(
            @Parameter(description = "ID de la inscripción") @PathVariable Long id,
            @Valid @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(service.update(id, inscripcion));
    }

    @Operation(summary = "Eliminar inscripción")
    @ApiResponse(responseCode = "204", description = "Inscripción eliminada")
    @ApiResponse(responseCode = "404", description = "No existe inscripción")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la inscripción") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
