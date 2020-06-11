-- auto-generated definition
create table House
(
    houseId     int auto_increment
        primary key,
    province    nvarchar(20) not null,
    city        nvarchar(20) not null,
    area        nvarchar(30) not null,
    address     nvarchar(50) not null,
    rentType    int         not null,
    houseType   int         not null,
    pic         text        null,
    intro       text        not null,
    ownerTel    nvarchar(11) not null,
    price       int         not null,
    houseStatus int         not null
);

