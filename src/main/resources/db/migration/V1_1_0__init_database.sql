CREATE TABLE users
(
    id         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(20),
    last_name  varchar(50),
    role       varchar,
    email      varchar(100),
    password   varchar(20)
);

CREATE TABLE ticket
(
    id                      bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                    varchar(100),
    description             varchar(500),
    created_on              timestamp(8),
    desired_resolution_date timestamp(8),
    assignee_id             bigint,
    owner_id                bigint,
    state                   varchar,
    category                varchar,
    urgency                 varchar,
    approved_id             bigint,
    FOREIGN KEY (assignee_id) REFERENCES users (id),
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (approved_id) REFERENCES users (id)
);


CREATE TABLE attachment
(
    id        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    url       varchar,
    ticket_id bigint,
    name      varchar(20),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE TABLE comment
(
    id        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id   bigint,
    text      varchar(500),
    date      timestamp(8),
    ticket_id bigint,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE TABLE feedback
(
    id        bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id   bigint,
    rate      int,
    date      timestamp(8),
    text      varchar,
    ticket_id bigint,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);


CREATE TABLE history
(
    id          bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ticket_id   bigint,
    date        timestamp(8),
    action      varchar,
    user_id     bigint,
    description varchar(500),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);




