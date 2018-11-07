/*
 Navicat Premium Data Transfer

 Source Server         : BookCrossing
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : bookcrossing

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 07/11/2018 11:13:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_loc
-- ----------------------------
DROP TABLE IF EXISTS `book_loc`;
CREATE TABLE `book_loc`  (
  `book_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scho_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `available` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE,
  CONSTRAINT `book_loc_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book_map` (`book_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_loc
-- ----------------------------
INSERT INTO `book_loc` VALUES ('1', '1', '', 1);
INSERT INTO `book_loc` VALUES ('2', '1', NULL, 0);
INSERT INTO `book_loc` VALUES ('3', NULL, '1', 1);
INSERT INTO `book_loc` VALUES ('4', NULL, '1', 1);

-- ----------------------------
-- Table structure for book_map
-- ----------------------------
DROP TABLE IF EXISTS `book_map`;
CREATE TABLE `book_map`  (
  `book_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `picture` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `press` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_map
-- ----------------------------
INSERT INTO `book_map` VALUES ('1', 'Java编程思想', 'http://10.11.32.180:8080/staticimage/7e06e8f3-d9ec-4798-bdbb-58383787eb2c.jpg', '[美] Bruce Eckel 著，陈昊鹏 译', '计算机科学丛书：Java编程思想（第4版）》赢得了全球程序员的广泛赞誉，即使是晦涩的概念，在BruceEckel的文字亲和力和小而直接的编程示例面前也会化解于无形。从Java的基础语法到高级特性（深入的面向对象概念、多线程、自动项目构建、单元测试和调试等），本书都能逐步指导你轻松掌握。', '机械工业出版社');
INSERT INTO `book_map` VALUES ('10', '剑指Offer：名企面试官精讲典型编程题（第2版）', 'http://202.114.6.141:8080/staticimage/9359afeb-b18f-4b40-8b69-49df8ee2b664.jpg', '何海涛 著', '《剑指Offer：名企面试官精讲典型编程题（第2版）》剖析了80个典型的编程面试题，系统整理基础知识、代码质量、解题思路、优化效率和综合能力这5个面试要点。', '电子工业出版社');
INSERT INTO `book_map` VALUES ('11', '红与黑', 'http://202.114.6.141:8080/staticimage/f79413ef-45b5-4eb3-9d19-84f38565f018.jpg', '司汤达 著', '欧洲批判现实主义文学奠基者、“现代小说之父”司汤达经典传世名作\r\n\r\n军职与圣职，梦想与现实，用红与黑的色彩谱写一首“灵魂的哲学诗”；\r\n\r\n自尊与自卑，野心与彷徨，雄浑笔力描摹“少年野心家”跌宕起伏的人生。', '天津人民出版社');
INSERT INTO `book_map` VALUES ('2', '算法导论', 'http://202.114.6.141:8080/staticimage/73733de4-cada-4f7d-8140-a1becea5558b.jpg', '[美] Thomas H.Cormen', '在有关算法的书中，有一些叙述非常严谨，但不够全面；另一些涉及了大量的题材，但又缺乏严谨性。《算法导论（原书第3版）/计算机科学丛书》将严谨性和全面性融为一体，深入讨论各类算法，并着力使这些算法的设计和分析能为各个层次的读者接受。全书各章自成体系，可以作为独立的学习单元；算法以英语和伪代码的形式描述，具备初步程序设计经验的人就能看懂；说明和解释力求浅显易懂，不失深度和数学严谨性。', '机械工业出版社');
INSERT INTO `book_map` VALUES ('3', 'Python编程 从入门到实践', 'http://202.114.6141:8080/staticimage/cb16427f-361b-406c-ae02-9deeb1c9b6e9.jpg', '[美] 埃里克·马瑟斯（Eric Matthes） 著', '本书是一本针对所有层次的Python读者而作的Python入门书。全书分两部分：首部分介绍用Python 编程所必须了解的基本概念，包括matplotlib、NumPy和Pygal等强大的Python库和工具介绍，以及列表、字典、if语句、类、文件与异常、代码测试等内容；第二部分将理论付诸实践，讲解如何开发三个项目，包括简单的Python 2D游戏开发，如何利用数据生成交互式的信息图，以及创建和定制简单的Web应用，并帮读者解决常见编程问题和困惑。', '人民邮电出版社');
INSERT INTO `book_map` VALUES ('4', '算法', 'http://202.114.6.141:8080/staticimage/41787721-3cb5-4088-b496-fea2fcf1051c.jpg', '[美] Robert Sedgewick，[美] Kevin Wayne 著', 'Sedgewick之巨著，与高德纳TAOCP一脉相承\r\n　　几十年多次修订，经久不衰的畅销书\r\n　　涵盖所有程序员必须掌握的50种算法', '人民邮电出版社');
INSERT INTO `book_map` VALUES ('5', '码农翻身：用故事给技术加点料', 'http://202.114.6.141:8080/staticimage/ac325c28-e9a3-46c0-a258-51635c3cfac9.jpg', '刘欣（@码农翻身） 著', '《码农翻身》用故事的方式讲解了软件编程的若干重要领域，侧重于基础性、原理性的知识。\r\n\r\n《码农翻身》分为6章。\r\n\r\n第1章讲述计算机的基础知识；\r\n\r\n第2章侧重讲解Java的基础知识；\r\n\r\n第3章偏重Web后端编程；\r\n\r\n第4章讲解代码管理的本质；\r\n\r\n第5章讲述了JavaScript的历史、Node.js的原理、程序的链接、命令式和声明式编程的区别，以及作者十多年来使用各种编程语言的感受；\r\n\r\n第6章是作者的经验总结和心得体会，包括职场发展的注意事项、作为架构师的感想、写作的好处等。', '电子工业出版社');
INSERT INTO `book_map` VALUES ('6', '深度学习', 'http://202.114.6.141:8080/staticimage/508e9bf9-55c1-4eb6-a28b-58a90ef3077c.jpg', '美] Ian，Goodfellow，[加] Yoshua，Bengio著', 'AI圣经！深度学习领域奠基性的经典畅销书！长期位居美国ya马逊AI和机器学习类图书榜首！所有数据科学家和机器学习从业者的bi读图书！特斯拉CEO埃隆·马斯克等国内外众多专家推jian！', '人民邮电出版社');
INSERT INTO `book_map` VALUES ('7', '余华作品：活着', 'http://202.114.6.141:8080/staticimage/39c0c30a-c827-4973-b9c9-e550ec53749b.jpg', '余华 著', '《活着（新版）》讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。他的父亲被他活活气死，母亲则在穷困中患了重病，福贵前去求药，却在途中被国民党抓去当壮丁。经过几番波折回到家里，才知道母亲早已去世，妻子家珍含辛茹苦地养大两个儿女。此后更加悲惨的命运一次又一次降临到福贵身上，他的妻子、儿女和孙子相继死去，最后只剩福贵和一头老牛相依为命，但老人依旧活着，仿佛比往日更加洒脱与坚强。', '作家出版社');
INSERT INTO `book_map` VALUES ('8', '浮生六记', 'http://202.114.6.141:8080/staticimage/7a7af505-b62f-499e-9279-78e085fb75dd.jpg', '[清] 沈复 著', '《浮生六记》为自传散文体小说，书中记闺房之乐，琴瑟相和、缱绻情深;记闲情雅趣，贫士心性、喜恶爱憎；记人生坎坷，困顿离合、人情世态;记各地浪游，山水名胜、奇闻趣观。作者以纯朴的文笔，记叙大半生的经历，欢愉与愁苦两相对照，真切动人。书中描述了他和妻子陈芸志趣投合，伉俪情深，愿意过一种布衣蔬食的生活，可由于贫困生活的煎熬，终至理想破灭，经历了生离死别的惨痛。', '浙江工商大学出版社');
INSERT INTO `book_map` VALUES ('9', '我与地坛', 'http://202.114.6.141:8080/staticimage/5ef64531-5217-4d88-9ece-9687fc2bb77b.jpg', '史铁生 著', '《我与地坛》是史铁生的经典散文集，2010年12月31日，史铁生离开，这本书问世。此后七年，这本书以每年近30万册的数量持续畅销。千千万万读者从《我与地坛》阅读史铁生，认识史铁生，怀念史铁生。', '人民文学出版社');
INSERT INTO `book_map` VALUES ('B2A97D49CEBB4A9D83394E3C96511F1C', '数学之美（第二版）', 'http://202.114.6.141:8080/staticimage/6d379d73-f507-4b66-8774-8543fef9cf9b.jpg', '吴军', '几年前，“数学之美”系列文章原刊载于谷歌黑板报，获得上百万次点击，得到读者高度评价。正式出版前，吴军博士几乎把所有文章都重写了一遍，为的是把高深的数学原理讲得更加通俗易懂，让非专业读者也能领略数学的魅力。 \r\n　　《数学之美》上市后深受广大读者欢迎，并荣获国家图书馆第八届文津图书奖。读者说，读了《数学之美》，才发现大学时学的数学知识，比如马尔科夫链、矩阵计算，甚至余弦函数原来都如此亲切，并且栩栩如生，才发现自然语言和信息处理这么有趣。', '人民邮电出版社');

-- ----------------------------
-- Table structure for book_owner
-- ----------------------------
DROP TABLE IF EXISTS `book_owner`;
CREATE TABLE `book_owner`  (
  `book_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `book_owner_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book_map` (`book_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `book_owner_ibfk_2` FOREIGN KEY (`stu_id`) REFERENCES `stu_map` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_owner
-- ----------------------------
INSERT INTO `book_owner` VALUES ('1', '1');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `stu_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scho_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `book_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email_addr` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE,
  INDEX `scho_id`(`scho_id`) USING BTREE,
  INDEX `book_id`(`book_id`) USING BTREE,
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `stu_map` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `member_ibfk_2` FOREIGN KEY (`scho_id`) REFERENCES `school_map` (`scho_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `member_ibfk_3` FOREIGN KEY (`book_id`) REFERENCES `book_map` (`book_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '1', '1', 'zjj@hust.edu.cn');

-- ----------------------------
-- Table structure for school_map
-- ----------------------------
DROP TABLE IF EXISTS `school_map`;
CREATE TABLE `school_map`  (
  `scho_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scho_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`scho_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school_map
-- ----------------------------
INSERT INTO `school_map` VALUES ('1', '计算机学院');
INSERT INTO `school_map` VALUES ('2', '人文学院');
INSERT INTO `school_map` VALUES ('3', '文法学院');
INSERT INTO `school_map` VALUES ('4', '机械学院');
INSERT INTO `school_map` VALUES ('5', '自动化学院');

-- ----------------------------
-- Table structure for stu_map
-- ----------------------------
DROP TABLE IF EXISTS `stu_map`;
CREATE TABLE `stu_map`  (
  `stu_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_map
-- ----------------------------
INSERT INTO `stu_map` VALUES ('1', 'zjj', '123456');
INSERT INTO `stu_map` VALUES ('201873056', 'zjj1', '123');

SET FOREIGN_KEY_CHECKS = 1;
