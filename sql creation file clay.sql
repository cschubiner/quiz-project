# ************************************************************
# Sequel Pro SQL dump
# Version 4004
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: mysql-user.stanford.edu (MySQL 5.1.66-0+squeeze1-log)
# Database: c_cs108_lsun569
# Generation Time: 2013-03-14 04:45:52 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Administrators
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Administrators`;

CREATE TABLE `Administrators` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userName` char(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

LOCK TABLES `Administrators` WRITE;
/*!40000 ALTER TABLE `Administrators` DISABLE KEYS */;

INSERT INTO `Administrators` (`id`, `userName`)
VALUES
	(1,'admin'),
	(2,'clay'),
	(3,'lxr'),
	(5,'lingtong');

/*!40000 ALTER TABLE `Administrators` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Announcements
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Announcements`;

CREATE TABLE `Announcements` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Message` varchar(256) DEFAULT NULL,
  `Title` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

LOCK TABLES `Announcements` WRITE;
/*!40000 ALTER TABLE `Announcements` DISABLE KEYS */;

INSERT INTO `Announcements` (`id`, `Message`, `Title`)
VALUES
	(1,'test announcement','yay!! first!!'),
	(2,'test announcement 2','yay another announcemnt'),
	(3,'test announcement 3','yay another announcemnt!'),
	(5,'test announcement 3','yay another announcemnt!'),
	(7,'test announcement 3','yay another announcemnt!'),
	(9,'test announcement 3','yay another announcemnt!'),
	(11,'test announcement 3','yay another announcemnt!'),
	(13,'test announceme3nt 3','yay another announcemnt!'),
	(15,'test announceme3nt 3','yay another announcemnt!'),
	(17,'final announcemtnt','yay another announcemnt!'),
	(19,'final announcemtnt2','yay another announcemnt!'),
	(21,'final announcemtnt3','yay another announcemnt!'),
	(23,'final announcemtnt4','yay another announcemnt!'),
	(25,'i am a kitten','Announcement'),
	(27,'MESSAGE','TITLE'),
	(29,'like, actually','lxr is a whore'),
	(31,'MUAHAHAHAHHAH','Lingtong is da boss'),
	(33,'dsf','sdf'),
	(35,'dsfs\r\nsd','ds'),
	(37,'Clay is actually pretty cool\r\n-Bill Gates','Clay is cool'),
	(38,'I am here in the shadows','Heart of the swarm release this week!'),
	(39,'please fix the search bar','lingtong');

/*!40000 ALTER TABLE `Announcements` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table FillQuestion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `FillQuestion`;

CREATE TABLE `FillQuestion` (
  `QuestionID` int(11) NOT NULL,
  `mQuizID` int(11) NOT NULL,
  `QuestionNumber` int(11) NOT NULL,
  `Question` text NOT NULL,
  `Fill` text NOT NULL,
  PRIMARY KEY (`QuestionID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `FillQuestion` WRITE;
/*!40000 ALTER TABLE `FillQuestion` DISABLE KEYS */;

INSERT INTO `FillQuestion` (`QuestionID`, `mQuizID`, `QuestionNumber`, `Question`, `Fill`)
VALUES
	(2,2,1,'f2','hi'),
	(5,4,0,'One, two, skip a few. Three, four, skip some _____. ','more');

/*!40000 ALTER TABLE `FillQuestion` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Friend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Friend`;

CREATE TABLE `Friend` (
  `FriendOne` char(64) NOT NULL,
  `FriendTwo` char(64) NOT NULL,
  PRIMARY KEY (`FriendOne`,`FriendTwo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `Friend` WRITE;
/*!40000 ALTER TABLE `Friend` DISABLE KEYS */;

INSERT INTO `Friend` (`FriendOne`, `FriendTwo`)
VALUES
	('clay','lingtong'),
	('clay','lxr'),
	('jay','lxr'),
	('lingtong','clay'),
	('lingtong','lxr'),
	('lxr','clay'),
	('lxr','jay'),
	('lxr','lingtong'),
	('null','null');

/*!40000 ALTER TABLE `Friend` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table mAchievement
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mAchievement`;

CREATE TABLE `mAchievement` (
  `mAchievementID` int(11) NOT NULL,
  `Name` char(64) NOT NULL,
  `Description` char(64) NOT NULL,
  `ImageName` char(64) NOT NULL,
  PRIMARY KEY (`mAchievementID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `mAchievement` WRITE;
/*!40000 ALTER TABLE `mAchievement` DISABLE KEYS */;

INSERT INTO `mAchievement` (`mAchievementID`, `Name`, `Description`, `ImageName`)
VALUES
	(0,'Amateur Author','You Created a Quiz!','AmateurAuthor.png'),
	(1,'Prolific Author','You Created 5 Quizzes!!','ProlificAuthor.png'),
	(2,'Prodigious Author','You Created 10 Quizzes!!!','ProdigiousAuthor.png'),
	(3,'Quiz Machine','You Took 10 Quizzes!!','QuizMachine.png'),
	(4,'I Am The Greatest','You Had the Highest Score on a Quiz!!','Greatest.png'),
	(5,'Practice Makes Perfect','You Took a Quiz in Practice Mode!!','Practice.png');

/*!40000 ALTER TABLE `mAchievement` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Message`;

CREATE TABLE `Message` (
  `Sender` char(64) NOT NULL,
  `Recipient` char(64) NOT NULL,
  `Message` text NOT NULL,
  `TimeSent` datetime NOT NULL,
  `Seen` int(11) NOT NULL,
  `MessageType` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;

INSERT INTO `Message` (`Sender`, `Recipient`, `Message`, `TimeSent`, `Seen`, `MessageType`)
VALUES
	('lxr','lingtong','asdasdasd','2013-03-09 23:57:15',1,0),
	('lxr','lingtong','sdfsdfsd','2013-03-10 00:00:42',1,0),
	('lxr','lingtong','asdasdasdasdasd','2013-03-10 00:01:04',1,0),
	('clay','lxr','clay wants to add you as a friend!','2013-03-10 18:11:42',1,1),
	('lingtong','lxr','asdadsasdasd','2013-03-10 21:49:29',1,0),
	('lxr','lingtong','SUPPPP','2013-03-10 22:14:20',1,0),
	('lxr','lingtong','s2','2013-03-10 22:14:35',1,0),
	('lingtong','lxr','asdasda whadddup','2013-03-10 22:15:05',0,0),
	('jay','lxr','jay wants to add you as a friend!','2013-03-10 22:37:52',1,1),
	('lxr','jay','hello jay','2013-03-10 22:39:19',1,0),
	('jay','lxr','oh HIII','2013-03-10 22:39:52',1,0),
	('lxr','jay','hi :)','2013-03-10 22:40:34',0,0),
	('lingtong','lxr','fuck you','2013-03-13 01:14:00',0,0),
	('lingtong','jay','lingtong wants to add you as a friend!','2013-03-13 01:14:05',0,1),
	('clay','lingtong','clay wants to add you as a friend!','2013-03-13 21:40:17',1,1),
	('clay','lingtong','hihhiih','2013-03-13 21:40:36',0,0),
	('lingtong','clay','waddup','2013-03-13 21:40:38',1,0),
	('clay','lingtong','i am a kitten','2013-03-13 21:40:50',0,0);

/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table mQuiz
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mQuiz`;

CREATE TABLE `mQuiz` (
  `mQuizID` int(11) NOT NULL,
  `QuizName` char(64) NOT NULL,
  `Author` char(64) NOT NULL,
  `LastModified` datetime NOT NULL,
  `NumTaken` int(11) NOT NULL,
  `Ordering` int(11) DEFAULT NULL,
  `Paging` int(11) DEFAULT NULL,
  `Correction` int(11) DEFAULT NULL,
  PRIMARY KEY (`mQuizID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `mQuiz` WRITE;
/*!40000 ALTER TABLE `mQuiz` DISABLE KEYS */;

INSERT INTO `mQuiz` (`mQuizID`, `QuizName`, `Author`, `LastModified`, `NumTaken`, `Ordering`, `Paging`, `Correction`)
VALUES
	(2,'test','lxr','2013-03-13 20:56:23',0,1,1,1),
	(1,'Impossible','lxr','2001-08-12 12:00:00',1,NULL,NULL,NULL),
	(6,'lingy','lingtong','2013-03-13 21:20:37',0,0,0,0),
	(4,'STARCRAFT QUIZ','clay','2013-03-12 04:14:21',0,0,1,1),
	(5,'ling','lingtong','2013-03-13 21:20:10',0,1,0,0);

/*!40000 ALTER TABLE `mQuiz` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ReportedQuizzes
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ReportedQuizzes`;

CREATE TABLE `ReportedQuizzes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mQuizID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

LOCK TABLES `ReportedQuizzes` WRITE;
/*!40000 ALTER TABLE `ReportedQuizzes` DISABLE KEYS */;

INSERT INTO `ReportedQuizzes` (`id`, `mQuizID`)
VALUES
	(3,1),
	(13,2),
	(11,4);

/*!40000 ALTER TABLE `ReportedQuizzes` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table ResponseQuestion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ResponseQuestion`;

CREATE TABLE `ResponseQuestion` (
  `QuestionID` int(11) NOT NULL,
  `mQuizID` int(11) NOT NULL,
  `QuestionNumber` int(11) NOT NULL,
  `Question` text NOT NULL,
  `Response` text NOT NULL,
  PRIMARY KEY (`QuestionID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `ResponseQuestion` WRITE;
/*!40000 ALTER TABLE `ResponseQuestion` DISABLE KEYS */;

INSERT INTO `ResponseQuestion` (`QuestionID`, `mQuizID`, `QuestionNumber`, `Question`, `Response`)
VALUES
	(7,5,0,'ads','asd'),
	(6,4,1,'Starcraft 2 player, MarineKing, plays as which race?','Terran'),
	(8,6,0,'asdasd','sd'),
	(3,2,2,'resp3','hi'),
	(1,2,0,'resp1','hi');

/*!40000 ALTER TABLE `ResponseQuestion` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tAchievement
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tAchievement`;

CREATE TABLE `tAchievement` (
  `tAchievementID` int(11) NOT NULL AUTO_INCREMENT,
  `mAchievementID` int(11) NOT NULL,
  `User` char(64) NOT NULL,
  `DateIssued` datetime NOT NULL,
  PRIMARY KEY (`tAchievementID`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

LOCK TABLES `tAchievement` WRITE;
/*!40000 ALTER TABLE `tAchievement` DISABLE KEYS */;

INSERT INTO `tAchievement` (`tAchievementID`, `mAchievementID`, `User`, `DateIssued`)
VALUES
	(1,4,'clay','0000-00-00 00:00:00'),
	(3,1,'clay','0000-00-00 00:00:00'),
	(5,2,'clay','0000-00-00 00:00:00'),
	(7,2,'clay','0000-00-00 00:00:00'),
	(9,2,'clay','0000-00-00 00:00:00');

/*!40000 ALTER TABLE `tAchievement` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tQuiz
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tQuiz`;

CREATE TABLE `tQuiz` (
  `tQuizID` int(11) NOT NULL AUTO_INCREMENT,
  `mQuizID` int(11) NOT NULL,
  `TakenBy` char(64) NOT NULL,
  `TimeTaken` datetime NOT NULL,
  `Score` int(11) NOT NULL,
  PRIMARY KEY (`tQuizID`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

LOCK TABLES `tQuiz` WRITE;
/*!40000 ALTER TABLE `tQuiz` DISABLE KEYS */;

INSERT INTO `tQuiz` (`tQuizID`, `mQuizID`, `TakenBy`, `TimeTaken`, `Score`)
VALUES
	(3,5,'lingtong','2013-03-11 01:36:49',1),
	(5,5,'lingtong','2013-03-11 01:36:52',1),
	(7,6,'lingtong','2013-03-11 01:41:36',1),
	(9,6,'lingtong','2013-03-11 01:41:47',2),
	(11,10,'lxr','2013-03-11 02:01:49',3),
	(25,2,'lxr','2013-03-13 20:25:14',0),
	(15,5,'clay','2013-03-11 03:53:42',1),
	(17,2,'clay','2013-03-11 03:54:17',0),
	(27,5,'clay','2013-03-13 21:31:52',0);

/*!40000 ALTER TABLE `tQuiz` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Users`;

CREATE TABLE `Users` (
  `UserName` char(64) NOT NULL,
  `Password` char(64) NOT NULL,
  `loginCookieToken` char(64) DEFAULT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;

INSERT INTO `Users` (`UserName`, `Password`, `loginCookieToken`)
VALUES
	('admin','780a3ea7cc231bc9ac0ce796e064a175a75291d8',NULL),
	('lxr','18e351d796c7e3150817bc8702bd987d6e9e6c3a','0ae0be64fb5461b45f77822eb49b3190a7d7e414'),
	('lingtong','18e351d796c7e3150817bc8702bd987d6e9e6c3a',NULL),
	('clay','18e351d796c7e3150817bc8702bd987d6e9e6c3a','595e67b3c63823abe0cc68805ee1feb527f4da7c'),
	('jay','18e351d796c7e3150817bc8702bd987d6e9e6c3a',NULL),
	('clay2','18e351d796c7e3150817bc8702bd987d6e9e6c3a',NULL),
	('crackling','18e351d796c7e3150817bc8702bd987d6e9e6c3a','');

/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
