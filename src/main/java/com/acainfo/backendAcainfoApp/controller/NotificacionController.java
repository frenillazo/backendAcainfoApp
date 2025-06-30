package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Notificacion;
import com.acainfo.backendAcainfoApp.service.NotificacionService;
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
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Gestor de notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @Operation(summary = "Listar notificaciones")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<Notificacion>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener notificacion por ID")
    @ApiResponse(responseCode = "200", description = "Notificacion encontrada")
    @ApiResponse(responseCode = "404", description = "No existe notificacion")
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getById(
            @Parameter(description = "ID de la notificacion") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear notificacion")
    @ApiResponse(responseCode = "201", description = "Notificacion creada")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    @PostMapping
    public ResponseEntity<Notificacion> create(
            @Valid @RequestBody Notificacion notificacion) {
        Notificacion created = service.create(notificacion);
        return ResponseEntity
                .created(URI.create("/api/notificaciones/" + created.getIdNotificacion()))
                .body(created);
    }

    @Operation(summary = "Actualizar notificacion")
    @ApiResponse(responseCode = "200", description = "Notificacion actualizada")
    @ApiResponse(responseCode = "404", description = "No existe notificacion")
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> update(
            @Parameter(description = "ID de la notificacion") @PathVariable Long id,
            @Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(service.update(id, notificacion));
    }

    @Operation(summary = "Eliminar notificacion")
    @ApiResponse(responseCode = "204", description = "Notificacion eliminada")
    @ApiResponse(responseCode = "404", description = "No existe notificacion")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la notificacion") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar por usuario")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Notificacion>> byUsuario(@PathVariable("id") Long usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @Operation(summary = "Listar por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> byTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.findByTipo(tipo));
    }

    @Operation(summary = "Listar por leida")
    @GetMapping("/leida/{flag}")
    public ResponseEntity<List<Notificacion>> byLeida(@PathVariable("flag") Boolean leida) {
        return ResponseEntity.ok(service.findByLeida(leida));
    }
}
