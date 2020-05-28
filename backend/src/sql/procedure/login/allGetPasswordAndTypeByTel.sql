create procedure allGetPasswordAndTypeByTel(IN Tel varchar(11))
begin
    select a.password, a.userType from Account a where Tel = a.tel;
end;
--根据电话返回密码和type，type方便跳转到相应界面
