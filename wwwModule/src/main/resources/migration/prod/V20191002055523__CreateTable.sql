

drop table if exists hibernate_sequence;
    

drop table if exists task;
    

drop table if exists user;
    

create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;
    

insert into hibernate_sequence values ( 1 );
    

insert into hibernate_sequence values ( 1 );
    

create table task (
                      id bigint not null,
                      comment varchar(255),
                      created_date datetime not null,
                      end_date_time datetime not null,
                      is_all_day bit not null,
                      last_modified_date datetime not null,
                      place varchar(255),
                      start_date_time datetime not null,
                      title varchar(255) not null,
                      color varchar(255),
                      created_by_id bigint not null,
                      last_modified_by_id bigint not null,
                      primary key (id)
) engine=MyISAM;
    

create table user (
                      id bigint not null,
                      password varchar(255),
                      user_id varchar(255),
                      birthday varchar(255),
                      name varchar(255),
                      phone_number varchar(255),
                      status varchar(255),
                      primary key (id)
) engine=MyISAM;
    

alter table task
    add constraint FKsaw2pfw389opao1w32cljexhk
        foreign key (created_by_id)
            references user (id);
    

alter table task
    add constraint FKp1xuo730gc5tm43dbipjr0pvm
        foreign key (last_modified_by_id)
            references user (id);
