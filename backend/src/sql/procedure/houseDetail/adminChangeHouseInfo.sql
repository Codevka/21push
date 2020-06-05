 create procedure adminChangeHouseInfo(out status int ,in HID int ,in provinc varchar (20),cit varchar(20),are varchar(30),addres varchar(50),rentTyp int ,houseTyp int ,pi varchar(50),intr text,ownerTe varchar(11),pric int ,houseStatu int)
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