CREATE DATABASE `mini_bank` /*!40100 DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
USE `mini_bank`;

CREATE TABLE `users`
(
    `id`            int         NOT NULL AUTO_INCREMENT,
    `user_login`    varchar(45) NOT NULL,
    `user_password` varchar(30) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `users_user_login_index` (`user_login`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users_accounts`
(
    `id`           int         NOT NULL AUTO_INCREMENT,
    `first_name`   varchar(45) NOT NULL,
    `last_name`    varchar(45) NOT NULL,
    `phone_number` varchar(15) NOT NULL,
    `email`        varchar(45) NOT NULL,
    `user_id`      int         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_accounts_user_id_uindex` (`user_id`),
    CONSTRAINT `users_id___fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `currency_accounts`
(
    `id`              int          NOT NULL AUTO_INCREMENT,
    `account_number`  int          NOT NULL,
    `currency`        varchar(45)  NOT NULL,
    `code`            varchar(3)   NOT NULL,
    `balance`         double(6, 2) NOT NULL,
    `user_account_id` int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `user_account_id___fk` (`user_account_id`),
    CONSTRAINT `user_account_id___fk` FOREIGN KEY (`user_account_id`) REFERENCES `users_accounts` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `transaction_logs`
(
    `id`                        int    NOT NULL AUTO_INCREMENT,
    `date`                      date   NOT NULL,
    `foreign_currency_account`  int    NOT NULL,
    `transfer_amount`           double NOT NULL,
    `balance_after_transaction` double NOT NULL,
    `currency_account_id`       int    NOT NULL,
    PRIMARY KEY (`id`),
    KEY `currency_account_id___fk` (`currency_account_id`),
    CONSTRAINT `currency_account_id___fk` FOREIGN KEY (`currency_account_id`) REFERENCES `currency_accounts` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `rates`
(
    `id`       int    NOT NULL AUTO_INCREMENT,
    `currency` varchar(45)     DEFAULT NULL,
    `code`     varchar(3)      DEFAULT NULL,
    `bid`      double NOT NULL DEFAULT '0',
    `ask`      double NOT NULL DEFAULT '0',
    `ratedate` date            DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;






