create procedure getUserTelByCom(out status int,in CID int)
begin
        select tel from Account a,Complaint c where a.username=c.username and c.complaintId=CID;
        select  1 into  status;
end;
--通过complaint获得用户的tel
