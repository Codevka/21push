create view adminShowUserInfoA
as
    select a.UID,a.username,a.location,a.telephone
    from Account a where authority=2
    -- (上传文档这里可以写成'user')