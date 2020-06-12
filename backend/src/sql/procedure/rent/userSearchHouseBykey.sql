create
    definer = root@localhost procedure userSearchHouseByKey(OUT status int, IN KeyW varchar(64))
begin
    select 1 into status;
    select houseId,
           province,
           city,
           area,
           address,
           rentType,
           houseType,
           pic,
           intro,
           ownerTel,
           price,
           houseStatus
    from House h
    where houseStatus <> 0
      and houseStatus <> -1
      and (intro like concat('%', KeyW, '%') or (intro like concat('%', KeyW, '%')) or
           (intro like concat('%', KeyW, '%')) or
           (province like concat('%', KeyW, '%')) or
           (city like concat('%', KeyW, '%')) or
           (area like concat('%', KeyW, '%')) or
           (address like concat('%', KeyW, '%')));
end;



--我这里照的是main.js里面的 searchHouses 函数写的，不知道为啥不用上地址
-- 这里不用的话感觉没地方用了啊