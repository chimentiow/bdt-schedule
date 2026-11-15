-- @query 1 | Учасники заданої секції з керівником (вибір з кількох таблиць із сортуванням) | Назва секції=Гітарна студія «Акорд»
SELECT p.last_name AS "Прізвище", p.first_name AS "Ім’я", p.birth_date AS "Дата народження",
       e.enroll_date AS "Дата запису", l.last_name || ' ' || l.first_name AS "Керівник"
FROM section s
     JOIN leader l      ON l.leader_id = s.leader_id
     JOIN enrollment e  ON e.section_id = s.section_id
     JOIN participant p ON p.participant_id = e.participant_id
WHERE s.name = ? AND e.status = 'Активний'
ORDER BY p.last_name, p.first_name

-- @query 2 | Учасники, прізвище яких починається на задану літеру (предикат LIKE) | Перша літера прізвища=Л
SELECT p.last_name AS "Прізвище", p.first_name AS "Ім’я", s.name AS "Секція"
FROM participant p
     JOIN enrollment e ON e.participant_id = p.participant_id AND e.status = 'Активний'
     JOIN section s    ON s.section_id = e.section_id
WHERE p.last_name LIKE ? || '%'
ORDER BY p.last_name, p.first_name, s.name

-- @query 3 | Заняття секцій заданого напряму, проведені у заданий період (предикат BETWEEN) | Напрям=Хореографічна; Дата з=2026-10-19; Дата по=2026-10-25
SELECT ls.lesson_date AS "Дата", ls.start_time AS "Початок", s.name AS "Секція",
       r.room_number AS "Приміщення", ls.topic AS "Тема заняття"
FROM lesson ls
     JOIN section s ON s.section_id = ls.section_id
     JOIN room r    ON r.room_id = ls.room_id
WHERE s.direction = ? AND ls.lesson_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)
ORDER BY ls.lesson_date, ls.start_time

-- @query 4 | Скільки занять проведено за заданий тиждень і скільки було відвідувань (агрегатні функції без групування) | Дата з=2026-11-02; Дата по=2026-11-08
SELECT COUNT(DISTINCT ls.lesson_id) AS "Проведено занять",
       SUM(CASE WHEN a.present THEN 1 ELSE 0 END) AS "Відвідувань",
       SUM(CASE WHEN a.present THEN 0 ELSE 1 END) AS "Пропусків"
FROM lesson ls
     LEFT JOIN attendance a ON a.lesson_id = ls.lesson_id
WHERE ls.lesson_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)

-- @query 5 | Кількість активних учасників у кожній секції (агрегатна функція з групуванням)
SELECT s.name AS "Секція", COUNT(e.enrollment_id) AS "Учасників", s.max_participants AS "Місць",
       CAST(ROUND(100.0 * COUNT(e.enrollment_id) / s.max_participants, 1) AS DECIMAL(5,1)) AS "Заповненість, %"
FROM section s
     LEFT JOIN enrollment e ON e.section_id = s.section_id AND e.status = 'Активний'
GROUP BY s.section_id, s.name, s.max_participants
ORDER BY COUNT(e.enrollment_id) DESC, s.name

-- @query 6 | Керівник, який провів найбільшу кількість занять (предикат ALL)
SELECT l.last_name || ' ' || l.first_name || ' ' || l.middle_name AS "Керівник",
       COUNT(*) AS "Проведено занять"
FROM leader l
     JOIN lesson ls ON ls.leader_id = l.leader_id
GROUP BY l.leader_id, l.last_name, l.first_name, l.middle_name
HAVING COUNT(*) >= ALL (SELECT COUNT(*) FROM lesson GROUP BY leader_id)

-- @query 7 | Для кожного напряму – секція з найбільшою кількістю учасників (корельований підзапит)
SELECT s.direction AS "Напрям", s.name AS "Секція",
       (SELECT COUNT(*) FROM enrollment e
         WHERE e.section_id = s.section_id AND e.status = 'Активний') AS "Учасників"
FROM section s
WHERE (SELECT COUNT(*) FROM enrollment e
        WHERE e.section_id = s.section_id AND e.status = 'Активний')
      >= ALL (SELECT COUNT(e2.enrollment_id)
                FROM section s2
                     LEFT JOIN enrollment e2 ON e2.section_id = s2.section_id AND e2.status = 'Активний'
               WHERE s2.direction = s.direction
               GROUP BY s2.section_id)
ORDER BY s.direction

-- @query 8а | Учасники, які не оплатили заняття за заданий місяць (заперечення, LEFT JOIN) | Місяць (РРРР-ММ)=2026-11
SELECT p.last_name AS "Прізвище", p.first_name AS "Ім’я", s.name AS "Секція", p.parent_phone AS "Телефон батьків"
FROM enrollment e
     JOIN participant p ON p.participant_id = e.participant_id
     JOIN section s     ON s.section_id = e.section_id
     LEFT JOIN payment pm ON pm.enrollment_id = e.enrollment_id AND pm.period = ?
WHERE e.status = 'Активний' AND pm.payment_id IS NULL
ORDER BY p.last_name, p.first_name

-- @query 8б | Учасники, які не оплатили заняття за заданий місяць (заперечення, NOT IN) | Місяць (РРРР-ММ)=2026-11
SELECT p.last_name AS "Прізвище", p.first_name AS "Ім’я", s.name AS "Секція", p.parent_phone AS "Телефон батьків"
FROM enrollment e
     JOIN participant p ON p.participant_id = e.participant_id
     JOIN section s     ON s.section_id = e.section_id
WHERE e.status = 'Активний'
  AND e.enrollment_id NOT IN (SELECT pm.enrollment_id FROM payment pm WHERE pm.period = ?)
ORDER BY p.last_name, p.first_name

-- @query 8в | Учасники, які не оплатили заняття за заданий місяць (заперечення, NOT EXISTS) | Місяць (РРРР-ММ)=2026-11
SELECT p.last_name AS "Прізвище", p.first_name AS "Ім’я", s.name AS "Секція", p.parent_phone AS "Телефон батьків"
FROM enrollment e
     JOIN participant p ON p.participant_id = e.participant_id
     JOIN section s     ON s.section_id = e.section_id
WHERE e.status = 'Активний'
  AND NOT EXISTS (SELECT 1 FROM payment pm
                   WHERE pm.enrollment_id = e.enrollment_id AND pm.period = ?)
ORDER BY p.last_name, p.first_name

-- @query 9 | Керівники з коментарем (операція UNION) | Дата з=2026-11-02; Дата по=2026-11-08
SELECT l.last_name || ' ' || l.first_name AS "Керівник", 'Провів найбільшу кількість занять' AS "Коментар"
FROM leader l JOIN lesson ls ON ls.leader_id = l.leader_id
GROUP BY l.leader_id, l.last_name, l.first_name
HAVING COUNT(*) >= ALL (SELECT COUNT(*) FROM lesson GROUP BY leader_id)
UNION
SELECT l.last_name || ' ' || l.first_name, 'Веде більше однієї секції'
FROM leader l JOIN section s ON s.leader_id = l.leader_id
GROUP BY l.leader_id, l.last_name, l.first_name
HAVING COUNT(*) > 1
UNION
SELECT l.last_name || ' ' || l.first_name, 'Не проводив занять у заданий період'
FROM leader l
WHERE NOT EXISTS (SELECT 1 FROM lesson ls
                   WHERE ls.leader_id = l.leader_id
                     AND ls.lesson_date BETWEEN CAST(? AS DATE) AND CAST(? AS DATE))
ORDER BY 1, 2
