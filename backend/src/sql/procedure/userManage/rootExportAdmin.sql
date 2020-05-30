create  procedure rootExportAdmin(OUT status int,IN Passwor varchar(30), IN Nam varchar(30),
                                                           IN Te char(11), IN Are varchar(30), IN IdNumbe char(18),
                                                           IN Provinc varchar(20), IN Cit varchar(20))
begin
    if exists(select tel from Account i where i.tel = Te)
    then
        BEGIN
            SELECT -1 into status;
        end;
    else
        insert into Account
            (password, userType, name, tel, area, Idnumber, province, city)
        values (passwor,1, Nam, Te, Are, IdNumbe, Provinc, Cit);
        select LAST_INSERT_ID();
        select 1 into status;
    end if;
end;

