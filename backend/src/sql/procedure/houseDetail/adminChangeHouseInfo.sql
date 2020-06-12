create procedure adminChangeHouseInfo(OUT status int, IN HID int, IN provinc varchar(20),
                                                            IN cit nvarchar(20), IN are nvarchar(30),
                                                            IN addres nvarchar(50), IN rentTyp int, IN houseTyp int,
                                                            IN pi text, IN intr text, IN ownerTe nvarchar(11),
                                                            IN pric int, IN houseStatu int)
begin
     select -1 into  status;
     update House h
         set h.province = provinc,
             h.city =cit,
             h.area=are,
             h.address=addres,
             h.rentType=rentTyp,
             h.houseType=houseTyp,
             h.pic=pi,
             h.intro=intr,
             h.ownerTel=ownerTe,
             price=pric,
             houseStatus=houseStatu where h.houseId=HID;
     select 1 into status;
 end;


 --实现逻辑是默认了管理员可以修改除了房子的主键之外的所有信息。
--返回 1 说明处理正常
--返回 -1 说明出问题了