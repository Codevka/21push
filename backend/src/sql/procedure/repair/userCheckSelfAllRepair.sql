create procedure userCheckSelfAllRepair(out status int,in usernam int)
begin
        select *  from Repair r where r.username=usernam;
        select 1 into status;
end;
--用户查看自己的所有报修