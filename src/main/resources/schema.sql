CREATE TABLE IF NOT EXISTS report_event (
    event_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (event_id)
);

CREATE TABLE IF NOT EXISTS report_bootcamp (
    id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    launch_date DATE NOT NULL,
    duration_days INT NOT NULL,
    capability_count INT NOT NULL DEFAULT 0,
    technology_count INT NOT NULL DEFAULT 0,
    capabilities_json TEXT NOT NULL,
    enrollment_count BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS report_person (
    id BIGINT NOT NULL AUTO_INCREMENT,
    bootcamp_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_report_person_bootcamp_email (bootcamp_id, email),
    INDEX idx_report_person_bootcamp (bootcamp_id)
);
