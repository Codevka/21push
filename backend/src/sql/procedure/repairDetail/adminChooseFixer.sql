create procedure adminChooseFixer(out status int)
begin
        select * from Account a where a.userType=2;
        select 1 into status;
end;
