create procedure userQueryRentableHouse (in UID int)
begin
    select *  from House h where h.location=(select  location from Account where Account.UID=UID);
end;
--用户查询自己可以租赁的房子
--返回House中所有列