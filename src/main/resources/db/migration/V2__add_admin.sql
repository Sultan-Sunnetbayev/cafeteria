INSERT INTO users(id,username,password,email,status)
VALUES (1,'admin','admin','admim@admin.com','ACTIVE');

INSERT INTO roles(id,name)
VALUES (1,'ROLE_ADMIN'),
       (2,'ROLE_SELLER');

INSERT INTO roles_users(user_id,role_id) VALUES (1,1);