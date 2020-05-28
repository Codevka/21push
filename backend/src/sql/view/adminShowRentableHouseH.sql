create view adminShowRentableHouseH
as
    select *from House h where h.status<>-1
    --(可以改'rented')