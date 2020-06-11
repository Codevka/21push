create procedure adminImportNewHouse(OUT status int, IN provinc nvarchar(20), IN cit nvarchar(20),
                                                           IN are nvarchar(30), IN addres nvarchar(50), IN rentTyp int,
                                                           IN houseTyp int, IN pi text, IN intr text,
                                                           IN ownerTe nvarchar(11), IN pric int)
begin
     insert into House(province,city,area,address,rentType,houseType,pic,intro,ownerTel,price,houseStatus)  values (provinc,cit,are,addres,rentTyp,houseTyp,pi,intr,ownerTe,pric,1);
 end;


--status自动设置为出租中状态