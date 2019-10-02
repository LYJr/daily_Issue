

drop table task if exists;


drop table user if exists;


drop sequence if exists hibernate_sequence;
 create sequence hibernate_sequence start with 1 increment by 1;


create table task (
                      id bigint not null,
                      created_date timestamp,
                      last_modified_date timestamp,
                      color varchar(255),
                      comment varchar(255),
                      end_date_time timestamp not null,
                      is_all_day boolean not null,
                      place varchar(255),
                      start_date_time timestamp not null,
                      title varchar(255) not null,
                      created_by_id bigint,
                      last_modified_by_id bigint,
                      primary key (id)
);
    

create table user (
                      id bigint not null,
                      password varchar(255),
                      user_id varchar(255),
                      birthday varchar(255),
                      name varchar(255),
                      phone_number varchar(255),
                      status varchar(255),
                      primary key (id)
);
    

alter table task
    add constraint FKsaw2pfw389opao1w32cljexhk
        foreign key (created_by_id)
            references user;
    

alter table task
    add constraint FKp1xuo730gc5tm43dbipjr0pvm
        foreign key (last_modified_by_id)
            references user;