CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    first_name varchar(20),
    last_name  varchar(50),
    role       varchar               not null,
    email      varchar(100)          not null,
    password   varchar(20)           not null
);

CREATE TABLE ticket
(
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    name                    varchar(100)          not null,
    description             varchar(500)          not null,
    created_on              timestamp             not null,
    desired_resolution_date date                  not null,
    assignee_id             bigint,
    owner_id                bigint                not null,
    status                  varchar default 'NEW',
    category                varchar               not null,
    urgency                 varchar               not null,
    approver_id             bigint,
    FOREIGN KEY (assignee_id) REFERENCES users (id),
    FOREIGN KEY (owner_id) REFERENCES users (id),
    FOREIGN KEY (approver_id) REFERENCES users (id)
);

CREATE TABLE attachment
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    url       varchar     not null,
    ticket_id bigint      not null,
    name      varchar(20) not null,
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE TABLE comment
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    user_id   bigint       not null,
    text      varchar(500) not null,
    date      timestamp    not null,
    ticket_id bigint       not null,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE TABLE feedback
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    user_id   bigint    not null,
    rate      int,
    date      timestamp not null,
    text      varchar   not null,
    ticket_id bigint    not null,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);

CREATE TABLE history
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    ticket_id   bigint    not null,
    date        timestamp not null,
    action      varchar,
    user_id     bigint    not null,
    description varchar(500),
    FOREIGN KEY (ticket_id) REFERENCES ticket (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);




