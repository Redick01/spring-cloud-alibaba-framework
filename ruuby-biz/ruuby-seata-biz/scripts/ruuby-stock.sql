create table if not exists `ruuby-stock`.seata_account
(
    id      bigint(11) auto_increment comment 'id'
    primary key,
    user_id bigint(11)        null comment '用户id',
    total   decimal           null comment '总额度',
    used    decimal           null comment '已用余额',
    residue decimal default 0 null comment '剩余可用额度'
    );

create table if not exists `ruuby-stock`.undo_log
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

