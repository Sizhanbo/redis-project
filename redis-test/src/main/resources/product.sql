create database if not exists springboot_demo;
use springboot_demo;
create table if not exists product
(
    id          bigint auto_increment primary key,
    name        varchar(255)  NOT NULL,
    description varchar(255),
    price       double(10, 2) not null,
    stock       int           not null
);
insert into product (name, description, price, stock)
values ('笔记本电脑', '高性能商务笔记本电脑，搭载酷睿i9处理器', 9999.99, 50),
       ('智能手机', '新款5G智能手机，6.7英寸超清大屏，512G内存', 6999.99, 100),
       ('平板电脑', '轻薄便携平板电脑，续航12小时，支持触控笔', 2999.99, 75),
       ('无线蓝牙耳机', '主动降噪无线蓝牙耳机，续航30小时，适配全机型', 399.99, 200),
       ('智能手表', '多功能智能手表，支持心率监测、NFC支付、防水', 1299.99, 80),
       ('无线充电器', '20W快充无线充电器，兼容苹果/安卓机型', 89.99, 300),
       ('台式电脑主机', '酷睿i7台式主机，16G内存+1T固态，游戏办公通用', 5999.99, 40),
       ('家用打印机', '无线喷墨打印机，支持复印/扫描，适配家用场景', 899.99, 60);