create table post
(
    id serial,
    name varchar(50),
    text text,
    link varchar(75),
    created date,
    PRIMARY KEY (id, link)


)