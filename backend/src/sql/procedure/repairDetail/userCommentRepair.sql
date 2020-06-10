create procedure userCommentRepair(out status int, in RID int, in commen text, in scor int)
begin
    if (select status from Repair r where r.repairId=RID)=2
    then
          update Repair r
    set r.evaluation=commen,
        r.score=scor,
        r.status=3
    where r.repairId = RID;
    select  1 into status;
          else
        select -1 into status;
    end if;
end;
-- 用户对保修做出评价,当且仅当repair处于 修理完毕未评价状态时能够被评价

