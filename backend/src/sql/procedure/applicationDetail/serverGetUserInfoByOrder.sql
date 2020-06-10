 create procedure serverGetUserInfoByOrder(out status int ,in OID int )
 begin
 select 1 into status;
select *from Account where Account.username=(select o.username from Orders o where o.contractId=OID);
 end;
 --页面组成：页面展示租客!!!个人信息!!!、房源信息、租房时长等内容，有“同意申请”、“拒绝申请”、“返回”按钮
