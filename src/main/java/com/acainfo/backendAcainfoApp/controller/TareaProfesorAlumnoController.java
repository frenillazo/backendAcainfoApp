package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumno;
import com.acainfo.backendAcainfoApp.domain.TareaProfesorAlumnoId;
import com.acainfo.backendAcainfoApp.service.TareaProfesorAlumnoService;
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
@RequestMapping("/api/tareas-profesor-alumno")
@Tag(name = "Tareas Profesor Alumno", description = "CRUD de asignaciones de tareas a alumnos")
public class TareaProfesorAlumnoController {

    private final TareaProfesorAlumnoService service;

    public TareaProfesorAlumnoController(TareaProfesorAlumnoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar asignaciones")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<TareaProfesorAlumno>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener asignación por ID")
    @ApiResponse(responseCode = "200", description = "Asignación encontrada")
    @ApiResponse(responseCode = "404", description = "No existe asignación")
    @GetMapping("/{tareaId}/{usuarioId}")
    public ResponseEntity<TareaProfesorAlumno> getById(
            @Parameter(description = "ID de la tarea") @PathVariable("tareaId") Long tareaId,
            @Parameter(description = "ID del usuario") @PathVariable("usuarioId") Long usuarioId) {
        TareaProfesorAlumnoId id = new TareaProfesorAlumnoId(tareaId, usuarioId);
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear asignación")
    @ApiResponse(responseCode = "201", description = "Asignación creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<TareaProfesorAlumno> create(
            @Valid @RequestBody TareaProfesorAlumno tpa) {
        TareaProfesorAlumno created = service.create(tpa);
        URI location = URI.create("/api/tareas-profesor-alumno/" + created.getIdTarea() + "/" + created.getIdUsuario());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Actualizar asignación")
    @ApiResponse(responseCode = "200", description = "Asignación actualizada")
    @ApiResponse(responseCode = "404", description = "No existe asignación")
    @PutMapping("/{tareaId}/{usuarioId}")
    public ResponseEntity<TareaProfesorAlumno> update(
            @Parameter(description = "ID de la tarea") @PathVariable("tareaId") Long tareaId,
            @Parameter(description = "ID del usuario") @PathVariable("usuarioId") Long usuarioId,
            @Valid @RequestBody TareaProfesorAlumno tpa) {
        TareaProfesorAlumnoId id = new TareaProfesorAlumnoId(tareaId, usuarioId);
        return ResponseEntity.ok(service.update(id, tpa));
    }

    @Operation(summary = "Eliminar asignación")
    @ApiResponse(responseCode = "204", description = "Asignación eliminada")
    @ApiResponse(responseCode = "404", description = "No existe asignación")
    @DeleteMapping("/{tareaId}/{usuarioId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la tarea") @PathVariable("tareaId") Long tareaId,
            @Parameter(description = "ID del usuario") @PathVariable("usuarioId") Long usuarioId) {
        TareaProfesorAlumnoId id = new TareaProfesorAlumnoId(tareaId, usuarioId);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar asignaciones por usuario")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<TareaProfesorAlumno>> byUsuario(
            @Parameter(description = "ID del usuario") @PathVariable("id") Long idUsuario) {
        return ResponseEntity.ok(service.findByIdUsuario(idUsuario));
    }

    @Operation(summary = "Listar asignaciones por tarea")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/tarea/{id}")
    public ResponseEntity<List<TareaProfesorAlumno>> byTarea(
            @Parameter(description = "ID de la tarea") @PathVariable("id") Long idTarea) {
        return ResponseEntity.ok(service.findByIdTarea(idTarea));
    }

    @Operation(summary = "Listar asignaciones por estado")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaProfesorAlumno>> byEstado(
            @Parameter(description = "Estado") @PathVariable("estado") String estado) {
        return ResponseEntity.ok(service.findByEstado(estado));
    }
}
