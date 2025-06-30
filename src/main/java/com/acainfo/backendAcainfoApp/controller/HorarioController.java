package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Horario;
import com.acainfo.backendAcainfoApp.service.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@Tag(name = "Horarios", description = "CRUD de horarios")
public class HorarioController {

    private final HorarioService service;

    public HorarioController(HorarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar horarios")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<Horario>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener horario por ID")
    @ApiResponse(responseCode = "200", description = "Horario encontrado")
    @ApiResponse(responseCode = "404", description = "No existe horario")
    @GetMapping("/{id}")
    public ResponseEntity<Horario> getById(
            @Parameter(description = "ID del horario") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear horario")
    @ApiResponse(responseCode = "201", description = "Horario creado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Horario> create(
            @Valid @RequestBody Horario horario) {
        Horario created = service.create(horario);
        return ResponseEntity
                .created(URI.create("/api/horarios/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar horario")
    @ApiResponse(responseCode = "200", description = "Horario actualizado")
    @ApiResponse(responseCode = "404", description = "No existe horario")
    @PutMapping("/{id}")
    public ResponseEntity<Horario> update(
            @Parameter(description = "ID del horario") @PathVariable Long id,
            @Valid @RequestBody Horario horario) {
        return ResponseEntity.ok(service.update(id, horario));
    }

    @Operation(summary = "Eliminar horario")
    @ApiResponse(responseCode = "204", description = "Horario eliminado")
    @ApiResponse(responseCode = "404", description = "No existe horario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del horario") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar horarios por asignatura")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/asignatura/{id}")
    public ResponseEntity<List<Horario>> byAsignatura(
            @Parameter(description = "ID de la asignatura") @PathVariable("id") Long asignaturaId) {
        return ResponseEntity.ok(service.findByAsignaturaId(asignaturaId));
    }

    @Operation(summary = "Listar horarios por profesor")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/profesor/{id}")
    public ResponseEntity<List<Horario>> byProfesor(
            @Parameter(description = "ID del profesor") @PathVariable("id") Long profesorId) {
        return ResponseEntity.ok(service.findByProfesorId(profesorId));
    }

    @Operation(summary = "Listar horarios por fecha")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Horario>> byFecha(
            @Parameter(description = "Fecha (YYYY-MM-DD)") @PathVariable("fecha") LocalDate fecha) {
        return ResponseEntity.ok(service.findByFecha(fecha));
    }
}
