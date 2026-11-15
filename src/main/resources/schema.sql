-- ІС «Будинок дитячої творчості»: схема бази даних (H2, SQL:2016)

SET COLLATION UKRAINIAN;

CREATE TABLE leader (
    leader_id      INT AUTO_INCREMENT PRIMARY KEY,
    last_name      VARCHAR(40) NOT NULL,
    first_name     VARCHAR(30) NOT NULL,
    middle_name    VARCHAR(30),
    phone          VARCHAR(20) NOT NULL UNIQUE,
    specialization VARCHAR(60) NOT NULL,
    hire_date      DATE        NOT NULL
);

CREATE TABLE section (
    section_id       INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(60)  NOT NULL UNIQUE,
    direction        VARCHAR(20)  NOT NULL
        CHECK (direction IN ('Хореографічна', 'Музична', 'Художня', 'Технічна', 'Спортивна')),
    leader_id        INT          NOT NULL REFERENCES leader (leader_id),
    age_min          INT          NOT NULL CHECK (age_min BETWEEN 4 AND 17),
    age_max          INT          NOT NULL CHECK (age_max BETWEEN 4 AND 17),
    max_participants INT          NOT NULL CHECK (max_participants > 0),
    monthly_fee      DECIMAL(8,2) NOT NULL CHECK (monthly_fee >= 0),
    CHECK (age_max >= age_min)
);

CREATE TABLE participant (
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    last_name      VARCHAR(40) NOT NULL,
    first_name     VARCHAR(30) NOT NULL,
    birth_date     DATE        NOT NULL,
    parent_name    VARCHAR(80) NOT NULL,
    parent_phone   VARCHAR(20) NOT NULL,
    benefit        VARCHAR(15) DEFAULT 'Немає' NOT NULL
        CHECK (benefit IN ('Немає', 'Багатодітна', 'Пільгова'))
);

CREATE TABLE enrollment (
    enrollment_id  INT AUTO_INCREMENT PRIMARY KEY,
    section_id     INT         NOT NULL REFERENCES section (section_id),
    participant_id INT         NOT NULL REFERENCES participant (participant_id),
    enroll_date    DATE        NOT NULL,
    status         VARCHAR(10) DEFAULT 'Активний' NOT NULL CHECK (status IN ('Активний', 'Вибув')),
    UNIQUE (section_id, participant_id)
);

CREATE TABLE room (
    room_id     INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    room_type   VARCHAR(30) NOT NULL,
    capacity    INT         NOT NULL CHECK (capacity > 0)
);

CREATE TABLE schedule_entry (
    entry_id    INT AUTO_INCREMENT PRIMARY KEY,
    section_id  INT  NOT NULL REFERENCES section (section_id),
    room_id     INT  NOT NULL REFERENCES room (room_id),
    day_of_week INT  NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    CHECK (end_time > start_time)
);

CREATE TABLE lesson (
    lesson_id   INT AUTO_INCREMENT PRIMARY KEY,
    section_id  INT          NOT NULL REFERENCES section (section_id),
    entry_id    INT          REFERENCES schedule_entry (entry_id) ON DELETE SET NULL,
    room_id     INT          NOT NULL REFERENCES room (room_id),
    leader_id   INT          NOT NULL REFERENCES leader (leader_id),
    lesson_date DATE         NOT NULL,
    start_time  TIME         NOT NULL,
    end_time    TIME         NOT NULL,
    topic       VARCHAR(120) NOT NULL,
    UNIQUE (section_id, lesson_date, start_time)
);

CREATE TABLE attendance (
    lesson_id      INT     NOT NULL REFERENCES lesson (lesson_id) ON DELETE CASCADE,
    participant_id INT     NOT NULL REFERENCES participant (participant_id),
    present        BOOLEAN NOT NULL,
    PRIMARY KEY (lesson_id, participant_id)
);

CREATE TABLE event (
    event_id    INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    event_type  VARCHAR(20)  NOT NULL
        CHECK (event_type IN ('Концерт', 'Виставка', 'Конкурс', 'Фестиваль', 'Змагання')),
    event_date  DATE         NOT NULL,
    place       VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE event_section (
    event_id    INT          NOT NULL REFERENCES event (event_id) ON DELETE CASCADE,
    section_id  INT          NOT NULL REFERENCES section (section_id),
    performance VARCHAR(100) NOT NULL,
    result      VARCHAR(40),
    PRIMARY KEY (event_id, section_id)
);

CREATE TABLE payment (
    payment_id    INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT          NOT NULL REFERENCES enrollment (enrollment_id),
    pay_date      DATE         NOT NULL,
    period        CHAR(7)      NOT NULL,
    amount        DECIMAL(8,2) NOT NULL CHECK (amount > 0),
    method        VARCHAR(10)  NOT NULL CHECK (method IN ('Готівка', 'Картка')),
    UNIQUE (enrollment_id, period)
);
