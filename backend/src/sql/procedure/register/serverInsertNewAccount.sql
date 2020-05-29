create
    definer = root@localhost procedure serverInsertNewAccount(OUT status int, IN Passwor varchar(30), IN userTyp int,
                                                              IN Nam varchar(30), IN Te char(11), IN Are varchar(30),
                                                              IN IdNumbe char(18), IN Provinc varchar(20),
                                                              IN Cit varchar(20), IN emai varchar(64))
begin
    if exists(select tel from Account i where i.tel = Te)
    then
        BEGIN
            SELECT -1 into status;
        end;
    else
        insert into Account
            (password, userType, name, tel, area, Idnumber, province, city, email)
        values (passwor, userTyp, Nam, Te, Are, IdNumbe, Provinc, Cit, emai);
        select LAST_INSERT_ID();
        select 1 into status;
    end if;
end;


-- -1 表示帐号用的手机号重复了
-- 1 表示正常插入
