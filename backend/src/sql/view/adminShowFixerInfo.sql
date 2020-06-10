create view adminShowFixerInfo
as
    select a.UID,a.username,a.location,a.telephone
    from Account a where authority=3 
    --(上传文档这里可以写成'fixer')