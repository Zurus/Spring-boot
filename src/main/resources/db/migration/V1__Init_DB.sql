create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
create table post (
    id bigint not null,
    anons varchar(255),
    file_name varchar(255),
    full_text varchar(255),
    title varchar(255),
    views integer not null,
    user_id bigint,
    primary key (id)
);

create table user_role (
    user_id bigint not null,
    roles varchar(255)
);

create table usr (
    id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table post add constraint post_user_fk foreign key (user_id) references usr (id);
alter table user_role add constraint user_role_fk foreign key (user_id) references usr (id);
