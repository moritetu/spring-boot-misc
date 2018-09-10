insert into users (username, password, enabled) values ('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
insert into users (username, password, enabled) values ('guest', '{noop}guest', true);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('guest', 'ROLE_USER');