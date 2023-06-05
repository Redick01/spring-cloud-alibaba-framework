create table if not exists `ruuby-order`.seata_order
(
    id         bigint(11) auto_increment
    primary key,
    user_id    bigint(11)  null comment '用户id',
    product_id bigint(11)  null comment '产品id',
    count      int         null comment '数量',
    money      decimal(11) null comment '金额',
    status     int(1)      null comment '订单状态：0：创建中；1：已完结'
    );

create table if not exists `ruuby-order`.undo_log
(
    id            bigint auto_increment
    primary key,
    branch_id     bigint       not null,
    xid           varchar(100) not null,
    context       varchar(128) not null,
    rollback_info longblob     not null,
    log_status    int          not null,
    log_created   datetime     not null,
    log_modified  datetime     not null,
    ext           varchar(100) null,
    constraint ux_undo_log
    unique (xid, branch_id)
    );

