create table makers (
  maker_id int primary key AUTO_INCREMENT,
  maker_name varchar(64) not null,
  created_by varchar(64),
  created_date datetime,
  last_modified_by varchar(64),
  last_modified_date datetime
);

create table goods (
  goods_id int primary key AUTO_INCREMENT,
  goods_name varchar(64) not null,
  maker_id int not null,
  FOREIGN KEY(maker_id) REFERENCES makers(maker_id)
);

