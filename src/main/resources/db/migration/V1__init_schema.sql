-- V1__init_schema.sql
-- Esquema inicial para AcainfoApp

-- Tabla de usuarios
CREATE TABLE usuario (
  id_usuario     SERIAL PRIMARY KEY,
  nombre         VARCHAR(100)    NOT NULL,
  email          VARCHAR(150)    NOT NULL UNIQUE,
  password       VARCHAR(255)    NOT NULL,
  carrera        VARCHAR(100),
  curso          INTEGER,
  metodo_pago    VARCHAR(100),
  rol            VARCHAR(20)     NOT NULL,          -- ADMIN / USER
  activo         BOOLEAN         NOT NULL DEFAULT TRUE,
  foto_url       VARCHAR(255)
);

-- Tabla de asignaturas
CREATE TABLE asignatura (
  id_asignatura  SERIAL PRIMARY KEY,
  nombre         VARCHAR(150)    NOT NULL,
  carrera        VARCHAR(100),
  curso          INTEGER,
  cuatrimestre   INTEGER,
  descripcion    TEXT,
  activo         BOOLEAN         NOT NULL DEFAULT TRUE
);

-- Tabla de inscripciones
CREATE TABLE inscripcion (
  id_inscripcion SERIAL PRIMARY KEY,
  id_usuario     INTEGER         NOT NULL,
  id_asignatura  INTEGER         NOT NULL,
  fecha_alta     TIMESTAMP       NOT NULL DEFAULT NOW(),
  fecha_baja     TIMESTAMP,
  estado         VARCHAR(20)     NOT NULL,
  CONSTRAINT fk_inscripcion_usuario    FOREIGN KEY (id_usuario)    REFERENCES usuario(id_usuario),
  CONSTRAINT fk_inscripcion_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura)
);

-- Tabla de material de asignaturas
CREATE TABLE material (
  id_material     SERIAL PRIMARY KEY,
  id_asignatura   INTEGER         NOT NULL,
  tipo            VARCHAR(20)     NOT NULL,               -- video/pdf/codigo/git
  titulo          VARCHAR(200)    NOT NULL,
  url             TEXT            NOT NULL,
  fecha_subida    TIMESTAMP       NOT NULL DEFAULT NOW(),
  fecha_expiracion TIMESTAMP,
  CONSTRAINT fk_material_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura)
);

-- Tabla de profesores (extiende usuario)
CREATE TABLE profesor (
  id_profesor    INTEGER PRIMARY KEY,                      -- igual a usuario.id_usuario
  especialidad   VARCHAR(100),
  activo         BOOLEAN     NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_profesor_usuario FOREIGN KEY (id_profesor) REFERENCES usuario(id_usuario)
);

-- Tabla de horarios de asignaturas
CREATE TABLE horario (
  id_horario     SERIAL PRIMARY KEY,
  id_asignatura  INTEGER     NOT NULL,
  id_profesor    INTEGER     NOT NULL,
  fecha          DATE        NOT NULL,
  hora_inicio    TIME        NOT NULL,
  hora_fin       TIME        NOT NULL,
  activo         BOOLEAN     NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_horario_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignatura(id_asignatura),
  CONSTRAINT fk_horario_profesor    FOREIGN KEY (id_profesor)     REFERENCES profesor(id_profesor)
);

-- Tabla de tareas personales del usuario
CREATE TABLE tarea_calendario (
  id             SERIAL PRIMARY KEY,
  id_usuario     INTEGER         NOT NULL,
  titulo         VARCHAR(200)    NOT NULL,
  descripcion    TEXT,
  fecha          TIMESTAMP       NOT NULL,
  hora_aviso     TIME,
  completada     BOOLEAN         NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_tarea_calendario_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de tareas creadas por profesores
CREATE TABLE tarea_profesor (
  id_tarea        SERIAL PRIMARY KEY,
  id_profesor     INTEGER         NOT NULL,
  titulo          VARCHAR(200)    NOT NULL,
  descripcion     TEXT,
  fecha_creacion  TIMESTAMP       NOT NULL DEFAULT NOW(),
  fecha_ejecucion TIMESTAMP       NOT NULL,
  visibilidad     VARCHAR(20)     NOT NULL,               -- ALL / CUSTOM
  CONSTRAINT fk_tarea_profesor_profesor FOREIGN KEY (id_profesor) REFERENCES profesor(id_profesor)
);

-- Asociación de tareas de profesor a alumnos
CREATE TABLE tarea_profesor_alumno (
  id_tarea      INTEGER NOT NULL,
  id_usuario    INTEGER NOT NULL,
  estado        VARCHAR(20) NOT NULL,                     -- PENDING / COMPLETED / CANCELLED
  PRIMARY KEY (id_tarea, id_usuario),
  CONSTRAINT fk_tpa_tarea   FOREIGN KEY (id_tarea)   REFERENCES tarea_profesor(id_tarea),
  CONSTRAINT fk_tpa_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de notificaciones enviadas
CREATE TABLE notificacion (
  id_notificacion SERIAL PRIMARY KEY,
  id_usuario      INTEGER         NOT NULL,
  tipo            VARCHAR(20)     NOT NULL,               -- PUSH / EMAIL / IN_APP
  titulo          VARCHAR(100)    NOT NULL,
  cuerpo          TEXT,
  enviada_en      TIMESTAMP       NOT NULL DEFAULT NOW(),
  leida           BOOLEAN         NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_notificacion_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de auditoría de cambios
CREATE TABLE auditoria (
  id_auditoria      SERIAL PRIMARY KEY,
  tabla             VARCHAR(50)      NOT NULL,
  id_registro       BIGINT           NOT NULL,
  operacion         VARCHAR(10)      NOT NULL,            -- INSERT / UPDATE / DELETE
  datos_anteriores  JSONB,
  datos_nuevos      JSONB,
  usuario           VARCHAR(100),
  timestamp         TIMESTAMP       NOT NULL DEFAULT NOW()
);