INSERT INTO `alumno` (`id`, `username`, `password`, `nombre`, `apellidos`, `telefono`, `email`) VALUES (1, 'RubDHS', '$2a$10$.nlNNZl4VJVbiJUxLtOd5e8VKE/jv7EyBXqCSKVYSKEu.T4WT5Z7e', 'Ruben', 'De Haro Sanchez', '615294786', 'ruben@example.es');

INSERT INTO `profesor` (`id`, `username`, `password`, `nombre`, `apellidos`, `email`) VALUES (1, 'Juangu', '$2a$10$.nlNNZl4VJVbiJUxLtOd5e8VKE/jv7EyBXqCSKVYSKEu.T4WT5Z7e', 'Juan Gualberto', 'Gutierrez Marin', 'juangu@example.es');

INSERT INTO `asignatura` (`id`, `nombre`, `curso`, `ciclo`) VALUES (1, 'Acceso A Datos', 'Segundo', 'DAM');

INSERT INTO `gestor` (`id`, `username`, `password`) VALUES (1, 'Admin', '$2a$10$by25NsxQ8Rwvi6FcFL8ROuQQ2ktdt.ftXvuqLV8T7cTc3zL1.gcTi');

INSERT INTO `usuario` (`id`, `username`, `password`, `authority`, `alumno_id`) VALUES (1, 'RubDHS', '$2a$10$.nlNNZl4VJVbiJUxLtOd5e8VKE/jv7EyBXqCSKVYSKEu.T4WT5Z7e', 'ALUMNO', 1);
INSERT INTO `usuario` (`id`, `username`, `password`, `authority`, `profesor_id`) VALUES (2, 'Juangu', '$2a$10$.nlNNZl4VJVbiJUxLtOd5e8VKE/jv7EyBXqCSKVYSKEu.T4WT5Z7e', 'PROFESOR', 1);
INSERT INTO `usuario` (`id`, `username`, `password`, `authority`, `gestor_id`) VALUES (3, 'Admin', '$2a$10$by25NsxQ8Rwvi6FcFL8ROuQQ2ktdt.ftXvuqLV8T7cTc3zL1.gcTi', 'GESTOR', 1);
