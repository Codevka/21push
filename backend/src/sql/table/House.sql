-- auto-generated definition
create table House
(
    houseId     int auto_increment
        primary key,
    province    varchar(20) not null,
    city        varchar(20) not null,
    area        varchar(30) not null,
    address     varchar(50) not null,
    rentType    int         not null,
    houseType   int         not null,
    pic         text        null,
    intro       text        not null,
    ownerTel    varchar(11) not null,
    price       int         not null,
    houseStatus int         not null
);

