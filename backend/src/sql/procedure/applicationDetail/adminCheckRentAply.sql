 create procedure adminCheckRentApply(out status int ,in OID int ,OrderStatu int)
 begin
if  OrderStatu=-1
    then
    update House h set h.houseStatus=1 where h.houseId=(select o.houseId from Orders o where o.contractId=OID);
    update Orders o1 set o1.status=OrderStatu where o1.contractId=OID;
    select 1 into status;
    else
       update Orders o2 set o2.status=OrderStatu where o2.contractId=OID;
       select 2 into status;
end if;
 end;

-- Orderstat 是 -1 说明拒绝了这个申请，于是就把目标房子的状态改回了未租出，返回1
--Orderstat 是 1 说明同意了这个申请，返回2