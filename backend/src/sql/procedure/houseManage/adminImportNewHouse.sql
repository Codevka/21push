create @localhost procedure adminImportNewHouse(OUT status int, IN provinc varchar(20), IN cit varchar(20),
                                                           IN are varchar(30), IN addres varchar(50), IN rentTyp int,
                                                           IN houseTyp int, IN pi varchar(50), IN intr text,
                                                           IN ownerTe varchar(11), IN pric int)
begin
     insert into House(province,city,area,address,rentType,houseType,pic,intro,ownerTel,price,houseStatus)  values (provinc,cit,are,addres,rentTyp,houseTyp,pi,intr,ownerTe,pric,1);
 end;

--status自动设置为出租中状态