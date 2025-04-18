SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for url_map
-- ----------------------------
DROP TABLE IF EXISTS `url_map`;
CREATE TABLE `url_map`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `short_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短链接',
    `long_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '长链接',
    `views` int(0) NOT NULL DEFAULT 0 COMMENT '访问次数',
    `create_time` bigint(0) NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `short_url`(`short_url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
