package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Role;
import com.acainfo.backendAcainfoApp.service.RoleService;
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
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones CRUD sobre roles de usuario")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @Operation(summary = "Listar roles", description = "Devuelve todos los roles disponibles")
    @ApiResponse(responseCode = "200", description = "Listado de roles exitoso")
    @GetMapping
    public ResponseEntity<List<Role>> list() {
        List<Role> roles = service.findAll();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Obtener un rol por ID", description = "Busca un rol existente por su identificador")
    @ApiResponse(responseCode = "200", description = "Rol encontrado")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(
            @Parameter(description = "ID del rol") @PathVariable Long id) {
        Role role = service.findById(id);
        return ResponseEntity.ok(role);
    }

    @Operation(summary = "Crear un nuevo rol", description = "Crea un rol con nombre único")
    @ApiResponse(responseCode = "201", description = "Rol creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o rol existente")
    @PostMapping
    public ResponseEntity<Role> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Detalles del rol a crear")
            @Valid @RequestBody Role role) {
        Role created = service.create(role);
        return ResponseEntity
                .created(URI.create("/api/roles/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar un rol existente", description = "Modifica el nombre de un rol")
    @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Role> update(
            @Parameter(description = "ID del rol a actualizar") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del rol")
            @Valid @RequestBody Role role) {
        Role updated = service.update(id, role);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar un rol", description = "Borra un rol por su identificador")
    @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del rol a borrar") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
