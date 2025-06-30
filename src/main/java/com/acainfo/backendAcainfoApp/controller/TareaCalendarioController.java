package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.TareaCalendario;
import com.acainfo.backendAcainfoApp.service.TareaCalendarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tareas-calendario")
@Tag(name = "Tareas Calendario", description = "CRUD de tareas de calendario")
public class TareaCalendarioController {

    private final TareaCalendarioService service;

    public TareaCalendarioController(TareaCalendarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar tareas")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<TareaCalendario>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener tarea por ID")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada")
    @ApiResponse(responseCode = "404", description = "No existe tarea")
    @GetMapping("/{id}")
    public ResponseEntity<TareaCalendario> getById(
            @Parameter(description = "ID de la tarea") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear tarea")
    @ApiResponse(responseCode = "201", description = "Tarea creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<TareaCalendario> create(
            @Valid @RequestBody TareaCalendario tarea) {
        TareaCalendario created = service.create(tarea);
        return ResponseEntity
                .created(URI.create("/api/tareas-calendario/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar tarea")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada")
    @ApiResponse(responseCode = "404", description = "No existe tarea")
    @PutMapping("/{id}")
    public ResponseEntity<TareaCalendario> update(
            @Parameter(description = "ID de la tarea") @PathVariable Long id,
            @Valid @RequestBody TareaCalendario tarea) {
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

    @Operation(summary = "Listar tareas por usuario")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<TareaCalendario>> byUsuario(
            @Parameter(description = "ID del usuario") @PathVariable("id") Long usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @Operation(summary = "Listar tareas por estado")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/completada/{flag}")
    public ResponseEntity<List<TareaCalendario>> byCompletada(
            @Parameter(description = "Completada") @PathVariable("flag") Boolean flag) {
        return ResponseEntity.ok(service.findByCompletada(flag));
    }

    @Operation(summary = "Listar tareas próximas")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/desde/{fecha}")
    public ResponseEntity<List<TareaCalendario>> byFechaAfter(
            @Parameter(description = "Fecha desde (ISO)") @PathVariable("fecha") LocalDateTime fecha) {
        return ResponseEntity.ok(service.findByFechaAfter(fecha));
    }
}
