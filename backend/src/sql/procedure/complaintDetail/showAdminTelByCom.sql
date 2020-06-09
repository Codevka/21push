create procedure showAdminTelByCom (out status int,in CID int,in adminI int )
begin
select tel from Account  a,Complaint c where c.adminID=a.username and c.adminID=adminI;
select  1 into status;
end;