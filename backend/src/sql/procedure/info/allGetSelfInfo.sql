create procedure allGetSelfInfo(out status int ,in ID int)
begin
    select 1 into status;
    select * from   Account where ID = Account.username;
end;