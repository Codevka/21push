 create procedure adminSelectAllUnhandledRepair(out status int)
 begin
          select *from Repair  r where r.status=0;
          select 1 into status;
 end;
