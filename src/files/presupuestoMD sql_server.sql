-- -----------------------------------------------------
-- Table dbo.usuario
-- -----------------------------------------------------
-- DROP TABLE dbo.usuario ;
CREATE TABLE dbo.usuario
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   numero_documento    varchar(50) NULL,
   nombre              varchar(50) NULL,
   usuario             varchar(20) NULL,
   correo              varchar(80) NULL,
   cargo    		   varchar(80) NULL,
   rol           	   varchar(150) NULL,
   estado              bit             NOT NULL
)

-- datos
USE [presupuestomd]
GO
INSERT INTO [dbo].[usuario] 
		([numero_documento],[nombre],[usuario],[correo],[cargo],[rol],[estado])
VALUES
		('1','Jarrison Garcia', 'jarrison','jarrison', 'Administrador', 'Administrador', 1);

INSERT INTO [dbo].[usuario] 
		([numero_documento],[nombre],[usuario],[correo],[cargo],[rol],[estado])
VALUES
		('2','Juan Monsalve', 'camilo','camilo', 'Administrador', 'Administrador', 1)
GO


-- -----------------------------------------------------
-- Table dbo.gerencia
-- -----------------------------------------------------
    
-- DROP TABLE dbo.gerencia ;
CREATE TABLE dbo.gerencia (
  id 		 		int IDENTITY(1,1) PRIMARY KEY,
  nombre 			VARCHAR(100) NULL,
  estado 			bit not NULL);
	
-- -----------------------------------------------------
-- Table dbo.direccion
-- ----------------------------------------------------- 
    
-- DROP TABLE dbo.direccion ;
CREATE TABLE dbo.direccion (
  id 			int IDENTITY(1,1) PRIMARY KEY,
  nombre 		VARCHAR(100) NULL,
  estado 		bit not NULL);

-- -----------------------------------------------------
-- Table dbo.jefatura
-- -----------------------------------------------------
    
-- DROP TABLE dbo.jefatura ;
CREATE TABLE dbo.jefatura (
  id 		int IDENTITY(1,1) PRIMARY KEY,
  nombre 	VARCHAR(100) NULL,
  estado 	bit not NULL);
	
-- -----------------------------------------------------
-- Table dbo.cuenta
-- -----------------------------------------------------
    
-- DROP TABLE dbo.cuenta ;
CREATE TABLE dbo.cuenta (
  id 		int IDENTITY(1,1) PRIMARY KEY,
  cuenta 	VARCHAR(100) NULL,
  nombre 	VARCHAR(100) NULL,
  estado 	bit not NULL);
	
-- -----------------------------------------------------
-- Table dbo.centrocosto
-- -----------------------------------------------------
    
-- DROP TABLE dbo.centrocosto ;
CREATE TABLE dbo.centrocosto (
  id 			int IDENTITY(1,1) PRIMARY KEY,
  centrocosto 	VARCHAR(100) NULL,
  id_gerencia 	int not null,
  id_direccion 	int not null,
  id_jefatura 	int not null,
  estado 		bit not NULL,
  CONSTRAINT fk_cc_gerencia FOREIGN KEY (id_gerencia)
        REFERENCES dbo.gerencia (id),
  CONSTRAINT fk_cc_direccion FOREIGN KEY (id_direccion)
        REFERENCES dbo.direccion (id),
  CONSTRAINT fk_cc_jefatura FOREIGN KEY (id_jefatura)
        REFERENCES dbo.jefatura (id));
	
-- -----------------------------------------------------
-- Table dbo.home
-- -----------------------------------------------------
    
-- DROP TABLE dbo.home ;
CREATE TABLE dbo.home (
  id 			int IDENTITY(1,1) PRIMARY KEY,
  url 			VARCHAR(100) NULL,
  nombre 		VARCHAR(100) NULL,
  fecha_inicio 	date NULL,
  fecha_fin 	date NULL,
  estado 		bit not NULL);
	
-- -----------------------------------------------------
-- Table dbo.cueta_x_centrocosto
-- -----------------------------------------------------

-- DROP TABLE dbo.cuenta_x_centrocosto ;
CREATE TABLE dbo.cuenta_x_centrocosto (
  id 				int IDENTITY(1,1) PRIMARY KEY,
  id_centrocosto 	int not NULL,
  id_cuenta 		int not NULL,
  CONSTRAINT fk_cxc_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES dbo.cuenta (id),
  CONSTRAINT fk_cxc_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES dbo.centrocosto (id));
		
-- -----------------------------------------------------
-- Table dbo.usuaio_x_ccosto
-- -----------------------------------------------------

-- DROP TABLE dbo.usuario_x_centrocosto ;
CREATE TABLE dbo.usuario_x_centrocosto
(
    id 					int IDENTITY(1,1) PRIMARY KEY,
	id_centrocosto 		int not null,
	id_usuario_resp 	int not null,
	id_usuario_aprini 	int not null,
	id_usuario_aprfin 	int not null,
    CONSTRAINT fk_uxc_usuario FOREIGN KEY (id_usuario_resp)
        REFERENCES dbo.usuario (id),
	CONSTRAINT fk_uxc_usuario2 FOREIGN KEY (id_usuario_aprini)
        REFERENCES dbo.usuario (id),
	CONSTRAINT fk_uxc_usuario3 FOREIGN KEY (id_usuario_aprfin)
        REFERENCES dbo.usuario (id),
	CONSTRAINT fk_uxc_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES dbo.centrocosto (id)
);

    
-- -----------------------------------------------------
-- Table dbo.presupuesto
-- -----------------------------------------------------

-- DROP TABLE dbo.presupuesto ;
CREATE TABLE dbo.presupuesto
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   nombre    		   varchar(100) NULL,
   descripcion         varchar(100) NULL,
   tipo                varchar(20) NULL,
   clasificacion       varchar(20) NULL,
   anio                int NOT NULL,	
   fecha_creacion	   DATE NULL,
   id_usuario	       int NOT NULL,
   CONSTRAINT fk_prep_usuario FOREIGN KEY (id_usuario)
        REFERENCES dbo.usuario (id)
); 

-- -----------------------------------------------------
-- Table dbo.detalle_presupuesto_mes
-- -----------------------------------------------------
    
-- DROP TABLE dbo.detalle_presupuesto_mes ;
CREATE TABLE dbo.detalle_presupuesto_mes
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   id_presupuesto      int NOT NULL,
   observacion    	   varchar(200) NULL,	
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
   estado              varchar(20) NULL DEFAULT 'PENDIENTE',
   id_cuenta		   int NOT NULL,
   id_centrocosto	   int NOT NULL,
   id_usuario_aprini   int NOT NULL,
   id_usuario_aprfin   int NOT NULL,
   CONSTRAINT fk_detalle_presupuesto_mes FOREIGN KEY (id_presupuesto)
        REFERENCES dbo.presupuesto (id),
   CONSTRAINT fk_detalle_presupuesto_mes_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES dbo.cuenta (id),
   CONSTRAINT fk_detalle_presupuesto_mes_centrocosto FOREIGN KEY (id_centrocosto)
        REFERENCES dbo.centrocosto(id),
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES dbo.usuario (id),
   CONSTRAINT fk_detalle_presupuesto_mes_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES dbo.usuario (id)
);
	
-- -----------------------------------------------------
-- Table dbo.detalle_presupuesto_campania
-- -----------------------------------------------------

-- DROP TABLE dbo.detalle_presupuesto_mes ;
CREATE TABLE dbo.detalle_presupuesto_campania
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   id_presupuesto      int NOT NULL,
   observacion    	   varchar(200) NULL,	
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
   estado              varchar(20) NULL DEFAULT 'PENDIENTE',
   id_centrocosto	   int NOT NULL,
   id_cuenta		   int NOT NULL,
   id_usuario_aprini   int NOT NULL,
   id_usuario_aprfin   int NOT NULL,
   CONSTRAINT fk_detalle_presupuesto_campania FOREIGN KEY (id_presupuesto)
        REFERENCES dbo.presupuesto (id),
   CONSTRAINT fk_detalle_presupuesto_campania_cuenta FOREIGN KEY (id_cuenta)
        REFERENCES dbo.cuenta (id),
   CONSTRAINT fk_detalle_presupuesto_campania_ccosto FOREIGN KEY (id_centrocosto)
        REFERENCES dbo.centrocosto (id),
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprini FOREIGN KEY (id_usuario_aprini)
        REFERENCES dbo.usuario (id),
   CONSTRAINT fk_detalle_presupuesto_campania_usuario_aprfin FOREIGN KEY (id_usuario_aprfin)
        REFERENCES dbo.usuario (id)
);
	
-- -----------------------------------------------------
-- Table dbo.observacion
-- -----------------------------------------------------
    
-- DROP TABLE dbo.observacion ;
CREATE TABLE dbo.observacion
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   observacion    	   varchar(200) NULL,
   usuario_envia       int NOT NULL,
   usuario_recibe      int NOT NULL,
   id_detalle_presupuesto_mes  int NULL,
   id_detalle_presupuesto_campania  int NULL,
   fecha			   DATE NOT NULL,
   estado              varchar(20) NOT NULL,
   CONSTRAINT fk_ob_usuario_env FOREIGN KEY (usuario_envia)
        REFERENCES dbo.usuario (id),
   CONSTRAINT fk_ob_usuario_rec FOREIGN KEY (usuario_recibe)
        REFERENCES dbo.usuario (id),
   CONSTRAINT fk_ob_dt_prep_mes FOREIGN KEY (id_detalle_presupuesto_mes)
        REFERENCES dbo.detalle_presupuesto_mes (id),
	CONSTRAINT fk_ob_dt_prep_campania FOREIGN KEY (id_detalle_presupuesto_campania)
        REFERENCES dbo.detalle_presupuesto_campania (id)
);	
								  

-- -----------------------------------------------------
-- Table dbo.calculadora
-- -----------------------------------------------------
    
-- DROP TABLE dbo.calculadora ;
CREATE TABLE dbo.calculadora
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   campana    		   int NULL,
   mes         		   int NULL,
   anio                int NULL,
   tipo    			   varchar(20) NULL,
   porcentaje		   real null
);	 

-- -----------------------------------------------------
-- Table dbo.parametro
-- -----------------------------------------------------

-- DROP TABLE dbo.parametro ;
CREATE TABLE dbo.parametro
(
   id                  int IDENTITY(1,1) PRIMARY KEY,
   codigo    		   varchar(50) NULL,
   nombre    		   varchar(50) NULL,
   valor         	   varchar(50) NULL
);

INSERT INTO [dbo].[parametro] 
		([codigo],[nombre],[valor])
VALUES
		('ANIO_CALCULADORA', 'Año Calculadora', '2018');
	   
    
	