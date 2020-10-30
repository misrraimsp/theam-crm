drop table if exists customer;
drop table if exists image;

create table customer (id varchar(255) not null, created_by varchar(255), created_date timestamp, last_modified_by varchar(255), last_modified_date timestamp, name varchar(255), surname varchar(255), image_id varchar(255), primary key (id));
create table image (id varchar(255) not null, data bytea, mime_type varchar(255), name varchar(255), primary key (id));

alter table customer add constraint fk_imageIdOnCustomer foreign key (image_id) references image(id);
