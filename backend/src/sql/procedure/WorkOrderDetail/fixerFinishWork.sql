create procedure fixerFinishWork(out status int, in workerOrderID int, callbac text)
begin
    if (select status from WorkOrder g where g.repairWorkId = workerOrderID) = 0
    then
        update WorkOrder w
        set w.status=1
        where w.repairWorkId = workerOrderID;
        update Repair r
        set r.status=2
        where repairId = (select repairId from WorkOrder o1 where o1.repairWorkId = workerOrderID);
    else
        select -1 into status;
    end if;
end;
-- func 将工单和保修设置为完成状态
--返回 -1 说明该工单已经终止或是正常结束
