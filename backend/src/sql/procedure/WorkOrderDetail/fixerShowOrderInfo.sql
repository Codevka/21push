create procedure fixerShowOrderInfo(out status int, in workerOrderID int)
begin
    select a.tel,r.houseId,r.content,r.pic
    from WorkOrder w,
         Repair r,
         Account a
    where r.username = a.username
      and r.repairId = w.repairId
      and w.repairWorkId = workerOrderID;
      select 1 into status;
end;
--为fixer展示租客提交的报修房源编号、报修描述和报修图片,租客联系方式