insert into tb_user (username, password, license_key, activated) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'bKOOtphsiGqM3/qEOfouLg==', 1);
/**
  $2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi
  admin복호화
  **/
insert into tb_admin (admin_id, pw,license_key) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi','bKOOtphsiGqM3/qEOfouLg==');
INSERT INTO tb_account (account_id, admin_id, role, name) VALUES ('test_user', 'admin', 'USER', '홍길동');

insert into tb_authority (authority_name) values ('ROLE_USER');
insert into tb_authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values ('1', 'ROLE_ADMIN');