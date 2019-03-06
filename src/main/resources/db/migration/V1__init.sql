create sequence customer_sequence start with 1 increment by 1;
create sequence location_sequence start with 1 increment by 1;
create sequence order_sequence start with 1 increment by 1;
create sequence product_category_sequence start with 1 increment by 1;
create sequence product_sequence start with 1 increment by 1;
create sequence supplier_sequence start with 1 increment by 1;

create table customer (id integer GENERATED BY DEFAULT AS IDENTITY, first_name varchar(255), last_name varchar(255), username varchar(255), primary key (id));
create table location (id integer GENERATED BY DEFAULT AS IDENTITY, city varchar(255), country varchar(255), county varchar(255), name varchar(255), street_address varchar(255), primary key (id));
create table product_order (id integer GENERATED BY DEFAULT AS IDENTITY, city varchar(255), country varchar(255), county varchar(255), street_address varchar(255), customer integer, shipped_from integer, primary key (id));
create table order_detail (quantity integer, order_id integer not null, product_id integer not null, primary key (order_id, product_id));
create table product (id integer GENERATED BY DEFAULT AS IDENTITY, description varchar(255), name varchar(255), price decimal(19,2), weight double, category integer, supplier integer, primary key (id));
create table product_category (id integer GENERATED BY DEFAULT AS IDENTITY, description varchar(255), name varchar(255), primary key (id));
create table stock (quantity integer, location_id integer not null, product_id integer not null, primary key (location_id, product_id));
create table supplier (id integer GENERATED BY DEFAULT AS IDENTITY, name varchar(255), primary key (id));
alter table product_order add constraint FKtfp2hwv6siwe2rkt9nq9cqvst foreign key (customer) references customer;
alter table product_order add constraint FK5ctg5pf4vox82b4orrgg3alc8 foreign key (shipped_from) references location;
alter table order_detail add constraint FKplam7wxc4tjbgex0xyk8f0qxo foreign key (order_id) references product_order;
alter table order_detail add constraint FKb8bg2bkty0oksa3wiq5mp5qnc foreign key (product_id) references product;
alter table product add constraint FKtkqfa7dfd5qe1m0vj4jr6kdyv foreign key (category) references product_category;
alter table product add constraint FKhk956bj7gcdn9e2paefdt5spw foreign key (supplier) references supplier;
alter table stock add constraint FK6t3m0kaf6fubuus331gf7xmn8 foreign key (location_id) references location;
alter table stock add constraint FKjghkvw2snnsr5gpct0of7xfcf foreign key (product_id) references product;

insert into customer (first_name, last_name, username) values ('Alanah', 'Espinay', 'aespinay0');
insert into customer (first_name, last_name, username) values ('Kipper', 'Francesc', 'kfrancesc1');
insert into customer (first_name, last_name, username) values ('Chancey', 'Crocken', 'ccrocken2');

insert into location (city, country, county, name, street_address) values ('Hägersten', 'Sweden', 'Stockholm', 'Algoma', '982 Ilene Park');
insert into location (city, country, county, name, street_address) values ('Cluj-Napoca', 'Romania', 'CJ', 'Cordelia', 'Croitorilor 5');
insert into location (city, country, county, name, street_address) values ('Passau', 'Germany', 'PA', 'Packers', 'Domplatz');

insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Newmarket', 'Canada', 'Ontario', '75 Corben Place', 1, 2);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Paris', 'France', 'Île-de-France', '8 4th Trail', 3, 2);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Edsbyn', 'Sweden', 'Gävleborg', '9 Knutson Place', 1, 3);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Vila Maior', 'Portugal', 'Aveiro', '96072 Fordem Street', 3, 1);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Paris', 'France', 'Île-de-France', '8 4th Trail', 3, 2);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Edsbyn', 'Sweden', 'Gävleborg', '9 Knutson Place', 1, 3);
insert into product_order (city, country, county, street_address, customer, shipped_from) values ('Newmarket', 'Canada', 'Ontario', '75 Corben Place', 1, 3);

insert into product_category(description, name) values ('Vehicles and accessories', 'cars');
insert into product_category(description, name) values ('Products for healthcare', 'pharmaceuticals');

insert into supplier(name) values ('Mante-Howell');
insert into supplier(name) values ('Barton-Ward');
insert into supplier(name) values ('Kub-Wolff');

insert into product (description, name, price, weight, category, supplier) values ('oxycodone hydrochloride', 'Roxicodone', 9.50, 2, 1, 2);
insert into product (description, name, price, weight, category, supplier) values ('teduglutide', 'Gattex', 9.49, 10, 1, 1);
insert into product (description, name, price, weight, category, supplier) values ('Simethicone', 'Gas Relief', 15.64, 5, 1, 2);
insert into product (description, name, price, weight, category, supplier) values ('Panamera', 'Porsche', 65754.36, 22073, 2, 3);
insert into product (description, name, price, weight, category, supplier) values ('Odyssey', 'Honda', 18227.99, 25674, 2, 1);
insert into product (description, name, price, weight, category, supplier) values ('300D', 'Mercedes-Benz', 15784.49, 20612, 2, 3);
insert into product (description, name, price, weight, category, supplier) values ('Grand Marquis', 'Mercury', 2628.75, 23534, 1, 2);

insert into order_detail (quantity, order_id, product_id) values (12, 1, 5);
insert into order_detail (quantity, order_id, product_id) values (1, 4, 2);
insert into order_detail (quantity, order_id, product_id) values (6, 2, 6);
insert into order_detail (quantity, order_id, product_id) values (3, 3, 1);

insert into stock(quantity, location_id, product_id) values (2, 1, 1);
insert into stock(quantity, location_id, product_id) values (2, 2, 1);
insert into stock(quantity, location_id, product_id) values (2, 3, 2);
insert into stock(quantity, location_id, product_id) values (2, 2, 6);
insert into stock(quantity, location_id, product_id) values (2, 2, 5);
