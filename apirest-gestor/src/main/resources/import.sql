INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, activo) VALUES ('Proyecto 1','Descripcion Proyecto 1','2022-03-18','2022-03-22',1);
INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, activo) VALUES ('Proyecto 2','Descripcion Proyecto 2','2022-03-19','2022-03-23',0);
INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, activo) VALUES ('Proyecto 3','Descripcion Proyecto 3','2022-03-20','2022-03-24',1);

INSERT INTO empleados (nombre, apellidos, email, telefono, activo, imagen, proyecto_id) VALUES('Pelayo','Garcia','pelayo@email.com',11111111,1,"",2);
INSERT INTO empleados (nombre, apellidos, email, telefono, activo, imagen, proyecto_id) VALUES('Carmen','Valverde','carmen@email.com',22222222,1,"",1);
INSERT INTO empleados (nombre, apellidos, email, telefono, activo, imagen, proyecto_id) VALUES('Diana','Castro','diana@email.com',33333333,0,"",3);
INSERT INTO empleados (nombre, apellidos, email, telefono, activo, imagen, proyecto_id) VALUES('Sergio','Sadaba','sergio@email.com',44444444,1,"",1);
INSERT INTO empleados (nombre, apellidos, email, telefono, activo, imagen, proyecto_id) VALUES('Jairo','Gonzalez','jairo@email.com',55555555,0,"",2);



INSERT INTO usuarios (username,password,enabled) VALUES ('pelayo', '$2a$10$pO.nraD5PPkeIg/TiaiI3.ydA081vVNgvFkNZlpFGKYHm0PJ22XTu', 1);
INSERT INTO usuarios (username,password,enabled) VALUES ('admin', '$2a$10$h3kT1Jm2jajKnDzQF0y3I.5D6NVgN7yBsSgEmUY4lOz1MwCT3HHCi', 1);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,1);