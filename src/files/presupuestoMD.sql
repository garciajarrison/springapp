-- -----------------------------------------------------
-- CAMBIOS
-- -----------------------------------------------------
-- actualizacion de tabla presupuesto
-- creacion de tabla observaciones


-- -----------------------------------------------------
-- Table presupuestoMD.calculadora
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestoMD.calculadora_seq;
CREATE SEQUENCE presupuestoMD.calculadora_seq;
ALTER SEQUENCE presupuestoMD.calculadora_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.calculadora ;
CREATE TABLE presupuestoMD.calculadora
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.calculadora_seq'::regclass),
   campana    		   INTEGER NULL,
   mes         		   INTEGER NULL,
   anio                INTEGER NULL,
   tipo    			   CHARACTER VARYING (20) NULL,
   porcentaje		   real null,
   CONSTRAINT pk_calculadora PRIMARY KEY (id)
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.calculadora
    OWNER to postgres;	 


-- -----------------------------------------------------
-- Schema PresupuestoMD
-- -----------------------------------------------------
CREATE DATABASE presupuestoMD
    WITH 
    OWNER = postgres
    ENCODING = UTF8
    LC_COLLATE = Spanish_Colombia.1252
    LC_CTYPE = Spanish_Colombia.1252
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- SCHEMA: presupuestoMD
-- DROP SCHEMA presupuestoMD ;
CREATE SCHEMA presupuestoMD
    AUTHORIZATION postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.usuario
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.usuario_seq;
CREATE SEQUENCE presupuestoMD.usuario_seq;
ALTER SEQUENCE presupuestoMD.usuario_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.usuario ;
CREATE TABLE presupuestoMD.usuario
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.usuario_seq'::regclass),
   numero_documento    CHARACTER VARYING (50) NULL,
   nombre              CHARACTER VARYING (50) NULL,
   usuario             CHARACTER VARYING (20) NULL,
   correo              CHARACTER VARYING (80) NULL,
   cargo    		   CHARACTER VARYING (80) NULL,
   rol           	   CHARACTER VARYING (150) NULL,
   estado              BOOLEAN             NOT NULL,
   CONSTRAINT pk_usuario PRIMARY KEY (id) NOT DEFERRABLE INITIALLY IMMEDIATE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.usuario
    OWNER to postgres;		

-- datos
insert into presupuestoMD.usuario  (numero_documento, nombre, usuario, correo, cargo, rol, estado)
values ('1','Jarrison Garcia', 'jarrison','jarrison', 'Administrador', 'Administrador', true);
values ('2','Juan Monsalve', 'camilo','camilo', 'Administrador', 'Administrador', true);
-- -----------------------------------------------------
-- Table presupuestoMD.gerencia
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.gerencia_seq;
CREATE SEQUENCE presupuestoMD.gerencia_seq;
ALTER SEQUENCE presupuestoMD.gerencia_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.gerencia ;
CREATE TABLE presupuestoMD.gerencia (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.gerencia_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_gerencia PRIMARY KEY (id));

ALTER TABLE presupuestoMD.gerencia
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.direccion
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.direccion_seq;
CREATE SEQUENCE presupuestoMD.direccion_seq;
ALTER SEQUENCE presupuestoMD.direccion_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.direccion ;
CREATE TABLE presupuestoMD.direccion (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.direccion_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_direccion PRIMARY KEY (id));

ALTER TABLE presupuestoMD.direccion
    OWNER to postgres;

-- -----------------------------------------------------
-- Table presupuestoMD.jefatura
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.jefatura_seq;
CREATE SEQUENCE presupuestoMD.jefatura_seq;
ALTER SEQUENCE presupuestoMD.jefatura_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.jefatura ;
CREATE TABLE presupuestoMD.jefatura (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.jefatura_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_jefatura PRIMARY KEY (id));

ALTER TABLE presupuestoMD.jefatura
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.cuenta
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.cuenta_seq;
CREATE SEQUENCE presupuestoMD.cuenta_seq;
ALTER SEQUENCE presupuestoMD.cuenta_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.cuenta ;
CREATE TABLE presupuestoMD.cuenta (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.cuenta_seq'::regclass),
  cuenta VARCHAR(100) NULL,
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_cuenta PRIMARY KEY (id));

ALTER TABLE presupuestoMD.cuenta
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.centrocosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.centrocosto_seq;
CREATE SEQUENCE presupuestoMD.centrocosto_seq;
ALTER SEQUENCE presupuestoMD.centrocosto_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.centrocosto ;
CREATE TABLE presupuestoMD.centrocosto (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.centrocosto_seq'::regclass),
  centrocosto VARCHAR(100) NULL,
  id_gerencia integer not null,
  id_direccion integer not null,
  id_jefatura integer not null,
  estado boolean not NULL,
  CONSTRAINT pk_centrocosto PRIMARY KEY (id),
  CONSTRAINT fk_cc_gerencia FOREIGN KEY (id_gerencia)
        REFERENCES presupuestoMD.gerencia (id) MATCH SIMPLE,
  CONSTRAINT fk_cc_direccion FOREIGN KEY (id_direccion)
        REFERENCES presupuestoMD.direccion (id) MATCH SIMPLE,
  CONSTRAINT fk_cc_jefatura FOREIGN KEY (id_jefatura)
        REFERENCES presupuestoMD.jefatura (id) MATCH SIMPLE);

ALTER TABLE presupuestoMD.centrocosto
    OWNER to postgres;	
	
-- -----------------------------------------------------
-- Table presupuestoMD.home
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.home_seq;
CREATE SEQUENCE presupuestoMD.home_seq;
ALTER SEQUENCE presupuestoMD.home_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestoMD.home ;
CREATE TABLE presupuestoMD.home (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.home_seq'::regclass),
  url VARCHAR(100) NULL,
  nombre VARCHAR(100) NULL,
  fecha_inicio date NULL,
  fecha_fin date NULL,
  estado boolean not NULL,
  CONSTRAINT pk_home PRIMARY KEY (id));

ALTER TABLE presupuestoMD.home
    OWNER to postgres;	
	
	
-- -----------------------------------------------------
-- Table presupuestoMD.cueta_x_centrocosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.cuenta_x_centrocosto_seq;
CREATE SEQUENCE presupuestoMD.cuenta_x_centrocosto_seq;
ALTER SEQUENCE presupuestoMD.cuenta_x_centrocosto_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.cuenta_x_centrocosto ;
CREATE TABLE presupuestoMD.cuenta_x_centrocosto (
  id integer NOT NULL DEFAULT nextval('presupuestoMD.cuenta_x_centrocosto_seq'::regclass),
  id_centrocosto integer not NULL,
  id_cuenta integer not NULL,
  CONSTRAINT pk_cuenta_x_centrocosto PRIMARY KEY (id),
  CONSTRAINT fk_cxc_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestoMD.cuenta (id) MATCH SIMPLE,
  CONSTRAINT fk_cxc_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestoMD.centrocosto (id) MATCH SIMPLE);

ALTER TABLE presupuestoMD.cuenta_x_centrocosto
    OWNER to postgres;

-- -----------------------------------------------------
-- Table presupuestoMD.usuaio_x_ccosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestoMD.usuario_x_centrocosto_seq;
CREATE SEQUENCE presupuestoMD.usuario_x_centrocosto_seq;
ALTER SEQUENCE presupuestoMD.usuario_x_centrocosto_seq
    OWNER TO postgres; 

-- DROP TABLE presupuestoMD.usuario_x_centrocosto ;
CREATE TABLE presupuestoMD.usuario_x_centrocosto
(
    id integer NOT NULL DEFAULT nextval('presupuestoMD.usuario_x_centrocosto_seq'::regclass),
	id_centrocosto integer not null,
	id_usuario_resp integer not null,
	id_usuario_aprini integer not null,
	id_usuario_aprfin integer not null,
    CONSTRAINT pk_usuario_x_centrocosto PRIMARY KEY (id),
    CONSTRAINT fk_uxc_usuario FOREIGN KEY (id_usuario_resp)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_usuario2 FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_usuario3 FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestoMD.centrocosto (id) MATCH SIMPLE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE presupuestoMD.usuario_x_centrocosto
    OWNER to postgres;
    
-- -----------------------------------------------------
-- Table presupuestoMD.presupuesto
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestoMD.presupuesto_seq;
CREATE SEQUENCE presupuestoMD.presupuesto_seq;
ALTER SEQUENCE presupuestoMD.presupuesto_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.presupuesto ;
CREATE TABLE presupuestoMD.presupuesto
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.presupuesto_seq'::regclass),
   nombre    		   CHARACTER VARYING (100) NULL,
   descripcion         CHARACTER VARYING (100) NULL,
   tipo                CHARACTER VARYING (20) NULL,
   clasificacion       CHARACTER VARYING (20) NULL,
   anio                INTEGER NOT NULL,	
   fecha_creacion	   DATE NULL,
   id_usuario	       INTEGER NOT NULL,
   CONSTRAINT pk_presupuesto PRIMARY KEY (id),
   CONSTRAINT fk_prep_usuario FOREIGN KEY (id_usuario)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.presupuesto
    OWNER to postgres;	 

-- -----------------------------------------------------
-- Table presupuestoMD.detalle_presupuesto_mes
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestoMD.detalle_presupuesto_mes_seq;
CREATE SEQUENCE presupuestoMD.detalle_presupuesto_mes_seq;
ALTER SEQUENCE presupuestoMD.detalle_presupuesto_mes_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.detalle_presupuesto_mes ;
CREATE TABLE presupuestoMD.detalle_presupuesto_mes
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.detalle_presupuesto_mes_seq'::regclass),
   id_presupuesto      INTEGER NOT NULL,
   observacion    	   CHARACTER VARYING (200) NULL,	
   valor_m1            double NOT NULL,
   valor_m2            double NOT NULL,
   valor_m3            double NOT NULL,
   valor_m4            double NOT NULL,
   valor_m5            double NOT NULL,
   valor_m6            double NOT NULL,
   valor_m7            double NOT NULL,
   valor_m8            double NOT NULL,
   valor_m9            double NOT NULL,
   valor_m10           double NOT NULL,
   valor_m11           double NOT NULL,
   valor_m12           double NOT NULL,	
   estado              CHARACTER VARYING (20) NULL DEFAULT 'PENDIENTE',
   id_centrocosto	   INTEGER NOT NULL,
   id_cuenta		   INTEGER NOT NULL,
   id_usuario_aprini   INTEGER NOT NULL,
   id_usuario_aprfin   INTEGER NOT NULL,
   CONSTRAINT pk_detalle_presupuesto_mes PRIMARY KEY (id),
   CONSTRAINT fk_detalle_presupuesto_mes FOREIGN KEY (id_presupuesto)
        REFERENCES presupuestoMD.presupuesto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestoMD.cuenta (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestoMD.centrocosto.(id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.detalle_presupuesto_mes
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.detalle_presupuesto_campania
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestoMD.detalle_presupuesto_campania_seq;
CREATE SEQUENCE presupuestoMD.detalle_presupuesto_campania_seq;
ALTER SEQUENCE presupuestoMD.detalle_presupuesto_campania_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.detalle_presupuesto_mes ;
CREATE TABLE presupuestoMD.detalle_presupuesto_campania
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.detalle_presupuesto_campania_seq'::regclass),
   id_presupuesto      INTEGER NOT NULL,
   observacion    	   CHARACTER VARYING (200) NULL,	
   valor_c1            double NOT NULL,
   valor_c2            double NOT NULL,
   valor_c3            double NOT NULL,
   valor_c4            double NOT NULL,
   valor_c5            double NOT NULL,
   valor_c6            double NOT NULL,
   valor_c7            double NOT NULL,
   valor_c8            double NOT NULL,
   valor_c9            double NOT NULL,
   valor_c10           double NOT NULL,
   valor_c11           double NOT NULL,
   valor_c12           double NOT NULL,	
   valor_c13           double NOT NULL,	
   valor_c14           double NOT NULL,	
   valor_c15           double NOT NULL,	
   valor_c16           double NOT NULL,		
   valor_c17           double NOT NULL,	
   valor_c18           double NOT NULL,	
   valor_c19           double NOT NULL,	
   valor_c20           double NOT NULL,	
   valor_c21           double NOT NULL,	
   valor_c22           double NOT NULL,	
   valor_c23           double NOT NULL,	
   valor_c24           double NOT NULL,	
   valor_c25           double NOT NULL,	
   estado              CHARACTER VARYING (20) NULL DEFAULT 'PENDIENTE',
   id_centrocosto	   INTEGER NOT NULL,
   id_cuenta		   INTEGER NOT NULL,
   id_usuario_aprini   INTEGER NOT NULL,
   id_usuario_aprfin   INTEGER NOT NULL,
   CONSTRAINT pk_detalle_presupuesto_campania PRIMARY KEY (id),
   CONSTRAINT fk_detalle_presupuesto_campania FOREIGN KEY (id_presupuesto)
        REFERENCES presupuestoMD.presupuesto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestoMD.cuenta (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestoMD.centrocosto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.detalle_presupuesto_campania
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestoMD.observacion
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestoMD.observacion_seq;
CREATE SEQUENCE presupuestoMD.observacion_seq;
ALTER SEQUENCE presupuestoMD.observacion_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestoMD.observacion ;
CREATE TABLE presupuestoMD.observacion
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestoMD.observacion_seq'::regclass),
   observacion    	   CHARACTER VARYING (200) NULL,
   usuario_envia       INTEGER NOT NULL,
   usuario_recibe      INTEGER NOT NULL,
   id_detalle_presupuesto_mes  INTEGER NOT NULL,
   id_detalle_presupuesto_campania  INTEGER NOT NULL,
   fecha			   DATE NOT NULL,
   estado              CHARACTER VARYING (20) NOT NULL,
   CONSTRAINT pk_observacion PRIMARY KEY (id),
   CONSTRAINT fk_ob_usuario_env FOREIGN KEY (usuario_envia)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_ob_usuario_rec FOREIGN KEY (usuario_recibe)
        REFERENCES presupuestoMD.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_ob_dt_prep_mes FOREIGN KEY (id_detalle_presupuesto_mes)
        REFERENCES presupuestoMD.detalle_presupuesto_mes (id) MATCH SIMPLE,
	CONSTRAINT fk_ob_dt_prep_campania FOREIGN KEY (id_detalle_presupuesto_campania)
        REFERENCES presupuestoMD.detalle_presupuesto_campania (id) MATCH SIMPLE,
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestoMD.observacion
    OWNER to postgres;	
	