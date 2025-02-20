CREATE TABLE usuario (
  id_user BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE refresh_token (
  id BIGSERIAL PRIMARY KEY,
  version BIGINT,
  token VARCHAR(255),
  expiry_date TIMESTAMP WITHOUT TIME ZONE,
  id_user BIGINT,
  FOREIGN KEY (id_user) REFERENCES usuario(id_user)
);

CREATE TABLE operador (
  id_operador BIGSERIAL PRIMARY KEY,
  nombre_operador VARCHAR(45) NOT NULL,
  img_logo VARCHAR(255) NOT NULL
);

CREATE TABLE tipo_cliente (
  id_tipo_cliente SERIAL PRIMARY KEY,
  tipo_cliente VARCHAR(30) NOT NULL
);

CREATE TABLE cliente (
  id_cliente BIGSERIAL PRIMARY KEY,
  nombre_cliente VARCHAR(50),
  contrato VARCHAR(50),
  nit INT,
  id_tipo_cliente BIGINT,
  FOREIGN KEY (id_tipo_cliente) REFERENCES tipo_cliente(id_tipo_cliente)
);

CREATE TABLE planta(
  id_planta VARCHAR(255) PRIMARY KEY,
  nombre_planta VARCHAR(255) NOT NULL,
  centro_costos VARCHAR(255) NOT NULL,
  id_cliente BIGINT,
  asunto VARCHAR(255),
  url_img VARCHAR(255),
  id_operador BIGINT,
  valor_unidad DOUBLE PRECISION,
  porcentaje_aumento DOUBLE PRECISION,
  FOREIGN KEY (id_operador) REFERENCES operador (id_operador),
  FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE factura (
  id_factura BIGSERIAL PRIMARY KEY,
  fecha_inicial TIMESTAMP WITHOUT TIME ZONE,
  fecha_final TIMESTAMP WITHOUT TIME ZONE,
  dias_facturados INT,
  cufe VARCHAR(255),
  fecha_pago TIMESTAMP WITHOUT TIME ZONE,
  fecha_dian TIMESTAMP WITHOUT TIME ZONE,
  pdf VARCHAR(250),
  numero_factura VARCHAR(30),
  concepto_facturado TEXT,
  id_planta VARCHAR (255),
  id_cliente BIGINT,
  FOREIGN KEY (id_planta) REFERENCES planta (id_planta),
  FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
);

CREATE TABLE facturacion_especial (
  id_facturacion_especial BIGSERIAL PRIMARY KEY,
  excedente DOUBLE PRECISION,
  costo_agregado DOUBLE PRECISION,
  valor_exportacion DOUBLE PRECISION,
  id_planta VARCHAR (255),
  mes INT,
  anio INT,
  FOREIGN KEY (id_planta) REFERENCES planta (id_planta)
);

CREATE TABLE tarifa_operadores (
  id_tarifa_operador BIGSERIAL PRIMARY KEY,
  tarifa_operador DOUBLE PRECISION,
  mes INT,
  anio INT,
  id_operador BIGINT,
  FOREIGN KEY (id_operador) REFERENCES operador(id_operador)
);

CREATE TABLE generacion (
  id_generacion BIGSERIAL PRIMARY KEY,
  generacion_actual DOUBLE PRECISION,
  generacion_acumulado DOUBLE PRECISION,
  valor_unidad DOUBLE PRECISION,
  valor_total DOUBLE PRECISION,
  diferencia_tarifa DOUBLE PRECISION,
  ahorro_actual DOUBLE PRECISION,
  ahorro_acumulado DOUBLE PRECISION,
  ahorro_codos_actual DOUBLE PRECISION,
  ahorro_codos_acumulado DOUBLE PRECISION,
  anio INT,
  mes INT,
  id_tarifa_operador BIGINT,
  id_planta VARCHAR (255),
  FOREIGN KEY (id_planta) REFERENCES planta (id_planta),
  FOREIGN KEY (id_tarifa_operador) REFERENCES tarifa_operadores(id_tarifa_operador)
);

CREATE TABLE email (
  id_email BIGSERIAL PRIMARY KEY,
  email VARCHAR(255),
  id_planta VARCHAR(255),
  FOREIGN KEY (id_planta) REFERENCES planta(id_planta)
);

INSERT INTO tipo_cliente (tipo_cliente) VALUES 
('Normal'),
('Especial');

INSERT INTO operador (nombre_operador,img_logo) VALUES 
('EPM','https://www.epm.com.co/content/dam/epm/iconos/logo.svg'),
('Air-e','https://www.air-e.com/Portals/aire/logo-aire.png');

INSERT INTO tarifa_operadores (anio, mes, tarifa_operador, id_operador) VALUES
(2024, 1, 1009.00, 1),
(2024, 2, 974.72, 1),
(2024, 3, 990.84, 1),
(2024, 4, 1009.04, 1),
(2024, 5, 1062.36, 1),
(2024, 6, 941.82, 1),
(2024, 1, 1357.62, 2),
(2024, 2, 1354.1, 2),
(2024, 3, 1409.6, 2),
(2024, 4, 1375.23, 2),
(2024, 5, 1391.36, 2),
(2024, 6, 1172.07, 2);

INSERT INTO cliente (nombre_cliente, contrato, nit, id_tipo_cliente) VALUES 
('Ceipa', 'FE13256', 890910961, 1),
('Liceo Frances', 'FE578964', 900620017, 1),
('Incubant', 'FE245678', 900762687, 1),
('Punto Clave', 'FE12312', 900004830, 2),
('Lemont', 'FE367431', 901064820, 1),
('Pollocoa', 'FE23448', 900134841, 1);

INSERT INTO planta (id_planta, nombre_planta, centro_costos, id_cliente, id_operador, asunto, url_img, valor_unidad, porcentaje_aumento) VALUES
('505', 'PUNTO CLAVE', 'PUNTOCLAVESSFV', 4, 1, 'Detalle de Consumo de Energia Solar - Punto Clave', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 409.25, 0),
('506', 'CEIPA BARRANQUILLA', 'CEIPABARRANQUIL', 1, 2, 'Detalle de Consumo de Energia Solar - Ceipa Barranquilla', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 614.90, 0),
('507', 'CEIPA SABANETA', 'CEIPASABANETASS', 1, 1, 'Detalle de Consumo de Energia Solar - Ceipa Sabaneta', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 614.90, 0),
('508', 'LICEO FRANCES', 'LICEOFRANCESSFV', 2, 1, 'Detalle de Consumo de Energia Solar - Liceo Frances', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 614.90, 0),
('511', 'LEMONT PORTERIA', 'LEMONT1PORTERIA', 5, 1, 'Detalle de Consumo de Energia Solar - Lemont Porteria', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 649.60, 0),
('512', 'LEMONT SALÓN SOCIAL', 'LEMONT1SALONSOC', 5, 1, 'Detalle de Consumo de Energia Solar - Lemont Salon Social', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 649.60, 0),
('513', 'POLLOCOA', 'POLLOCOASSFV', 6, 1, 'Detalle de Consumo de Energia Solar - Pollocoa', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 520.17, 0),
('514', 'INCUBANT', 'INCUBANSSFV', 3, 1, 'Detalle de Consumo de Energia Solar - Incubant', 'https://lh3.googleusercontent.com/u/0/drive-viewer/AKGpihYD3UTyWMa_ArwG9u7bDzO8KvSGykZ4ITjo2hCuiL2GWiTd5ri77dAOkVwFkEsPzhAw1dYnHENkEv6IJvh1kk5K31penVj-_Xw=w1879-h977-rw-v1', 519.19, 0);

INSERT INTO facturacion_especial (id_planta, excedente, costo_agregado, valor_exportacion, mes, anio) VALUES 
('505', 1992003.5, 992034.625, 989968.875, 1, 2024),
('505', 1993003.5, 993034.625, 991968.875, 2, 2024),
('505', 1994003.5, 994034.625, 993968.875, 3, 2024),
('505', 1995003.5, 995034.625, 995968.875, 4, 2024),
('505', 1996003.5, 996034.625, 997968.875, 5, 2024),
('505', 1997003.5, 997034.625, 999968.875, 6, 2024);

INSERT INTO factura (fecha_inicial, fecha_final, dias_facturados, cufe, fecha_dian, pdf, numero_factura, id_planta, concepto_facturado, id_cliente, fecha_pago) VALUES 
('2024-01-01', '2024-01-31', 31, 'e82ddc4bf34931e2974b7c8af7ac0cc5d1d06772a0b85228566c8073b21402203', '2024-01-25', 'https://google.com', 'FE10001', '508', 'Concepto factura', 2, '2024-02-15'),
('2024-01-01', '2024-01-31', 31, 'e82ddc4bf34931e2974b7c8af7aa0f6c3c40ffdcc5d1d06772a0b85228566c8073b21402203', '2024-01-25', 'https://google.com', 'FE10123', '512', 'Concepto factura', 5, '2024-02-15');

INSERT INTO email (email, id_planta) VALUES
('cmoralese@cesde.net', '506'),
('cmoralese@cesde.net', '508'),
('cmoralese@cesde.net', '514'),
('cmoralese@cesde.net', '505'),
('cmoralese@cesde.net', '512'),
('cmoralese@cesde.net', '513'),
('cmoralese@cesde.net', '507'),
('cmoralese@cesde.net', '511');

INSERT INTO usuario (username, password) VALUES 
('admin@admin.com', 'admin');
