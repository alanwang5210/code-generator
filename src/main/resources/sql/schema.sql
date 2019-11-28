-- drop table if exists `db_config`;
create table if not exists `db_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `db_name` varchar(30) COMMENT '名字',
  `url` varchar(255) COMMENT 'url',
  `driver` varchar(150) COMMENT 'driver',
  `username` varchar(255) COMMENT '用户名',
  `password` varchar(255) COMMENT '密码',
  `schema` varchar(255),
  `catalog` varchar(255),
  `db_type` varchar(255),
  PRIMARY KEY (`id`)
) engine=innodb default charset=utf8;