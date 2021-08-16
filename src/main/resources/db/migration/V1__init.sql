CREATE TABLE IF NOT EXISTS category (
      id SERIAL PRIMARY KEY,
      name VARCHAR (255) NOT NULL,
      req_name VARCHAR (255) NOT NULL,
      deleted BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS banner (
      id SERIAL PRIMARY KEY,
      name VARCHAR (255) NOT NULL,
      price DECIMAL(8, 2) NOT NULL,
      category_id INTEGER REFERENCES category(id),
      content TEXT NOT NULL,
      deleted BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS request (
      id SERIAL PRIMARY KEY,
      banner_id INTEGER REFERENCES banner(id),
      user_agent TEXT NOT NULL,
      ip_address VARCHAR (255) NOT NULL,
      date TIMESTAMP NOT NULL
);