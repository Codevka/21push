create procedure userShowOrderInfo(out status int, in workerOrderID int)
begin
    select a.tel,r.houseId,r.content,r.pic
    from WorkOrder w,
         Repair r,
         Account a
    where w.username = a.username
      and r.repairId = w.repairId
      and w.repairWorkId = workerOrderID;
      select 1 into status;
end;
--为用户展示，房源编号、报修描述和报修图片，维修师傅联系方式。