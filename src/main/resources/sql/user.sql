  CREATE TABLE `user` (
    `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL DEFAULT '',
    `password` VARCHAR(128) NOT NULL DEFAULT '',
    `salt` VARCHAR(32) NOT NULL DEFAULT '',
    `head_url` VARCHAR(256) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
  ) ENGINE=INNODB DEFAULT CHARSET=utf8;