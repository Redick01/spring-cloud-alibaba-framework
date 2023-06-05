create table if not exists seata_stock
(
    id         bigint(11) auto_increment
    primary key,
    product_id bigint(11) null comment '产品id',
    total      int        null comment '总库存',
    used       int        null comment '已用库存',
    residue    int        null comment '剩余库存'
    )
    charset = utf8;

create table if not exists undo_log
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
    )
    charset = utf8;

