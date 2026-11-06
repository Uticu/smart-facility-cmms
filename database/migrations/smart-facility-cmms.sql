DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS resource_reservations;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS assets;
DROP TABLE IF EXISTS inventory_items;
DROP TABLE IF EXISTS allocatable_resources;



CREATE TABLE roles(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50), 
    last_name VARCHAR(50),
    is_enabled TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_users
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_roles
        FOREIGN KEY (role_id) REFERENCES roles(id)
        ON DELETE CASCADE
);

CREATE TABLE assets(
    id INT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    installation_date TIMESTAMP NULL
);

CREATE TABLE inventory_items(
    id INT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL, 
    description TEXT,
    stock_quantity INT NOT NULL DEFAULT 0,
    min_restock_threshold INT NOT NULL DEFAULT 5,
    unit_price DECIMAL(10, 2) NOT NULL,
    version INT NOT NULL DEFAULT 0
);

CREATE TABLE allocatable_resources(
    id INT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    resource_type VARCHAR(50) NOT NULL
);

CREATE TABLE resource_reservations(
    id INT AUTO_INCREMENT PRIMARY KEY,
    resource_id INT NOT NULL,
    ticket_id INT NOT NULL, --Wait for 'Tickets' table to link
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,

    CONSTRAINT fk_resource_reservations_allocatable_resources
        FOREIGN KEY (resource_id) REFERENCES allocatable_resources(id)
        ON DELETE RESTICT,

    INDEX idx_resource_interval (resource_id, start_time, end_time)
);

CREATE TABLE payments(
    id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT NOT NULL, --Wait for 'Tickets' table to link
    stripe_payment_intent_id VARCHAR(255) UNIQUE,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'RON',
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
);