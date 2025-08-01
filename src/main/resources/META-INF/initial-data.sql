INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (1, '/security/userList.xhtml', 'ALL');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (2, '/security/userList.xhtml', 'READ');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (3, '/security/userList.xhtml', 'WRITE');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (4, '/security/userList.xhtml', 'DELETE');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (5, '/security/userEdit.xhtml', 'READ');

INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (6, '/cashier/orders.xhtml', 'READ');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (7, '/cashier/orders.xhtml', 'WRITE');
INSERT INTO PERMISSION (ID, RESOURCE, ACTION) VALUES (8, '/cashier/ordersEdit.xhtml', 'READ');

INSERT INTO ROLE (ID, NAME) VALUES (1, 'ADMIN');
INSERT INTO ROLE (ID, NAME) VALUES (2, 'USER');

INSERT INTO USER (ID, NAME, PASSWORD) VALUES (1, 'admin', 'uVQoLxtZvlhBuamIlWRLGQ==');
INSERT INTO USER (ID, NAME, PASSWORD) VALUES (2, 'patinio', 'uVQoLxtZvlhBuamIlWRLGQ==');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 1);

INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (1, 1);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (1, 2);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (1, 3);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (1, 4);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (1, 5);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (2, 6);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (2, 7);
INSERT INTO ROLE_PERMISSION (ROLE_ID, PERMISSION_ID) VALUES (2, 8);

COMMIT ;