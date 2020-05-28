create table Complaint
(
    complaintId   int auto_increment
        primary key,
    username      int         not null,
    houseId       int         not null,
    dealingStatus int         not null,
    content       text        not null,
    pic           varchar(50) not null,
    reply         text        null,
    evaluation    text        null,
    score         int         null,
    adminID       int         null,
    constraint UIDreference
        foreign key (username) references Account (username),
    constraint report_Account_UID_fk
        foreign key (adminID) references Account (username),
    constraint report_house_HID_fk
        foreign key (houseId) references House (houseId)
);

