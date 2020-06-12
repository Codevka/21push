create
    definer = root@localhost procedure serverInsertNewAccount(OUT status int, IN Passwor nvarchar(30), IN userTyp int,
                                                              IN Nam nvarchar(30), IN Te char(11), IN Are nvarchar(30),
                                                              IN IdNumbe char(18), IN Provinc nvarchar(20),
                                                              IN Cit nvarchar(20), IN emai nvarchar(64))
begin
    if exists(select tel from Account i where i.tel = Te) or exists(select email from  Account g where  g.email =emai)
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


-- -1 表示帐号用的手机号或邮箱重复了
-- 1 表示正常插入
