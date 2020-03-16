DROP TABLE IF EXISTS IMAGERY_TYPE_REF;
CREATE TABLE IMAGERY_TYPE_REF
        (
        imagery_type_name VARCHAR2(50) NOT NULL,
        PRIMARY KEY (imagery_type_name)
        );

DROP TABLE IF EXISTS MISSION;
CREATE TABLE MISSION
        (
        id NUMBER(10) NOT NULL,
        mission_name VARCHAR2(50) NOT NULL,
        mission_imagery_type VARCHAR2(50),
        mission_start_date DATE,
        mission_finish_date DATE,
        PRIMARY KEY(id, mission_name),
        FOREIGN KEY(mission_imagery_type) REFERENCES IMAGERY_TYPE_REF(imagery_type_name)
        );
DROP TABLE IF EXISTS PRODUCT;
CREATE TABLE PRODUCT
        (
        id NUMBER(10) NOT NULL,
        product_mission_name VARCHAR2(50),
        product_acquisition_date DATETIME,
        product_footprint VARCHAR2(50),
        product_price NUMBER(10),
        product_url VARCHAR2(50),
        url_access_permission VARCHAR2(50),
        order_count NUMBER(10) DEFAULT 0,
        PRIMARY KEY(id),
        FOREIGN KEY(product_mission_name) REFERENCES MISSION(mission_name)
        );
DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS
        (
        id NUMBER(10) IDENTITY NOT NULL,
        user_name VARCHAR2(50) NOT NULL,
        product_id NUMBER(10) NOT NULL,
        product_mission_name VARCHAR2(50) NOT NULL,
        time TIMESTAMP NOT NULL,
        PRIMARY KEY(id),
        FOREIGN KEY(product_id) REFERENCES PRODUCT(id),
        FOREIGN KEY(product_mission_name) REFERENCES PRODUCT(product_mission_name)
        );