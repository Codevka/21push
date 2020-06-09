create procedure userCheckSelfComplaintAll(out status int, in usernam int)
begin
      select *from Complaint c where c.username=usernam;
      select 1 into status;
end;