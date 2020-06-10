create  procedure adminChangeUserInfo (OUT status int, in ID int, IN Te char(11),
                                   IN Emai varchar(64), IN Nam varchar(30), IN Provinc varchar(20),
                                   IN Cit varchar(20), IN Are varchar(30))
begin
    select 1 into status;
    update Account a
    SET
        a.tel=Te,
        a.email=emai,
        a.name=nam,
        a.province=Provinc,
        a.city=Cit,
        a.area=Are where ID=a.username;
end;
-- 管理员不能修改用户的密码和主键，别的都可以修改