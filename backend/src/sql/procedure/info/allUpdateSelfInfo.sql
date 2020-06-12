create procedure allUpdateSelfInfo(OUT status int, in ID int, IN Passwor nvarchar(30), IN Te char(11),
                                   IN Emai nvarchar(64), IN Nam nvarchar(30), IN Provinc nvarchar(20),
                                   IN Cit nvarchar(20), IN Are nvarchar(30))
begin
    select  1 into status;
    update Account a
    SET a.password=Passwor,
        a.tel=Te,
        a.email=emai,
        a.name=nam,
        a.province=Provinc,
        a.city=Cit,
        a.area=Are where ID=a.username;
end;