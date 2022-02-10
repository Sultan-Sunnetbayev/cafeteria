CREATE TABLE products(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "name" CHARACTER VARYING(150)NOT NULL,
    "code" CHARACTER VARYING(50) NOT NULL,
    "amount" INT NOT NULL,
    "image_path" CHARACTER VARYING(150),
    "taken_price" DOUBLE PRECISION NOT NULL,
    "sell_price" DOUBLE PRECISION NOT NULL,
    "created" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admins (
    "id" SERIAL PRIMARY KEY NOT NULL,
    "username" CHARACTER VARYING(50) NOT NULL,
    "password" CHARACTER VARYING(80) NOT NULL,
    "email" CHARACTER VARYING(80) NOT NULL,
    "role" CHARACTER VARYING(10) NOT NULL,
    "code" CHARACTER VARYING(50),
    "image_path" CHARACTER VARYING(150),
--     "token" CHARACTER  VARYING (255),
    "created" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sellers (
    "id" SERIAL PRIMARY KEY NOT NULL,
    "username" CHARACTER VARYING(50) NOT NULL,
    "password" CHARACTER VARYING(80) NOT NULL,
    "email" CHARACTER VARYING(80) NOT NULL,
    "role" CHARACTER VARYING(10) NOT NULL,
    "code" CHARACTER VARYING(50),
    "image_path" CHARACTER VARYING(150),
--     "token" CHARACTER  VARYING (255),
    "created" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employees (
    "id" SERIAL PRIMARY KEY NOT NULL,
    "name" CHARACTER VARYING(50) NOT NULL,
    "surname" CHARACTER VARYING(80) NOT NULL,
    "password" CHARACTER VARYING(80),
    "grade" CHARACTER VARYING(10) NOT NULL,
    "image_path" CHARACTER VARYING(150),
    "code" CHARACTER VARYING(50),
    "role" CHARACTER VARYING(10) NOT NULL,
    "created" TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales_products(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "product_id" INT,
    "employee_id" INT NOT NULL,
    "product_name" CHARACTER VARYING(10) NOT NULL,
    "price" DOUBLE PRECISION NOT NULL,
    "amount" INT NOT NULL,
    "image_path" CHARACTER VARYING(150) NOT NULL,
    "created" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT sales_products_product_id_fk
        FOREIGN KEY ("product_id")
            REFERENCES products("id")
                ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT sales_products_user_id_fk
        FOREIGN KEY ("employee_id")
            REFERENCES employees("id")
                ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE return_products(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "product_id" INT,
    "employee_id" INT NOT NULL,
    "product_name" CHARACTER VARYING(10) NOT NULL,
    "price" DOUBLE PRECISION NOT NULL,
    "amount" INT NOT NULL,
    "created" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT return_products_product_id_fk
        FOREIGN KEY ("product_id")
            REFERENCES products("id")
                ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT return_products_user_id_fk
        FOREIGN KEY ("employee_id")
            REFERENCES employees("id")
                ON UPDATE CASCADE ON DELETE CASCADE

);
CREATE TABLE spoiled_products(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "product_id" INT,
    "product_name" CHARACTER VARYING(10) NOT NULL,
    "price" DOUBLE PRECISION NOT NULL,
    "amount" INT NOT NULL,
    "created" TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT return_products_product_id_fk
        FOREIGN KEY ("product_id")
            REFERENCES products("id")
                ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE bucket(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "employee_id" INT,
    "product_id" INT NOT NULL ,
    "amount" INT,
    "sum" DOUBLE PRECISION ,
    CONSTRAINT bucket_product_id_fk
        FOREIGN KEY ("product_id")
            REFERENCES products("id")
                ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT bucket_employee_id_fk
        FOREIGN KEY ("employee_id")
            REFERENCES employees("id")
                ON UPDATE CASCADE ON DELETE CASCADE
);