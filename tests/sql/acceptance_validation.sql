-- Validation queries for LMS acceptance and data integrity checks
-- Execute with psql after running functional tests.

-- 1) Base volumes by table
SELECT 'alumnos' AS tabla, COUNT(*) AS total FROM alumnos
UNION ALL SELECT 'cursos', COUNT(*) FROM cursos
UNION ALL SELECT 'clases', COUNT(*) FROM clases
UNION ALL SELECT 'contenidos', COUNT(*) FROM contenidos
UNION ALL SELECT 'tareas', COUNT(*) FROM tareas
UNION ALL SELECT 'entregas', COUNT(*) FROM entregas
UNION ALL SELECT 'calificaciones', COUNT(*) FROM calificaciones
UNION ALL SELECT 'recomendaciones', COUNT(*) FROM recomendaciones
UNION ALL SELECT 'inscripciones', COUNT(*) FROM inscripciones
ORDER BY tabla;

-- 2) Referential integrity probes (should return 0 rows)
SELECT e.id
FROM entregas e
LEFT JOIN alumnos a ON a.id = e.alumno_id
WHERE a.id IS NULL;

SELECT e.id
FROM entregas e
LEFT JOIN tareas t ON t.id = e.tarea_id
WHERE t.id IS NULL;

SELECT i.id
FROM inscripciones i
LEFT JOIN alumnos a ON a.id = i.alumno_id
LEFT JOIN cursos c ON c.id = i.curso_id
WHERE a.id IS NULL OR c.id IS NULL;

-- 3) Delivery to grade consistency (domain-specific consistency check)
SELECT e.id AS entrega_id, c.id AS calificacion_id
FROM entregas e
LEFT JOIN calificaciones c
  ON c.alumno_id = e.alumno_id
WHERE c.id IS NULL;

-- 4) Duplicate enrollment detection (should return 0 rows)
SELECT alumno_id, curso_id, COUNT(*)
FROM inscripciones
GROUP BY alumno_id, curso_id
HAVING COUNT(*) > 1;

-- 5) Query performance quick checks (requires ANALYZE privilege)
EXPLAIN ANALYZE SELECT * FROM cursos;
EXPLAIN ANALYZE SELECT * FROM clases WHERE curso_id = 1;
EXPLAIN ANALYZE SELECT * FROM contenidos WHERE clase_id = 1;
EXPLAIN ANALYZE SELECT * FROM tareas WHERE clase_id = 1;
EXPLAIN ANALYZE SELECT * FROM recomendaciones WHERE alumno_id = 1;
