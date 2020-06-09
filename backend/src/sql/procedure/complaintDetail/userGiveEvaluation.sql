create
    definer = root@localhost procedure userGiveEvaluation(OUT status int, IN CID int, IN con text, IN scor int)
begin
update Complaint c set
            c.evaluation=con,c.score=scor,c.dealingStatus=2 where c.complaintId=CID;
select 1 into status;
end;

