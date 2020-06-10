create procedure adminCheckAllCom(out status int)
begin
        select *from Complaint c where c.dealingStatus=0;
        select 1 into status;
end;