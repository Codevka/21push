create procedure  adminSelectUserInfoByID (out status int, IN ID int)
begin
    if exists(select password from Account a1 where a1.username = ID)
    then
        select 1 into status;
        select a.username,a.province,a.city,a.area,a.email,a.tel,a.userType from Account a where ID = a.username;
    else
        select -1 into status;
end if;
end;

-- 1 正常返回
-- -1 查无此人
