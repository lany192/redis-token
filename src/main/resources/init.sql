CREATE DATABASE redis_token_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

create table t_user(
  user_name varchar(20) unique not null,
  password varchar(20) not null,
  id int unsigned auto_increment,
  nick_name varchar(20) not null,
  primary key(id)
);

insert into t_user (user_name, password, nick_name) values ('admin', 'password', 'admin1' );
insert into t_user (user_name, password, nick_name) values ('lany', '123456', '张三' );