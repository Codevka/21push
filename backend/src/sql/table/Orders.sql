create table Orders
(
    contractId        int auto_increment
        primary key,
    username          int  not null,
    houseId           int  not null,
    rentTime          date not null,
    orderGenerateTime date null,
    contractDuration  int  not null,
    status            int  not null,
    price             int  not null,
    comment           text null,
    constraint Order_house_HID_fk
        foreign key (houseId) references House (houseId),
    constraint Orders_Account_username_fk
        foreign key (username) references Account (username)
);

