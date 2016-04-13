CREATE TABLE `e_insurance_api_result` (
    `id_`                   INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `signature_`                VARCHAR(64)               DEFAULT NULL COMMENT '方法',
    `request_`                Text               DEFAULT NULL COMMENT '请求',
    `response_`                Text               DEFAULT NULL COMMENT '返回',
    `result_`              INTEGER          NULL COMMENT '结果',
    `diff_time_`              INTEGER          NULL COMMENT '请求耗时',
    `state_`                INTEGER          NOT NULL DEFAULT 1 COMMENT '状态',
    `created_`              TIMESTAMP        NOT NULL DEFAULT current_timestamp COMMENT '记录创建时间',
    `updated_`              TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
    PRIMARY KEY (`id_`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT '接口调用结果';