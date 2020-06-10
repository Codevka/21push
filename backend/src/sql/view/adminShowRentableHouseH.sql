create view adminShowRentableHouseH
as
    select *from House as h where h.status<>-1
    -- (可以改'rented')