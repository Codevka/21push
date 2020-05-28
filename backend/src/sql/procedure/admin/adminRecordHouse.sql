create procedure adminRecordHouse (in p_location varchar(30),p_detailedLocation varchar(50),p_rentType int ,p_houseType int, p_picture varchar(50),p_introduction text, p_ownerPhone varchar(11),p_price int)
begin
    insert into House(location, datiledLocation, rentType, houseType, picture, introduction, ownerPhone, price, status) values(p_location,p_detailedLocation,p_rentType,p_houseType,p_picture,p_introduction,p_ownerPhone,p_price,0);
end;