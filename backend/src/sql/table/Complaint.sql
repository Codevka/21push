-- auto-generated definition
create table Complaint
(
    complaintId   int auto_increment
        primary key,
    username      int  not null,
    houseId       int  not null,
    dealingStatus int  not null,
    content       text not null,
    pic           text not null,
    reply         text null,
    evaluation    text null,
    score         int  null default 0,
    adminID       int  null default 0,
    constraint Complaint_Account_username_fk
        foreign key (username) references Account (username),
    constraint report_house_HID_fk
        foreign key (houseId) references House (houseId)
);

