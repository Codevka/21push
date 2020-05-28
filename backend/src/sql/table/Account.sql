--account list 
create table Account
(
    username int         not null,
    password varchar(30) not null,
    userType int         not null,
    name     varchar(30) not null,
    tel      char(11)    null,
    area     varchar(30) null,
    Idnumber char(18)    null,
    province varchar(20) not null,
    city     varchar(20) not null,
    email    varchar(64) null,
    constraint Account_UID_uindex
        unique (username)
);

alter table Account
    add primary key (username);
--暂时的想法是 用户名像qq号一样，然后权限用int值判断，等大家讨论决定//update: 已确定
-- root 管理员信息直接在数据库里面插入//
