-- auto-generated definition
create table user
(
    ids          int auto_increment comment '主键唯一表示,自增设置为int'
        primary key,
    nickName     varchar(255)                       null comment '昵称',
    userAccount  varchar(255)                       null comment '账号',
    userPassword varchar(255)                       not null comment '密码-12345678',
    avatar       varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别
',
    phone        varchar(128)                       null comment '电话',
    email        varchar(522)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除 0-未删除',
    userRole     int      default 0                 not null comment '用户角色 0-普通用户 1-管理员',
    planetCode   varchar(512)                       null comment '星球编号'
)
    comment '用户表';

