create procedure userApplyToRent(OUT status int, IN UID int, IN HID int, IN rentTim date,
                                                       IN contractDuratio int, IN pric int, IN commen text)
begin
     if (select h.houseStatus from House h where h.houseId=HID )<>0 and  (select h.houseStatus from House h where h.houseId=HID )<>-1
         then
             update House h1 set h1.houseStatus=0;
         insert into Orders( username, houseId, rentTime, orderGenerateTime, contractDuration, status, price, comment)  values (UID,HID,rentTim,now(),contractDuratio,0,pric,commen);
         select  1 into status;
         else
         select -1 into  status;
     end if;
 end;



 --返回 1 表示申请成功
 --返回 -1 表示该房已满
 --  ！！！！！   这里存的是申请租用的时长，而不是结束的日期！！！！！！！！    ！！！！！！！！！！
 --这里会把房子的状态暂时设计为已经租出（不然会出现多个用户同时申请一个房子的情况）
 -- 原因是zrq的表就这么写的（逃）
