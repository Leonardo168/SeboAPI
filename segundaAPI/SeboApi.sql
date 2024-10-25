select * from tb_user;
select * from tb_role;
select * from tb_users_roles;

insert into tb_role values ('f3bd1ddd-2b45-4dfd-be30-95fc4d21f97e', 'ROLE_USER');
insert into tb_role values ('0c5d4f9a-51cb-48ba-ac18-745b87b5cb10', 'ROLE_ADMIN');

insert into tb_user values ('eae7e721-05ee-4c59-95a5-e4a845c2ad8e', true, 'Administrador Default',
							'$2a$10$dEI8uF9qb5EmhZDeoas2jeOP03dmjKFRy/w4UbLdNlYWvoU450cHa', 'defaultAdmin');
insert into tb_users_roles values ('eae7e721-05ee-4c59-95a5-e4a845c2ad8e', '0c5d4f9a-51cb-48ba-ac18-745b87b5cb10');
