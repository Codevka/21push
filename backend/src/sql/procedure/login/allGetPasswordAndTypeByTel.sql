create
    definer = root@localhost procedure allGetPasswordAndTypeByTel(out status int, IN Te varchar(11))
begin
    if exists(select password from Account a1 where a1.tel =Te )
    then
        select 1 into status;
        select a.password, a.userType from Account a where a.tel = a.tel;
    else
        select -1 into status;
end if;
end;

--根据电话返回密码和type，type方便跳转到相应界面
