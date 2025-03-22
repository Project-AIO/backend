insert into tb_user (username, password, license_key, activated) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'bKOOtphsiGqM3/qEOfouLg==', 1);
insert into tb_user (username, password, license_key, activated) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', '', 1);

insert into tb_authority (authority_name) values ('ROLE_USER');
insert into tb_authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values ('1', 'ROLE_USER');
insert into user_authority (user_id, authority_name) values ('1', 'ROLE_ADMIN');
insert into user_authority (user_id, authority_name) values ('2', 'ROLE_USER');

insert into tb_account (account_id, admin_id, role, name) values ('1qaz2wsx!@', 'admin', 'user', '홍길동');
insert into tb_account (account_id, admin_id, role, name) values ('123qwe!@#', 'admin', 'user', '김동현');