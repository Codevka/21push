create procedure adminRefuseRepair(OUT status int, in adminI int,IN RID int)
begin
    update Repair r
        set r.status=4,
            r.adminId=adminI
    where r.repairId=RID;
    select 1 into status;
end;

