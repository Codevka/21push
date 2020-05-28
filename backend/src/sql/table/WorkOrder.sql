create table WorkOrder
(
    repairWorkId int auto_increment
        primary key,
    repairId     int  not null,
    username     int  not null,
    status       int  not null,
    callback     text null,
    constraint WorkOrder_Account_UID_fk
        foreign key (username) references Account (username),
    constraint WorkOrder_Fix_FID_fk
        foreign key (repairId) references Repair (repairId)
);

