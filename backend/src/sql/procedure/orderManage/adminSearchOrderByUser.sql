 create procedure adminSearchOrderByUser(out status int ,in UID int )
begin
    if   exists(select  username from  Orders where UID=Orders.username)
        then
            select 1 into status;
        select *  from    Orders where Orders.username=UID;
        else
        select -1 into status;
    end if;
end;
-- -1 表示这个用户没有订单

