create view adminShowHouseInfo
as
    select  h.HID, location, datiledLocation, rentType, houseType, picture, introduction, ownerPhone, price, status   from House h
