CREATE TABLE section (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    comment TEXT
);

CREATE TABLE sub_section (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subtitle VARCHAR(255) NOT NULL,
    content TEXT,
    section_id BIGINT,
    CONSTRAINT fk_section FOREIGN KEY (section_id) REFERENCES section(id) ON DELETE CASCADE
)
