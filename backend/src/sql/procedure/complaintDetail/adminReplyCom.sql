create procedure adminReplyCom(out status int,in CID int,in con text,in adminI int)
begin
update Complaint c set
                       c.reply=con,
                       c.adminID=adminI,
                       c.dealingStatus=1;
select 1 into status;
end;