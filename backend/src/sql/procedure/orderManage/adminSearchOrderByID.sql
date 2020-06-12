 create procedure adminSearchOrderByID(out status int ,in OID int )
 begin
     if     exists(select o.contractId from Orders o where o.contractId=OID)
         then
            select 1 into status;
         select *from  Orders o1 where o1.contractId=OID;
         else
         select -1 into status;
     end if;
 end;
 --返回-1表示不存在此订单
 --1表示ok
 