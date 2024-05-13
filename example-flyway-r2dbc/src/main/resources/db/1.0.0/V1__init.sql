CREATE TABLE `user`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户名',
    `password`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '密码',
    `email`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `nickname`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '昵称',
    `update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username_uk` (`username` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;