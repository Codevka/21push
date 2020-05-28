create procedure allCheckSelfInfoA(IN UID int)
begin
    select *from   Account a where a.UID=@UID;
end;

 