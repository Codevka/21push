create procedure adminExportRepairer(OUT status int, IN Passwor nvarchar(30),
                                     IN Nam nvarchar(30), IN Te char(11), IN Are nvarchar(30),
                                     IN IdNumbe char(18), IN Provinc nvarchar(20),
                                     IN Cit nvarchar(20))
begin
    if exists(select tel from Account i where i.tel = Te)
    then
        BEGIN
            SELECT -1 into status;
        end;
    else
        insert into Account
            (password, userType, name, tel, area, Idnumber, province, city)
        values (passwor,2 , Nam, Te, Are, IdNumbe, Provinc, Cit);
        select LAST_INSERT_ID();
        select 1 into status;
    end if;
end;


-- 按照type 3 插入
-- 返回 -1 表示 该手机号已被使用
-- 返回 1 表示 插入正常，并在结果集中返回了帐号