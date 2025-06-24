CREATE TABLE brands
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_brands PRIMARY KEY (id)
);

CREATE TABLE compatible_models
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_compatiblemodels PRIMARY KEY (id)
);

CREATE TABLE expenses
(
    id            BINARY(16)   NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    amount        INT          NOT NULL,
    CONSTRAINT pk_expenses PRIMARY KEY (id)
);

CREATE TABLE product_compatible_models
(
    model_id   BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    CONSTRAINT pk_product_compatible_models PRIMARY KEY (model_id, product_id)
);

CREATE TABLE products
(
    id                BINARY(16)   NOT NULL,
    created_at        datetime     NOT NULL,
    updated_at        datetime     NOT NULL,
    name              VARCHAR(255) NOT NULL,
    brand_id          BINARY(16)   NULL,
    sku               VARCHAR(255) NOT NULL,
    `description`     VARCHAR(255) NULL,
    quantity_in_stock INT          NOT NULL,
    purchase_price    INT          NOT NULL,
    selling_price     INT          NOT NULL,
    shelf_code_id     BINARY(16)   NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE sales
(
    id               BINARY(16)                                        NOT NULL,
    created_at       datetime                                          NOT NULL,
    updated_at       datetime                                          NOT NULL,
    product_id       BINARY(16)                                        NOT NULL,
    quantity_sold    INT                                               NOT NULL,
    total_amount     INT                                               NOT NULL,
    payment_status   ENUM ('PAID', 'UNPAID', 'PARTIAL') DEFAULT 'PAID' NOT NULL,
    sales_summary_id BINARY(16)                                        NULL,
    CONSTRAINT pk_sales PRIMARY KEY (id)
);

CREATE TABLE sales_person
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    user_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    `role`     VARCHAR(255) NULL,
    CONSTRAINT pk_salesperson PRIMARY KEY (id)
);

CREATE TABLE sales_summary
(
    id             BINARY(16)                         NOT NULL,
    created_at     datetime                           NOT NULL,
    updated_at     datetime                           NOT NULL,
    customer_name  VARCHAR(255)                       NULL,
    quantity_sold  INT                                NOT NULL,
    total_amount   INT                                NOT NULL,
    payment_status ENUM ('PAID', 'UNPAID', 'PARTIAL') NOT NULL,
    CONSTRAINT pk_salessummary PRIMARY KEY (id)
);

CREATE TABLE shelf_code
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_shelfcode PRIMARY KEY (id)
);

CREATE TABLE suppliers
(
    id             BINARY(16)   NOT NULL,
    created_at     datetime     NOT NULL,
    updated_at     datetime     NOT NULL,
    company        VARCHAR(255) NULL,
    contact_person VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    phone_number   VARCHAR(255) NULL,
    CONSTRAINT pk_suppliers PRIMARY KEY (id)
);

ALTER TABLE brands
    ADD CONSTRAINT uc_brands_name UNIQUE (name);

ALTER TABLE compatible_models
    ADD CONSTRAINT uc_compatiblemodels_name UNIQUE (name);

ALTER TABLE products
    ADD CONSTRAINT uc_products_sku UNIQUE (sku);

ALTER TABLE sales_person
    ADD CONSTRAINT uc_salesperson_email UNIQUE (email);

ALTER TABLE shelf_code
    ADD CONSTRAINT uc_shelfcode_name UNIQUE (name);

CREATE INDEX nameAndIdIndexing ON products (name, id);

CREATE INDEX name_Id ON shelf_code (name, id);

CREATE INDEX name_Id ON shelf_code (name, id);

CREATE INDEX name_Id ON shelf_code (name, id);

CREATE INDEX name_Id_createdAt ON sales (id, created_at);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_BRANDID FOREIGN KEY (brand_id) REFERENCES brands (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_SHELFCODEID FOREIGN KEY (shelf_code_id) REFERENCES shelf_code (id);

ALTER TABLE sales
    ADD CONSTRAINT FK_SALES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE sales
    ADD CONSTRAINT FK_SALES_ON_SALES_SUMMARY FOREIGN KEY (sales_summary_id) REFERENCES sales_summary (id);

ALTER TABLE product_compatible_models
    ADD CONSTRAINT fk_procommod_on_compatible_models FOREIGN KEY (model_id) REFERENCES compatible_models (id);

ALTER TABLE product_compatible_models
    ADD CONSTRAINT fk_procommod_on_products FOREIGN KEY (product_id) REFERENCES products (id);