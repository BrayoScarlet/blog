/*==============================================================*/
/* DBMS name:      MySQL 5.7.19                                 */
/* Created on:     2017/12/25 19:32:44                          */
/*==============================================================*/


-- 创建数据库
CREATE DATABASE blog CHARSET 'utf8';

USE blog;

-- 创建user表
CREATE TABLE `user`(
   uid                  CHAR(32) NOT NULL,
   email                VARCHAR(20),
   username             VARCHAR(10),
   `password`           VARCHAR(32),	-- 存储加密后的算法
   school               VARCHAR(20),	
   qualification        VARCHAR(20),	-- 最高学历
   graduation_year      CHAR(4),		-- 毕业年份
   specialisations      TEXT,			-- 感兴趣的职业方向
   sex                  CHAR(2) DEFAULT '男',
   ubrief               TEXT,			-- 个人简介
   domicile             TEXT,			-- 居住地
   token                CHAR(64),		-- 激活码
   state                BOOL DEFAULT FALSE,	-- 激活状态
   admin                SMALLINT DEFAULT 0,	-- 用户权限 0：普通用户 1：普通管理员 2：超级管理员
   PRIMARY KEY (uid)
);

-- 创建博客表
CREATE TABLE article(
   aid                  CHAR(32) NOT NULL,
   author_uid           CHAR(32),		-- 外键, 表示博客属于谁(博主)
   atitle               TEXT,			-- 文章标题
   `type`               VARCHAR(10),	-- 文章类型(原创, 转发, 翻译)
   acontent             TEXT,			-- 文章内容
   classify             VARCHAR(10),	-- 文章分类
   abstract             TEXT,			-- 文章摘要
   praise_num           BIGINT DEFAULT 0,	-- 点赞数
   remark_num           BIGINT DEFAULT 0,	-- 回复数
   read_num				BIGINT DEFAULT 0,	-- 阅读量
   atime                DATETIME,			-- 发表时间
   verify				INT DEFAULT 0,		-- 审核字段 0: 未审核 1: 通过 2: 未通过
   PRIMARY KEY (aid)
);
-- 创建一个指向user的外键
ALTER TABLE article 
ADD CONSTRAINT FK_user_article 
FOREIGN KEY (author_uid) 
REFERENCES `user` (uid);

-- 创建回复表
CREATE TABLE remark (
   rid                  CHAR(32) NOT NULL,
   author_uid           CHAR(32),			-- 外键, 回复的人
   article_returned_aid CHAR(32),			-- 外键, 将回复的文章
   rcontent             TEXT,				-- 回复的内容
   rtime                DATETIME,			-- 回复的时间
   PRIMARY KEY (rid)
);
-- 创建一个指向article的外键
ALTER TABLE remark 
ADD CONSTRAINT FK_article_remark 
FOREIGN KEY (article_returned_aid) 
REFERENCES article (aid);
-- 创建一个指向user的外键
ALTER TABLE remark 
ADD CONSTRAINT FK_user_remark 
FOREIGN KEY (author_uid) 
REFERENCES `user` (uid);

-- 创建一个触发器, 当向remark表插入一条数据之后, article表中对应的记录的remark_num自增1
DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `blog`.`add_article_remark_num_trigger` AFTER INSERT
    ON `blog`.`remark`
    FOR EACH ROW BEGIN
		UPDATE article SET remark_num = remark_num + 1 WHERE aid = new.article_returned_aid;
    END$$

DELIMITER ;

-- 创建一个触发器, 当remark表删除一条数据之后, article表中对应的记录的remark_num自减1
DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `blog`.`minus_article_remark_num_trigger` AFTER DELETE
    ON `blog`.`remark`
    FOR EACH ROW BEGIN
		UPDATE article SET remark_num = remark_num - 1 WHERE aid = old.article_returned_aid;
    END$$

DELIMITER ;

-- 创建阅读记录表
CREATE TABLE read_record
(
   read_uid           CHAR(32) NOT NULL,	-- 阅读的人
   read_aid           CHAR(32) NOT NULL,	-- 被阅读的文章
   PRIMARY KEY (read_uid, read_aid)
);
-- 创建一个指向article的外键
ALTER TABLE read_record 
ADD CONSTRAINT FK_article_reader 
FOREIGN KEY (read_aid) 
REFERENCES article (aid);
-- 创建一个指向user的外键
ALTER TABLE read_record
ADD CONSTRAINT FK_user_reader 
FOREIGN KEY (read_uid) 
REFERENCES `user` (uid);


-- 创建点赞记录表
CREATE TABLE praise_record
(
   praise_uid           CHAR(32) NOT NULL,	-- 点赞的人
   praise_aid           CHAR(32) NOT NULL,	-- 被点赞的文章
   PRIMARY KEY (praise_uid, praise_aid)
);
-- 创建一个指向article的外键
ALTER TABLE praise_record 
ADD CONSTRAINT FK_article_praise 
FOREIGN KEY (praise_aid) 
REFERENCES article (aid);
-- 创建一个指向user的外键
ALTER TABLE praise_record
ADD CONSTRAINT FK_user_praise 
FOREIGN KEY (praise_uid) 
REFERENCES `user` (uid);

-- 创建一个触发器, 当向praise_record表插入一条数据之后, article表中对应的记录的praise_num自增1
DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `blog`.`add_article_praise_num_trigger` AFTER INSERT
    ON `blog`.`praise_record`
    FOR EACH ROW BEGIN
		UPDATE article SET praise_num = praise_num + 1 WHERE aid = new.praise_aid;
    END$$

DELIMITER ;

-- 创建一个触发器, 当praise_record表删除一条数据之后, article表中对应的记录的praise_num自减1
DELIMITER $$

CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `blog`.`minus_article_praise_num_trigger` AFTER DELETE
    ON `blog`.`praise_record`
    FOR EACH ROW BEGIN
		UPDATE article SET praise_num = praise_num - 1 WHERE aid = old.praise_aid;
    END$$

DELIMITER ;

/******************************************************/

-- 创建学习小组信息表
CREATE TABLE StuGroupInfo(
	stugroupId 		VARCHAR(32),		-- 主键
	memberNum 		INT,				-- 小组人数
	stugroupName 	VARCHAR(254),	-- 小组名字
	stugroupIntro	VARCHAR(254),	-- 小组简介
	leadId 			VARCHAR(32),			-- 创建人userId
	qqNumber 		VARCHAR(32),		-- 小组qq号
	buildDate		DATETIME,		-- 创建日期
	imageUrl		VARCHAR(254),	-- 图片链接
	PRIMARY KEY(stugroupId)
);

-- 创建小组成员信息表
CREATE TABLE MemberInfo(
	stugroupId 		VARCHAR(32),		-- 主键
	userId 			VARCHAR(32),
	PRIMARY KEY(stugroupId ,userId)
);

-- 创建用户反馈信息表
CREATE TABLE FeedBackInfo(
	fbId 		VARCHAR(32),	-- 主键
	fbTitle 	VARCHAR(254),	-- 标题
	fbContent 	TEXT,			-- 内容
	fbContact 	VARCHAR(254),	-- 联系方式
	userId 		VARCHAR(32),	-- 用户id
	fbTime 		DATETIME,		-- 创建时间
	PRIMARY KEY(fbId)
);
