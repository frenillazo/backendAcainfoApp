package com.acainfo.backendAcainfoApp.controller;

import com.acainfo.backendAcainfoApp.domain.Usuario;
import com.acainfo.backendAcainfoApp.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD sobre usuarios (alumnos)")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Listado exitoso")
    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario existente por su identificador")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un usuario (alumno)")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o email duplicado")
    @PostMapping
    public ResponseEntity<Usuario> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a crear")
            @Valid @RequestBody Usuario usuario) {
        Usuario created = service.create(usuario);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Actualizar un usuario", description = "Modifica datos de un usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(
            @Parameter(description = "ID del usuario") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del usuario")
            @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.update(id, usuario));
    }

    @Operation(summary = "Eliminar un usuario", description = "Baja de un usuario por su ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del usuario") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}