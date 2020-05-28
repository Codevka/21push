create
    definer = root@localhost procedure userQuerySelfHouseHO(IN UID int)
begin
    select h.HID,h.location,h.datiledLocation,h.rentType,h.houseType,h.price,h.status
    from House h,
         Orders o
    where o.HID =h.HID
      and o.UID = UID;
end;


-- 用户用来查询自己的房屋状态
--显示的有 房屋编号 地区 地址 租房类型 房子类型 价格 状态
