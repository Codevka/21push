create procedure allGetPasswordAndTypeByID (in  ID int )
begin
    select a.password,a.userType  from Account a where ID=a.username;
end;
--根据id返回密码和type，type方便跳转到相应界面
