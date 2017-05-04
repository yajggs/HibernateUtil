create table hiberutil.ADDRESS (
        ADDRESS_ID integer not null auto_increment,
        ADDRESS varchar(100) not null,
        ADDRESS_TYPE varchar(20) not null,
        CITY varchar(50) not null,
        country varchar(255),
        STATE varchar(50) not null,
        primary key (ADDRESS_ID)
    );

    create table hiberutil.DETAIL (
        DETAIL_ID integer not null,
        AGE integer not null,
        BLOOD_GROUP varchar(5) not null,
        CONTACT_NUMBER varchar(15) not null,
        DOB date not null,
        primary key (DETAIL_ID)
    );

    create table hiberutil.GROUPS (
        GROUP_ID integer not null auto_increment,
        GROUP_NAME varchar(255) not null,
        primary key (GROUP_ID)
    );

    create table hiberutil.ROLE (
        ROLE_ID integer not null auto_increment,
        ROLE_NAME varchar(255) not null,
        primary key (ROLE_ID)
    );

    create table hiberutil.STUDENT (
        STUDENT_ID integer not null auto_increment,
        STUDENT_NAME varchar(50) not null,
        primary key (STUDENT_ID)
    );

    create table hiberutil.STUDENT_ADDRESS (
        STUDENT_ID integer not null,
        ADDRESS_ID integer not null,
        primary key (STUDENT_ID, ADDRESS_ID)
    );

    create table hiberutil.STUDENT_GROUP_ROLE (
        groupId integer not null,
        roleId integer not null,
        student tinyblob,
        primary key (groupId, roleId)
    );

    create table hiberutil.STUDENT_RECORDS (
        RECORD_ID integer not null auto_increment,
        CLASS_NAME varchar(50) not null,
        MARKS_SCORED integer not null,
        SUBJECT_NAME varchar(50) not null,
        STUDENT_ID integer,
        primary key (RECORD_ID)
    );

    alter table hiberutil.DETAIL
        add constraint UK_oehtj3oah9i12tr4nanyijdim  unique (AGE);

    alter table hiberutil.DETAIL
        add constraint UK_39ylfvar8u1g21svx927ntgua  unique (BLOOD_GROUP);

    alter table hiberutil.DETAIL
        add constraint UK_barng3bf10mxjocmulhc2mmw8  unique (CONTACT_NUMBER);

    alter table hiberutil.GROUPS
        add constraint UK_c74g8405cgv1hgmq02ab0vrdn  unique (GROUP_NAME);

    alter table hiberutil.ROLE
        add constraint UK_kxdwerf0h1q9og7xn5oi8jo5k  unique (ROLE_NAME);

    alter table hiberutil.STUDENT_ADDRESS
        add constraint FK_op720rmdf2hpvqxkut0knupg
        foreign key (ADDRESS_ID)
        references hiberutil.ADDRESS (ADDRESS_ID);

    alter table hiberutil.STUDENT_ADDRESS
        add constraint FK_rlkaansbohqki0ydctddq68wj
        foreign key (STUDENT_ID)
        references hiberutil.STUDENT (STUDENT_ID);

    alter table hiberutil.STUDENT_GROUP_ROLE
        add constraint FK_pwuws9jkj8gir0ixxuj0yo1pw
        foreign key (student_ID)
        references hiberutil.STUDENT (STUDENT_ID);


    alter table hiberutil.STUDENT_RECORDS
        add constraint FK_kw9ijt53p0i66lvsdh6iwo8a3
        foreign key (STUDENT_ID)
        references hiberutil.STUDENT (STUDENT_ID);