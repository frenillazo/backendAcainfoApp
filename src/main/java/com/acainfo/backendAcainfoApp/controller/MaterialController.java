package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Material;
import com.acainfo.backendAcainfoApp.service.MaterialService;
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
@RequestMapping("/api/materiales")
@Tag(name = "Materiales", description = "CRUD de materiales de asignaturas")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @Operation(summary = "Listar materiales")
    @ApiResponse(responseCode = "200", description = "Listado correcto")
    @GetMapping
    public ResponseEntity<List<Material>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener material por ID")
    @ApiResponse(responseCode = "200", description = "Material encontrado")
    @ApiResponse(responseCode = "404", description = "No existe material")
    @GetMapping("/{id}")
    public ResponseEntity<Material> getById(
            @Parameter(description = "ID del material") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear material")
    @ApiResponse(responseCode = "201", description = "Material creado")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ResponseEntity<Material> create(
            @Valid @RequestBody Material material) {
        Material created = service.create(material);
        return ResponseEntity
                .created(URI.create("/api/materiales/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar material")
    @ApiResponse(responseCode = "200", description = "Material actualizado")
    @ApiResponse(responseCode = "404", description = "No existe material")
    @PutMapping("/{id}")
    public ResponseEntity<Material> update(
            @Parameter(description = "ID del material") @PathVariable Long id,
            @Valid @RequestBody Material material) {
        return ResponseEntity.ok(service.update(id, material));
    }

    @Operation(summary = "Eliminar material")
    @ApiResponse(responseCode = "204", description = "Material eliminado")
    @ApiResponse(responseCode = "404", description = "No existe material")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del material") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
