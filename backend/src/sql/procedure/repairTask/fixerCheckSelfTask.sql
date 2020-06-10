create procedure fixerCheckSelfTask(out status int ,in UID int)
begin
    select *from WorkOrder where username=UID;
    select 1 into status;
end;
