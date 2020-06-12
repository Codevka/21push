create table Account
(
    username int auto_increment,
    password nvarchar(30) not null,
    userType int         not null,
    name     nvarchar(30) not null,
    tel      char(11)    null,
    area     nvarchar(30) null,
    Idnumber char(18)    null,
    province nvarchar(20) not null,
    city     nvarchar(20) not null,
    email    nvarchar(64) null,
    constraint Account_UID_uindex
        unique (username)
);

alter table Account add primary key (username);
alter table Account AUTO_INCREMENT =100000000 -- 把这自增主键起始值设置为9位数 

--暂时的想法是 用户名像qq号一样，然后权限用int值判断，等大家讨论决定//update: 已确定
-- root 管理员信息直接在数据库里面插入//
