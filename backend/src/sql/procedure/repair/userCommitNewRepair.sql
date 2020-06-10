 create procedure userCommitNewRepair(out status int ,in  usernam int,in houseI int ,in conten text,in pi text)
 begin
            if exists(select * from Orders where username =usernam and houseId=houseI and status=2)
                then
                    insert into Repair(username, houseId, status, content, pic) values (usernam, houseI, 0, conten, pi);
                    select 1 into status;
                else
                select -1 into status;
            end if;
 end;
 --提交报修申请的人必须已经支付租金，并且提交的房屋号要存在对应的订单，否则返回-1
