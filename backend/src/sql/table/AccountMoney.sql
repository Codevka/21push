create table AccountMoney
(
    username int not null primary key,
    money    int not null,
    constraint Money_Account_username_fk
        foreign key (username) references Account (username)
)