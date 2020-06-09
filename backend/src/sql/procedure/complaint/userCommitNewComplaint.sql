create procedure userCommitNewComplaint(out status int, in usernam int ,  houseI int,  conten text, pi text)
begin
        insert into Complaint(username, houseId, dealingStatus, content, pic)
        values ( usernam, houseI, 0, conten, pi);
        select 1 into status;
end;