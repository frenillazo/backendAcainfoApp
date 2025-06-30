package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Auditoria;
import com.acainfo.backendAcainfoApp.service.AuditoriaService;
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
@RequestMapping("/api/auditorias")
@Tag(name = "Auditorias", description = "Registros de auditoría")
public class AuditoriaController {

    private final AuditoriaService service;

    public AuditoriaController(AuditoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar auditorías")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<Auditoria>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener auditoría por ID")
    @ApiResponse(responseCode = "200", description = "Auditoría encontrada")
    @ApiResponse(responseCode = "404", description = "No existe auditoría")
    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> getById(
            @Parameter(description = "ID de la auditoría") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear auditoría")
    @ApiResponse(responseCode = "201", description = "Auditoría creada")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Auditoria> create(
            @Valid @RequestBody Auditoria auditoria) {
        Auditoria created = service.create(auditoria);
        return ResponseEntity
                .created(URI.create("/api/auditorias/" + created.getIdAuditoria()))
                .body(created);
    }

    @Operation(summary = "Actualizar auditoría")
    @ApiResponse(responseCode = "200", description = "Auditoría actualizada")
    @ApiResponse(responseCode = "404", description = "No existe auditoría")
    @PutMapping("/{id}")
    public ResponseEntity<Auditoria> update(
            @Parameter(description = "ID de la auditoría") @PathVariable Long id,
            @Valid @RequestBody Auditoria auditoria) {
        return ResponseEntity.ok(service.update(id, auditoria));
    }

    @Operation(summary = "Eliminar auditoría")
    @ApiResponse(responseCode = "204", description = "Auditoría eliminada")
    @ApiResponse(responseCode = "404", description = "No existe auditoría")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la auditoría") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar por tabla")
    @GetMapping("/tabla/{tabla}")
    public ResponseEntity<List<Auditoria>> byTabla(@PathVariable String tabla) {
        return ResponseEntity.ok(service.findByTabla(tabla));
    }

    @Operation(summary = "Listar por usuario")
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<Auditoria>> byUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok(service.findByUsuario(usuario));
    }

    @Operation(summary = "Listar por operación")
    @GetMapping("/operacion/{op}")
    public ResponseEntity<List<Auditoria>> byOperacion(@PathVariable("op") String operacion) {
        return ResponseEntity.ok(service.findByOperacion(operacion));
    }
}
