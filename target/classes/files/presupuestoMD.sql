-- Database: presupuestomd

-- DROP DATABASE "presupuestomd";

CREATE DATABASE "presupuestomd"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
-- SCHEMA: presupuestomd
-- DROP SCHEMA presupuestomd ;
CREATE SCHEMA presupuestomd
    AUTHORIZATION postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.usuario
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.usuario_seq;
CREATE SEQUENCE presupuestomd.usuario_seq;
ALTER SEQUENCE presupuestomd.usuario_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.usuario ;
CREATE TABLE presupuestomd.usuario
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.usuario_seq'::regclass),
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

ALTER TABLE presupuestomd.usuario
    OWNER to postgres;		

-- datos
insert into presupuestomd.usuario  (numero_documento, nombre, usuario, correo, cargo, rol, estado)
values ('1','Jarrison Garcia', 'jarrison','jarrison', 'Administrador', 'Administrador', true);
values ('2','Juan Monsalve', 'camilo','camilo', 'Administrador', 'Administrador', true);
-- -----------------------------------------------------
-- Table presupuestomd.gerencia
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.gerencia_seq;
CREATE SEQUENCE presupuestomd.gerencia_seq;
ALTER SEQUENCE presupuestomd.gerencia_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.gerencia ;
CREATE TABLE presupuestomd.gerencia (
  id integer NOT NULL DEFAULT nextval('presupuestomd.gerencia_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_gerencia PRIMARY KEY (id));

ALTER TABLE presupuestomd.gerencia
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.direccion
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.direccion_seq;
CREATE SEQUENCE presupuestomd.direccion_seq;
ALTER SEQUENCE presupuestomd.direccion_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.direccion ;
CREATE TABLE presupuestomd.direccion (
  id integer NOT NULL DEFAULT nextval('presupuestomd.direccion_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_direccion PRIMARY KEY (id));

ALTER TABLE presupuestomd.direccion
    OWNER to postgres;

-- -----------------------------------------------------
-- Table presupuestomd.jefatura
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.jefatura_seq;
CREATE SEQUENCE presupuestomd.jefatura_seq;
ALTER SEQUENCE presupuestomd.jefatura_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.jefatura ;
CREATE TABLE presupuestomd.jefatura (
  id integer NOT NULL DEFAULT nextval('presupuestomd.jefatura_seq'::regclass),
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_jefatura PRIMARY KEY (id));

ALTER TABLE presupuestomd.jefatura
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.cuenta
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.cuenta_seq;
CREATE SEQUENCE presupuestomd.cuenta_seq;
ALTER SEQUENCE presupuestomd.cuenta_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.cuenta ;
CREATE TABLE presupuestomd.cuenta (
  id integer NOT NULL DEFAULT nextval('presupuestomd.cuenta_seq'::regclass),
  cuenta VARCHAR(100) NULL,
  nombre VARCHAR(100) NULL,
  estado boolean not NULL,
  CONSTRAINT pk_cuenta PRIMARY KEY (id));

ALTER TABLE presupuestomd.cuenta
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.centrocosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.centrocosto_seq;
CREATE SEQUENCE presupuestomd.centrocosto_seq;
ALTER SEQUENCE presupuestomd.centrocosto_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.centrocosto ;
CREATE TABLE presupuestomd.centrocosto (
  id integer NOT NULL DEFAULT nextval('presupuestomd.centrocosto_seq'::regclass),
  centrocosto VARCHAR(100) NULL,
  id_gerencia integer not null,
  id_direccion integer not null,
  id_jefatura integer not null,
  estado boolean not NULL,
  CONSTRAINT pk_centrocosto PRIMARY KEY (id),
  CONSTRAINT fk_cc_gerencia FOREIGN KEY (id_gerencia)
        REFERENCES presupuestomd.gerencia (id) MATCH SIMPLE,
  CONSTRAINT fk_cc_direccion FOREIGN KEY (id_direccion)
        REFERENCES presupuestomd.direccion (id) MATCH SIMPLE,
  CONSTRAINT fk_cc_jefatura FOREIGN KEY (id_jefatura)
        REFERENCES presupuestomd.jefatura (id) MATCH SIMPLE);

ALTER TABLE presupuestomd.centrocosto
    OWNER to postgres;	
	
-- -----------------------------------------------------
-- Table presupuestomd.home
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.home_seq;
CREATE SEQUENCE presupuestomd.home_seq;
ALTER SEQUENCE presupuestomd.home_seq
    OWNER TO postgres; 
    
-- DROP TABLE presupuestomd.home ;
CREATE TABLE presupuestomd.home (
  id integer NOT NULL DEFAULT nextval('presupuestomd.home_seq'::regclass),
  url VARCHAR(100) NULL,
  nombre VARCHAR(100) NULL,
  fecha_inicio date NULL,
  fecha_fin date NULL,
  estado boolean not NULL,
  CONSTRAINT pk_home PRIMARY KEY (id));

ALTER TABLE presupuestomd.home
    OWNER to postgres;	
	
	
-- -----------------------------------------------------
-- Table presupuestomd.cueta_x_centrocosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.cuenta_x_centrocosto_seq;
CREATE SEQUENCE presupuestomd.cuenta_x_centrocosto_seq;
ALTER SEQUENCE presupuestomd.cuenta_x_centrocosto_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.cuenta_x_centrocosto ;
CREATE TABLE presupuestomd.cuenta_x_centrocosto (
  id integer NOT NULL DEFAULT nextval('presupuestomd.cuenta_x_centrocosto_seq'::regclass),
  id_centrocosto integer not NULL,
  id_cuenta integer not NULL,
  CONSTRAINT pk_cuenta_x_centrocosto PRIMARY KEY (id),
  CONSTRAINT fk_cxc_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestomd.cuenta (id) MATCH SIMPLE,
  CONSTRAINT fk_cxc_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestomd.centrocosto (id) MATCH SIMPLE);

ALTER TABLE presupuestomd.cuenta_x_centrocosto
    OWNER to postgres;

-- -----------------------------------------------------
-- Table presupuestomd.usuaio_x_ccosto
-- -----------------------------------------------------

-- DROP SEQUENCE presupuestomd.usuario_x_centrocosto_seq;
CREATE SEQUENCE presupuestomd.usuario_x_centrocosto_seq;
ALTER SEQUENCE presupuestomd.usuario_x_centrocosto_seq
    OWNER TO postgres; 

-- DROP TABLE presupuestomd.usuario_x_centrocosto ;
CREATE TABLE presupuestomd.usuario_x_centrocosto
(
    id integer NOT NULL DEFAULT nextval('presupuestomd.usuario_x_centrocosto_seq'::regclass),
	id_centrocosto integer not null,
	id_usuario_resp integer not null,
	id_usuario_aprini integer not null,
	id_usuario_aprfin integer not null,
    CONSTRAINT pk_usuario_x_centrocosto PRIMARY KEY (id),
    CONSTRAINT fk_uxc_usuario FOREIGN KEY (id_usuario_resp)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_usuario2 FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_usuario3 FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
	CONSTRAINT fk_uxc_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestomd.centrocosto (id) MATCH SIMPLE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE presupuestomd.usuario_x_centrocosto
    OWNER to postgres;
    
-- -----------------------------------------------------
-- Table presupuestomd.presupuesto
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.presupuesto_seq;
CREATE SEQUENCE presupuestomd.presupuesto_seq;
ALTER SEQUENCE presupuestomd.presupuesto_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.presupuesto ;
CREATE TABLE presupuestomd.presupuesto
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.presupuesto_seq'::regclass),
   nombre    		   CHARACTER VARYING (100) NULL,
   descripcion         CHARACTER VARYING (100) NULL,
   tipo                CHARACTER VARYING (20) NULL,
   clasificacion       CHARACTER VARYING (20) NULL,
   anio                INTEGER NOT NULL,	
   fecha_creacion	   DATE NULL,
   id_usuario	       INTEGER NOT NULL,
   CONSTRAINT pk_presupuesto PRIMARY KEY (id),
   CONSTRAINT fk_prep_usuario FOREIGN KEY (id_usuario)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.presupuesto
    OWNER to postgres;	 

-- -----------------------------------------------------
-- Table presupuestomd.detalle_presupuesto_mes
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.detalle_presupuesto_mes_seq;
CREATE SEQUENCE presupuestomd.detalle_presupuesto_mes_seq;
ALTER SEQUENCE presupuestomd.detalle_presupuesto_mes_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.detalle_presupuesto_mes ;
CREATE TABLE presupuestomd.detalle_presupuesto_mes
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.detalle_presupuesto_mes_seq'::regclass),
   id_presupuesto      INTEGER NOT NULL,
   observacion    	   CHARACTER VARYING (200) NULL,	
   valor_m1            real NOT NULL,
   valor_m2            real NOT NULL,
   valor_m3            real NOT NULL,
   valor_m4            real NOT NULL,
   valor_m5            real NOT NULL,
   valor_m6            real NOT NULL,
   valor_m7            real NOT NULL,
   valor_m8            real NOT NULL,
   valor_m9            real NOT NULL,
   valor_m10           real NOT NULL,
   valor_m11           real NOT NULL,
   valor_m12           real NOT NULL,
   total	           real NOT NULL,	
   estado              CHARACTER VARYING (20) NULL DEFAULT 'PENDIENTE',
   id_cuenta		   INTEGER NOT NULL,
   id_centrocosto	   INTEGER NOT NULL,
   id_usuario_aprini   INTEGER NOT NULL,
   id_usuario_aprfin   INTEGER NOT NULL,
   CONSTRAINT pk_detalle_presupuesto_mes PRIMARY KEY (id),
   CONSTRAINT fk_detalle_presupuesto_mes FOREIGN KEY (id_presupuesto)
        REFERENCES presupuestomd.presupuesto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestomd.cuenta (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestomd.centrocosto(id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.detalle_presupuesto_mes
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.detalle_presupuesto_campania
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.detalle_presupuesto_campania_seq;
CREATE SEQUENCE presupuestomd.detalle_presupuesto_campania_seq;
ALTER SEQUENCE presupuestomd.detalle_presupuesto_campania_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.detalle_presupuesto_mes ;
CREATE TABLE presupuestomd.detalle_presupuesto_campania
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.detalle_presupuesto_campania_seq'::regclass),
   id_presupuesto      INTEGER NOT NULL,
   observacion    	   CHARACTER VARYING (200) NULL,	
   valor_c1            real NOT NULL,
   valor_c2            real NOT NULL,
   valor_c3            real NOT NULL,
   valor_c4            real NOT NULL,
   valor_c5            real NOT NULL,
   valor_c6            real NOT NULL,
   valor_c7            real NOT NULL,
   valor_c8            real NOT NULL,
   valor_c9            real NOT NULL,
   valor_c10           real NOT NULL,
   valor_c11           real NOT NULL,
   valor_c12           real NOT NULL,	
   valor_c13           real NOT NULL,	
   valor_c14           real NOT NULL,	
   valor_c15           real NOT NULL,	
   valor_c16           real NOT NULL,		
   valor_c17           real NOT NULL,	
   valor_c18           real NOT NULL,	
   valor_c19           real NOT NULL,	
   valor_c20           real NOT NULL,	
   valor_c21           real NOT NULL,	
   valor_c22           real NOT NULL,	
   valor_c23           real NOT NULL,	
   valor_c24           real NOT NULL,	
   valor_c25           real NOT NULL,	
   total	           real NOT NULL,	
   estado              CHARACTER VARYING (20) NULL DEFAULT 'PENDIENTE',
   id_centrocosto	   INTEGER NOT NULL,
   id_cuenta		   INTEGER NOT NULL,
   id_usuario_aprini   INTEGER NOT NULL,
   id_usuario_aprfin   INTEGER NOT NULL,
   CONSTRAINT pk_detalle_presupuesto_campania PRIMARY KEY (id),
   CONSTRAINT fk_detalle_presupuesto_campania FOREIGN KEY (id_presupuesto)
        REFERENCES presupuestomd.presupuesto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES presupuestomd.cuenta (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES presupuestomd.centrocosto (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.detalle_presupuesto_campania
    OWNER to postgres;
	
-- -----------------------------------------------------
-- Table presupuestomd.observacion
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.observacion_seq;
CREATE SEQUENCE presupuestomd.observacion_seq;
ALTER SEQUENCE presupuestomd.observacion_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.observacion ;
CREATE TABLE presupuestomd.observacion
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.observacion_seq'::regclass),
   observacion    	   CHARACTER VARYING (200) NULL,
   usuario_envia       INTEGER NOT NULL,
   usuario_recibe      INTEGER NOT NULL,
   id_detalle_presupuesto_mes  INTEGER NULL,
   id_detalle_presupuesto_campania  INTEGER NULL,
   fecha			   DATE NOT NULL,
   estado              CHARACTER VARYING (20) NOT NULL,
   CONSTRAINT pk_observacion PRIMARY KEY (id),
   CONSTRAINT fk_ob_usuario_env FOREIGN KEY (usuario_envia)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_ob_usuario_rec FOREIGN KEY (usuario_recibe)
        REFERENCES presupuestomd.usuario (id) MATCH SIMPLE,
   CONSTRAINT fk_ob_dt_prep_mes FOREIGN KEY (id_detalle_presupuesto_mes)
        REFERENCES presupuestomd.detalle_presupuesto_mes (id) MATCH SIMPLE,
	CONSTRAINT fk_ob_dt_prep_campania FOREIGN KEY (id_detalle_presupuesto_campania)
        REFERENCES presupuestomd.detalle_presupuesto_campania (id) MATCH SIMPLE
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.observacion
    OWNER to postgres;	
								  
 -- -----------------------------------------------------
-- CAMBIOS
-- -----------------------------------------------------
-- actualizacion de tabla presupuesto
-- creacion de tabla observaciones


-- -----------------------------------------------------
-- Table presupuestomd.calculadora
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.calculadora_seq;
CREATE SEQUENCE presupuestomd.calculadora_seq;
ALTER SEQUENCE presupuestomd.calculadora_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.calculadora ;
CREATE TABLE presupuestomd.calculadora
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.calculadora_seq'::regclass),
   campana    		   INTEGER NULL,
   mes         		   INTEGER NULL,
   anio                INTEGER NULL,
   tipo    			   CHARACTER VARYING (20) NULL,
   porcentaje		   real null,
   CONSTRAINT pk_calculadora PRIMARY KEY (id)
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.calculadora
    OWNER to postgres;	 

 -- -----------------------------------------------------
-- Table presupuestomd.parametro
-- -----------------------------------------------------
-- DROP SEQUENCE presupuestomd.parametro_seq;
CREATE SEQUENCE presupuestomd.parametro_seq;
ALTER SEQUENCE presupuestomd.parametro_seq
    OWNER TO postgres;
    
-- DROP TABLE presupuestomd.parametro ;
CREATE TABLE presupuestomd.parametro
(
   id                  INTEGER NOT NULL DEFAULT nextval ('presupuestomd.parametro_seq'::regclass),
   codigo    		   CHARACTER VARYING (50) NULL,
   nombre    		   CHARACTER VARYING (50) NULL,
   valor         	   CHARACTER VARYING (50) NULL,
   CONSTRAINT pk_parametro PRIMARY KEY (id)
)
WITH (OIDS = FALSE);

ALTER TABLE presupuestomd.parametro
    OWNER to postgres;	 

insert into presupuestomd.parametro(codigo, nombre, valor)
values ('ANIO_CALCULADORA', 'Año Calculadora', '2018');
    
	   
    
	