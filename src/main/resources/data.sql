INSERT INTO users (username, password, enabled)
    values ('user', 'pass', true);

INSERT INTO users (username, password, enabled)
    values ('admin', 'pass', true);

INSERT into authorities (username, authority)
    values ('user', 'ROLE_USER');

INSERT into authorities (username, authority)
    values ('admin', 'ROLE_ADMIN');