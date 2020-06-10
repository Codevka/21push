create procedure adminGenerateWorkOrder(out status int, in adminID int,in RID int,in fixerID int)
begin
    insert into WorkOrder(repairId, username, status) values (RID,fixerID,0);
    update Repair r
    set r.adminId=adminID where r.repairId=RID;
    select 1 into status;
end;
