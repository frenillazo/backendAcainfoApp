package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.TareaProfesor;
import com.acainfo.backendAcainfoApp.service.TareaProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tareas-profesor")
@Tag(name = "Tareas Profesor", description = "CRUD de tareas creadas por profesores")
public class TareaProfesorController {

    private final TareaProfesorService service;

    public TareaProfesorController(TareaProfesorService service) {
        this.service = service;
    }

    @Operation(summary = "Listar tareas")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<TareaProfesor>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener tarea por ID")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada")
    @ApiResponse(responseCode = "404", description = "No existe tarea")
    @GetMapping("/{id}")
    public ResponseEntity<TareaProfesor> getById(
            @Parameter(description = "ID de la tarea") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear tarea")
    @ApiResponse(responseCode = "201", description = "Tarea creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<TareaProfesor> create(
            @Valid @RequestBody TareaProfesor tarea) {
        TareaProfesor created = service.create(tarea);
        return ResponseEntity
                .created(URI.create("/api/tareas-profesor/" + created.getIdTarea()))
                .body(created);
    }

    @Operation(summary = "Actualizar tarea")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada")
    @ApiResponse(responseCode = "404", description = "No existe tarea")
    @PutMapping("/{id}")
    public ResponseEntity<TareaProfesor> update(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Valid @RequestBody TareaProfesor tarea) {
        return ResponseEntity.ok(service.update(id, tarea));
    }

    @Operation(summary = "Eliminar tarea")
    @ApiResponse(responseCode = "204", description = "Tarea eliminada")
    @ApiResponse(responseCode = "404", description = "No existe tarea")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la tarea") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar tareas por profesor")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/profesor/{id}")
    public ResponseEntity<List<TareaProfesor>> byProfesor(
            @Parameter(description = "ID del profesor") @PathVariable("id") Long profesorId) {
        return ResponseEntity.ok(service.findByProfesorId(profesorId));
    }

    @Operation(summary = "Listar tareas por visibilidad")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/visibilidad/{vis}")
    public ResponseEntity<List<TareaProfesor>> byVisibilidad(
            @Parameter(description = "Visibilidad") @PathVariable("vis") String vis) {
        return ResponseEntity.ok(service.findByVisibilidad(vis));
    }
}
