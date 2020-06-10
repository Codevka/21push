create procedure allUpdateSelfInfo(OUT status int, in ID int, IN Passwor varchar(30), IN Te char(11),
                                   IN Emai varchar(64), IN Nam varchar(30), IN Provinc varchar(20),
                                   IN Cit varchar(20), IN Are varchar(30))
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