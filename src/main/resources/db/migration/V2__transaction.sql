create table `transaction` (
    id bigint auto_increment primary key,
    user_id bigint not null,
    origin_currency varchar(3) not null,
    origin_value decimal not null,
    destination_currency varchar(3),
    conversion_rate decimal not null,
    created_at timestamp not null,
    foreign key (user_id) references `user`(id)
);