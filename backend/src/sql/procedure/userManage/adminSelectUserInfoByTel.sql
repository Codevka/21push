create procedure  adminSelectUserInfoByTel (out status int, IN Te  nvarchar(11))
begin
    if exists(select password from Account a1 where a1.tel = Te)
    then
        select 1 into status;
        select a.username,a.province,a.city,a.area,a.email,a.tel,a.userType from Account a where Te = a.tel;
    else
        select -1 into status;
end if;
end;

-- 1  正常返回
-- -1 查无此人 