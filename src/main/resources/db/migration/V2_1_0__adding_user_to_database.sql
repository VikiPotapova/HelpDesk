insert into users (first_name, last_name, role, email, password)
values ('Oliver', 'Smith', 'EMPLOYEE', 'oliver.smith@gmail.com', 'oliver123');

insert into users (first_name, last_name, role, email, password)
values ('William', 'Collins', 'MANAGER', 'william.collins@gmail.com', 'collins1988');

insert into users (first_name, last_name, role, email, password)
values ('James', 'Ford', 'ENGINEER', 'james.ford@gmail.com', '12345678');

insert into ticket (name, description, created_on, desired_resolution_date, assignee_id, owner_id,
                    state, category, urgency, approved_id)
values ('First ticket', 'Computer broke down', '2023-11-24', '2023-11-26', 1, 3, 'NEW', 'HARDWARE_AND_SOFTWARE',
        'HIGH', 2);