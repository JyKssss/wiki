drop table if exists test;
create table test(
    id  int(15) not null comment 'id',
    name varchar(50) comment '名称',
    password varchar(50) comment '密码',
    primary key (id)
)engine=innodb default charset =utf8mb4 comment '测试'


drop table if exists demo;
create table demo(
                     id  int(15) not null comment 'id',
                     name varchar(50) comment '名称',
                     primary key (id)
)engine=innodb default charset =utf8mb4 comment '测试'


insert into demo (id, name) values (1,'test')


#电子书表
drop table if exists ebook;
create table ebook(
                      id bigint not null comment 'id',
                      name varchar(50) comment '名称',
                      category1_id bigint comment '分类1',
                      category2_id bigint comment '分类2',
                      description varchar(200) comment '描述',
                      cover varchar(200) comment '封面',
                      doc_count int comment '文档数',
                      view_count int comment '阅读数',
                      vote_count int comment '点赞数',
                      primary key (id)
)engine =innodb default charset =utf8mb4 comment '电子书'

insert into ebook(id, name, description) values (1,'Spring Boot 入门官方教程','零基础入门Java开发，企业级开发首选框架');
insert into ebook(id, name, description) values (2,'Vue 入门官方教程','零基础入门Vue开发，企业级开发首选框架');
insert into ebook(id, name, description) values (3,'Python 入门官方教程','零基础入门Python开发，企业级开发首选框架');
insert into ebook(id, name, description) values (4,'MySQL 入门官方教程','零基础入门MySQL开发，企业级开发首选框架');
insert into ebook(id, name, description) values (5,'Oracle 入门官方教程','零基础入门Oracle开发，企业级开发首选框架');

update ebook set cover='/image/cover1.png' where id=1;
update ebook set cover='/image/cover2.png' where id=2;


drop table if exists category;
create table category(
    id bigint not null comment 'id',
    parent bigint not null default 0 comment '父id',
    name varchar(50) not null comment '名称',
    sort int comment '顺序',
    primary key (id)
)engine =innodb default charset =utf8mb4 comment '分类表'

insert into category (id,parent, name, sort) values (100,000,'前端开发',100);
insert into category (id,parent, name, sort) values (101,100,'Vue',101);
insert into category (id,parent, name, sort) values (102,100,'HTML & CSS',102);
insert into category (id,parent, name, sort) values (200,000,'Java',200);
insert into category (id,parent, name, sort) values (201,200,'基础应用',201);
insert into category (id,parent, name, sort) values (202,200,'框架应用',202);
insert into category (id,parent, name, sort) values (300,000,'Python',300);
insert into category (id,parent, name, sort) values (301,300,'基础应用',301);
insert into category (id,parent, name, sort) values (302,300,'进阶方向应用',302);
insert into category (id,parent, name, sort) values (400,000,'数据库',400);
insert into category (id,parent, name, sort) values (401,400,'MySQL',401);
insert into category (id,parent, name, sort) values (500,000,'其它',500);
insert into category (id,parent, name, sort) values (501,500,'服务器',501);
insert into category (id,parent, name, sort) values (502,500,'开发工具',502);
insert into category (id,parent, name, sort) values (503,500,'热门服务端语言',503);

delete from category where id='200'