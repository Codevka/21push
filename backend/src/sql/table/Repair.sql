create table Repair
(
    repairId   int auto_increment
        primary key,
    username   int         not null,
    houseId    int         not null,
    status     int         not null,
    content    text        not null,
    pic        varchar(50) null,
    adminId    int         null,
    evaluation text        null,
    score      int         not null,
    constraint Repair_Account_username_fk
        foreign key (adminId) references Account (username),
    constraint Repair_Account_username_fk_2
        foreign key (username) references Account (username),
    constraint fix_House_HID_fk
        foreign key (houseId) references House (houseId)
);

