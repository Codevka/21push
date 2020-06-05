 create procedure adminCloseHouse(out status int ,in HID int )
 begin
    if (select h.houseStatus from House h)<>0
        then
        update  House h1
            set h1.houseStatus=-1 where houseId=HID;
            select 1 into status;
        else
        select -1 into status;
    end if;
 end;
 -- 返回1 说明正常关闭
 --返回-1 说明该房子已经租出了