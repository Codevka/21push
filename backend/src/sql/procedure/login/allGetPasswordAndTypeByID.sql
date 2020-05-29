create
    definer = root@localhost procedure allGetPasswordAndTypeByID(out status int, IN ID int)
begin
    if exists(select password from Account a1 where a1.username = ID)
    then
        select 1 into status;
        select a.password, a.userType from Account a where ID = a.username;
    else
        select -1 into status;
end if;
end;

--根据id返回密码和type，type方便跳转到相应界面
-- -1 帐号不存在
--  1 帐号存在，已返回密码
