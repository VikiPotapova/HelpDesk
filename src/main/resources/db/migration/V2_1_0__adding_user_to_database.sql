insert into users (first_name, last_name, role, email, password)
values ('Oliver', 'Smith', 'EMPLOYEE', 'oliver.smith@gmail.com', 'oliver123');

insert into users (first_name, last_name, role, email, password)
values ('William', 'Collins', 'MANAGER', 'william.collins@gmail.com', 'collins1988');

insert into users (first_name, last_name, role, email, password)
values ('James', 'Ford', 'ENGINEER', 'james.ford@gmail.com', '12345678');

insert into ticket (name, description, created_on, desired_resolution_date, assignee_id, owner_id,
                    status, category, urgency, approver_id)
values ('First ticket', 'Computer broke down', '2023-11-24 18:14:10', '2023-11-26', 1, 3, 'NEW',
        'HARDWARE_AND_SOFTWARE',
        'HIGH', 2);
insert into ticket (name, description, created_on, desired_resolution_date, assignee_id, owner_id,
                    status, category, urgency, approver_id)
values ('Second ticket', 'Mouse is not working', '2023-11-26 15:14:10', '2023-11-30', 1, 3, 'NEW',
        'HARDWARE_AND_SOFTWARE',
        'HIGH', 2);
insert into ticket (name, description, created_on, desired_resolution_date, assignee_id, owner_id,
                    status, category, urgency, approver_id)
values ('Third ticket', 'The Internet connection is low', '2023-11-29 19:14:10', '2023-11-30', 2, 3, 'NEW',
        'HARDWARE_AND_SOFTWARE',
        'HIGH', 2);