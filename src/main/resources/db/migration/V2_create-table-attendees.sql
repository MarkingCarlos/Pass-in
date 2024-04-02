CREATE TABLE attendees (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) not NULL,
    email VARCHAR(255) NOT NULL,
    event_id VARCHAR(255) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSISTENT attendees_event_id FOREIGN KEY (evennt_id) REFERENCES events (id) on DELETE RESTRICT on UPDATE CASCADE
)