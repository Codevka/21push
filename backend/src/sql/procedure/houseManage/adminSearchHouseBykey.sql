 create procedure adminSearchHouseByKey(OUT status int, IN KeyW varchar(64))
begin
    select houseId, province, city, area, address, rentType, houseType, pic, intro, ownerTel, price, houseStatus from House h where intro like concat('%',KeyW,'%');
     select 1 into status;
end;



