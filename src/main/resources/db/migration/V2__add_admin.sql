INSERT INTO users(id,username,password,email,status)
VALUES (1,'admin','$2a$10$TjK0cPTQKtrZsWhT0sIMSu7GLQLeLeRZ5BaJMG4.argsd/S.7kIDW','admin@admin.com','ACTIVE');

ALTER SEQUENCE users_id_seq RESTART WITH 2;

INSERT INTO roles(id,name)
VALUES (1,'ROLE_ADMIN'),
       (2,'ROLE_SELLER');

INSERT INTO roles_users(user_id,role_id) VALUES (1,1);