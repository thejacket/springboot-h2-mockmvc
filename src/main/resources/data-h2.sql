-- TEST data initialization, also used in tests

INSERT INTO IMAGERY_TYPE_REF (imagery_type_name) VALUES
 ('Panchromatic'),
 ('Multispectral'),
 ('Hyperspectral');

INSERT INTO MISSION (id, mission_name, mission_imagery_type, mission_start_date, mission_finish_date)
VALUES (1, 'MISSION1', 'Panchromatic', '2019-01-01', '2019-03-01');
INSERT INTO MISSION (id, mission_name, mission_imagery_type, mission_start_date, mission_finish_date)
VALUES (2, 'MISSION2', 'Panchromatic', '2019-01-01', '2019-03-01');
INSERT INTO MISSION (id, mission_name, mission_imagery_type, mission_start_date, mission_finish_date)
VALUES (3, 'APOLLO11', 'Multispectral', '2019-01-01', '2019-03-01');
INSERT INTO MISSION (id, mission_name, mission_imagery_type, mission_start_date, mission_finish_date)
VALUES (4, 'SERCE1', 'Hyperspectral', '2019-01-01', '2019-03-01');

INSERT INTO PRODUCT (id, product_mission_name, product_acquisition_date, product_footprint, product_price, product_url, order_count)
VALUES (100, 'APOLLO11', '2019-01-01', '52.168351, 21.205079; 50.10012, 21.0010', 10000, 'http://apollo11photo1.com', 1);
INSERT INTO PRODUCT (id, product_mission_name, product_acquisition_date, product_footprint, product_price, product_url, order_count)
VALUES (101, 'MISSION1', '1999-01-03', '52.168351, 21.205079; 50.10012, 21.0010', 560, 'http://mission1photo1.com', 10);
INSERT INTO PRODUCT (id, product_mission_name, product_acquisition_date, product_footprint, product_price, product_url, order_count)
VALUES (103, 'MISSION1', '2000-11-11', '52.118351, 19.204079; 30.10012, 22.05501', 680, 'http://mission1photo2.com', 4);
INSERT INTO ORDERS VALUES (1, 'user1', 100, 'APOLLO11', '2019-01-01');
INSERT INTO ORDERS VALUES (2, 'user1', 100, 'APOLLO11', '2019-01-01');
INSERT INTO ORDERS VALUES (3, 'user1', 101, 'MISSION1', '2019-01-03');
INSERT INTO ORDERS VALUES (4, 'user69', 103, 'MISSION1', '2019-01-03');
INSERT INTO ORDERS VALUES (5, 'user51', 103, 'MISSION1', '2019-01-03');
INSERT INTO ORDERS VALUES (6, 'user1', 103, 'MISSION1', '2019-01-05');
INSERT INTO ORDERS VALUES (7, 'user51', 100, 'APOLLO11', '2019-01-05');
INSERT INTO ORDERS VALUES (8, 'user1', 103, 'MISSION1', '2019-01-07');
INSERT INTO ORDERS VALUES (9, 'user1', 101, 'MISSION1', '2019-01-10');
