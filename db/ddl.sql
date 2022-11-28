--drop table

    drop table board cascade constraints;
    drop table board_file cascade constraints;
    drop table file_info cascade constraints;
    drop table message cascade constraints;
    drop table message_file cascade constraints;
    drop table organization cascade constraints;
    drop table pms_user cascade constraints;
    drop table user_rank cascade constraints;
    drop sequence board_seq;
    drop sequence boardfile_seq;
    drop sequence file_info_seq;
    drop sequence message_file_seq;
    drop sequence message_seq;
    drop sequence organization_seq;
    drop sequence rank_seq;
    drop sequence user_seq;
    
    
--create sequence
   
 create sequence board_seq start with 1 increment by  1;
 create sequence boardfile_seq start with 1 increment by  1;
 create sequence file_info_seq start with 1 increment by  1;
 create sequence message_file_seq start with 1 increment by  1;
 create sequence message_seq start with 1 increment by  1;
 create sequence organization_seq start with 1 increment by  1;
 create sequence rank_seq start with 1 increment by  1;
 create sequence user_seq start with 1 increment by  1;
 
 
--create table
    
    create table board (
       board_id number(19,0) not null,
        board_scope varchar2(255 char) not null,
        category varchar2(255 char) not null,
        content LONG,
        reg_date timestamp not null,
        title varchar2(200 char) not null,
        view_count number(19,0) not null,
        id number(19,0),
        primary key (board_id)
    );
 
    
    create table board_file (
       board_file_id number(19,0) not null,
        board_id number(19,0) not null,
        del_yn varchar2(255 char) not null,
        file_info_id number(19,0),
        primary key (board_file_id)
    );
 
    
    create table file_info (
       file_info_id number(19,0) not null,
        content_type varchar2(255 char) not null,
        extension varchar2(255 char) not null,
        file_volume number(19,0) not null,
        origin_file_name varchar2(255 char) not null,
        reg_date timestamp,
        saved_file_name varchar2(255 char) not null,
        upload_dir varchar2(255 char) not null,
        primary key (file_info_id)
    );
 
    
    create table message (
       message_id number(19,0) not null,
        comments varchar2(1500 BYTE),
        message_state varchar2(255 char),
        send_date timestamp,
        receiver_id number(19,0),
        sender_id number(19,0),
        primary key (message_id)
    );
 
    
    create table message_file (
       message_file_id number(19,0) not null,
        extension varchar2(255 char),
        file_content_type varchar2(255 char),
        file_volume number(19,0),
        reg_date timestamp,
        store_file_name varchar2(255 char),
        upload_file_name varchar2(255 char),
        message_id number(19,0) not null,
        primary key (message_file_id)
    );
 
    
    create table organization (
       organization_id number(19,0) not null,
        organization_code varchar2(50 char) not null,
        organization_delete number(1,0) not null,
        organization_name varchar2(50 char) not null,
        primary key (organization_id)
    );
 
    
    create table pms_user (
       id number(19,0) not null,
        auth varchar2(50 char) not null,
        user_deleteyn number(1,0) not null,
        user_id varchar2(50 char) not null,
        user_name varchar2(50 char) not null,
        user_organization_name varchar2(255 char),
        user_phone_number varchar2(255 char) not null,
        user_pw varchar2(200 char) not null,
        rank_id number(19,0),
        primary key (id)
    );
 
    
    create table user_rank (
       rank_id number(19,0) not null,
        rank_name varchar2(255 char),
        rank_weight number(10,0),
        organization_id number(19,0),
        primary key (rank_id)
    );
 
    
    alter table board 
       add constraint FKnvmxq8e7exl8wqwj17l7nyitq 
       foreign key (id) 
       references pms_user;
 
    
    alter table board_file 
       add constraint FKnb9tvyr6p65euxg8x5q360v33 
       foreign key (file_info_id) 
       references file_info;
 
    
    alter table message 
       add constraint FK5ych9og1wvtowcefsc16hpea9 
       foreign key (receiver_id) 
       references pms_user;
 
    
    alter table message 
       add constraint FKinpfmlho9nsc5sgpas7kjdlqa 
       foreign key (sender_id) 
       references pms_user;
 
    
    alter table message_file 
       add constraint FKlxqm1hth3c6m3qf3pess87oq1 
       foreign key (message_id) 
       references message;
 
    
    alter table pms_user 
       add constraint FKdkrimj158cjmkcth0tygymyxf 
       foreign key (rank_id) 
       references user_rank;
 
    
    alter table user_rank 
       add constraint FK7duvon17rch36782o9c95pun6 
       foreign key (organization_id) 
       references organization;
 
