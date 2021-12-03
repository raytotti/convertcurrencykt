create table `user`(
    id bigint auto_increment primary key,
    cpf varchar(14) not null,
    name varchar(256) not null
);
alter table `user` add constraint user_uk UNIQUE(cpf);
