
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    is_Admin BOOLEAN NOT NULL
    );


INSERT OR IGNORE INTO users (username, password, is_Admin) VALUES
    ('admin1', 'adminpass1', 1),
    ('employee1', 'employeepass1', 0),
    ('employee2', 'employeepass2', 0);

CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL
    );

CREATE TABLE IF NOT EXISTS products (
    product_id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    available_pieces INTEGER NOT NULL DEFAULT 0
    );

INSERT OR IGNORE INTO products (product_name, price, available_pieces) VALUES
    ('Laptop', 850.00, 10),
    ('Mouse', 15.50, 50),
    ('Keyboard', 25.00, 30);
CREATE TABLE IF NOT EXISTS orders (
    order_id VARCHAR(20) PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    total_price REAL NOT NULL,
    order_date TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY(customer_id) REFERENCES customers(id),
    FOREIGN KEY(product_id) REFERENCES products(product_id)
    );
CREATE TABLE IF NOT EXISTS draft_orders (
    draft_id VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY(product_id) REFERENCES products(product_id)
);



