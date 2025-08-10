/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.7.2-MariaDB, for osx10.20 (arm64)
--
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `active_storage_attachments`
--

DROP TABLE IF EXISTS `active_storage_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `active_storage_attachments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `record_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `record_id` bigint NOT NULL,
  `blob_id` bigint NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_active_storage_attachments_uniqueness` (`record_type`,`record_id`,`name`,`blob_id`),
  KEY `index_active_storage_attachments_on_blob_id` (`blob_id`),
  CONSTRAINT `fk_rails_c3b3935057` FOREIGN KEY (`blob_id`) REFERENCES `active_storage_blobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `active_storage_blobs`
--

DROP TABLE IF EXISTS `active_storage_blobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `active_storage_blobs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `filename` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `metadata` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `byte_size` bigint NOT NULL,
  `checksum` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `service_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_active_storage_blobs_on_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `active_storage_variant_records`
--

DROP TABLE IF EXISTS `active_storage_variant_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `active_storage_variant_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `blob_id` bigint NOT NULL,
  `variation_digest` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_active_storage_variant_records_uniqueness` (`blob_id`,`variation_digest`),
  CONSTRAINT `fk_rails_993965df05` FOREIGN KEY (`blob_id`) REFERENCES `active_storage_blobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ar_internal_metadata`
--

DROP TABLE IF EXISTS `ar_internal_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ar_internal_metadata` (
  `key` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archive_phpbb3_forums`
--

DROP TABLE IF EXISTS `archive_phpbb3_forums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_phpbb3_forums` (
  `forum_id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` mediumint unsigned NOT NULL DEFAULT '0',
  `left_id` mediumint unsigned NOT NULL DEFAULT '0',
  `right_id` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_parents` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `forum_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_desc` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `forum_desc_bitfield` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_desc_options` int unsigned NOT NULL DEFAULT '7',
  `forum_desc_uid` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_link` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_style` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_rules` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `forum_rules_link` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_rules_bitfield` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_rules_options` int unsigned NOT NULL DEFAULT '7',
  `forum_rules_uid` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_topics_per_page` tinyint NOT NULL DEFAULT '0',
  `forum_type` tinyint NOT NULL DEFAULT '0',
  `forum_status` tinyint NOT NULL DEFAULT '0',
  `forum_last_post_id` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_last_poster_id` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_last_post_subject` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_last_post_time` int unsigned NOT NULL DEFAULT '0',
  `forum_last_poster_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_last_poster_colour` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `forum_flags` tinyint NOT NULL DEFAULT '32',
  `display_on_index` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_indexing` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_icons` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_prune` tinyint unsigned NOT NULL DEFAULT '0',
  `prune_next` int unsigned NOT NULL DEFAULT '0',
  `prune_days` mediumint unsigned NOT NULL DEFAULT '0',
  `prune_viewed` mediumint unsigned NOT NULL DEFAULT '0',
  `prune_freq` mediumint unsigned NOT NULL DEFAULT '0',
  `display_subforum_list` tinyint unsigned NOT NULL DEFAULT '1',
  `forum_options` int unsigned NOT NULL DEFAULT '0',
  `forum_posts_approved` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_posts_unapproved` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_posts_softdeleted` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_topics_approved` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_topics_unapproved` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_topics_softdeleted` mediumint unsigned NOT NULL DEFAULT '0',
  `enable_shadow_prune` tinyint unsigned NOT NULL DEFAULT '0',
  `prune_shadow_days` mediumint unsigned NOT NULL DEFAULT '7',
  `prune_shadow_freq` mediumint unsigned NOT NULL DEFAULT '1',
  `prune_shadow_next` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`forum_id`),
  KEY `forum_lastpost_id` (`forum_last_post_id`),
  KEY `left_right_id` (`left_id`,`right_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archive_phpbb3_posts`
--

DROP TABLE IF EXISTS `archive_phpbb3_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_phpbb3_posts` (
  `post_id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `topic_id` mediumint unsigned NOT NULL DEFAULT '0',
  `forum_id` mediumint unsigned NOT NULL DEFAULT '0',
  `poster_id` mediumint unsigned NOT NULL DEFAULT '0',
  `icon_id` mediumint unsigned NOT NULL DEFAULT '0',
  `poster_ip` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_time` int unsigned NOT NULL DEFAULT '0',
  `post_reported` tinyint unsigned NOT NULL DEFAULT '0',
  `enable_bbcode` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_smilies` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_magic_url` tinyint unsigned NOT NULL DEFAULT '1',
  `enable_sig` tinyint unsigned NOT NULL DEFAULT '1',
  `post_username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_subject` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '',
  `post_text` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `post_checksum` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_attachment` tinyint unsigned NOT NULL DEFAULT '0',
  `bbcode_bitfield` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `bbcode_uid` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_postcount` tinyint unsigned NOT NULL DEFAULT '1',
  `post_edit_time` int unsigned NOT NULL DEFAULT '0',
  `post_edit_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_edit_user` mediumint unsigned NOT NULL DEFAULT '0',
  `post_edit_count` smallint unsigned NOT NULL DEFAULT '0',
  `post_edit_locked` tinyint unsigned NOT NULL DEFAULT '0',
  `post_visibility` tinyint NOT NULL DEFAULT '0',
  `post_delete_time` int unsigned NOT NULL DEFAULT '0',
  `post_delete_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `post_delete_user` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`post_id`),
  KEY `forum_id` (`forum_id`),
  KEY `post_username` (`post_username`),
  KEY `post_visibility` (`post_visibility`),
  KEY `poster_id` (`poster_id`),
  KEY `poster_ip` (`poster_ip`),
  KEY `tid_post_time` (`topic_id`,`post_time`),
  KEY `topic_id` (`topic_id`),
  FULLTEXT KEY `post_subject` (`post_subject`),
  FULLTEXT KEY `post_content` (`post_text`,`post_subject`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archive_phpbb3_topics`
--

DROP TABLE IF EXISTS `archive_phpbb3_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_phpbb3_topics` (
  `topic_id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `forum_id` mediumint unsigned NOT NULL DEFAULT '0',
  `icon_id` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_attachment` tinyint unsigned NOT NULL DEFAULT '0',
  `topic_reported` tinyint unsigned NOT NULL DEFAULT '0',
  `topic_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '',
  `topic_poster` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_time` int unsigned NOT NULL DEFAULT '0',
  `topic_time_limit` int unsigned NOT NULL DEFAULT '0',
  `topic_views` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_status` tinyint NOT NULL DEFAULT '0',
  `topic_type` tinyint NOT NULL DEFAULT '0',
  `topic_first_post_id` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_first_poster_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '',
  `topic_first_poster_colour` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `topic_last_post_id` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_last_poster_id` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_last_poster_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `topic_last_poster_colour` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `topic_last_post_subject` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `topic_last_post_time` int unsigned NOT NULL DEFAULT '0',
  `topic_last_view_time` int unsigned NOT NULL DEFAULT '0',
  `topic_moved_id` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_bumped` tinyint unsigned NOT NULL DEFAULT '0',
  `topic_bumper` mediumint unsigned NOT NULL DEFAULT '0',
  `poll_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `poll_start` int unsigned NOT NULL DEFAULT '0',
  `poll_length` int unsigned NOT NULL DEFAULT '0',
  `poll_max_options` tinyint NOT NULL DEFAULT '1',
  `poll_last_vote` int unsigned NOT NULL DEFAULT '0',
  `poll_vote_change` tinyint unsigned NOT NULL DEFAULT '0',
  `poll_vote_name` tinyint(1) NOT NULL DEFAULT '0',
  `topic_visibility` tinyint NOT NULL DEFAULT '0',
  `topic_delete_time` int unsigned NOT NULL DEFAULT '0',
  `topic_delete_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `topic_delete_user` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_posts_approved` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_posts_unapproved` mediumint unsigned NOT NULL DEFAULT '0',
  `topic_posts_softdeleted` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`topic_id`),
  KEY `fid_time_moved` (`forum_id`,`topic_last_post_time`,`topic_moved_id`),
  KEY `forum_id_type` (`forum_id`,`topic_type`),
  KEY `forum_vis_last` (`forum_id`,`topic_visibility`,`topic_last_post_id`),
  KEY `forum_id` (`forum_id`),
  KEY `last_post_time` (`topic_last_post_time`),
  KEY `topic_visibility` (`topic_visibility`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archive_phpbb3_users`
--

DROP TABLE IF EXISTS `archive_phpbb3_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_phpbb3_users` (
  `user_id` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `user_type` tinyint NOT NULL DEFAULT '0',
  `group_id` mediumint unsigned NOT NULL DEFAULT '3',
  `user_permissions` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `user_perm_from` mediumint unsigned NOT NULL DEFAULT '0',
  `user_ip` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_regdate` int unsigned NOT NULL DEFAULT '0',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `username_clean` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_passchg` int unsigned NOT NULL DEFAULT '0',
  `user_email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_email_hash` bigint NOT NULL DEFAULT '0',
  `user_birthday` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_lastvisit` int unsigned NOT NULL DEFAULT '0',
  `user_lastmark` int unsigned NOT NULL DEFAULT '0',
  `user_lastpost_time` int unsigned NOT NULL DEFAULT '0',
  `user_lastpage` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_last_confirm_key` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_last_search` int unsigned NOT NULL DEFAULT '0',
  `user_warnings` tinyint NOT NULL DEFAULT '0',
  `user_last_warning` int unsigned NOT NULL DEFAULT '0',
  `user_login_attempts` tinyint NOT NULL DEFAULT '0',
  `user_inactive_reason` tinyint NOT NULL DEFAULT '0',
  `user_inactive_time` int unsigned NOT NULL DEFAULT '0',
  `user_posts` mediumint unsigned NOT NULL DEFAULT '0',
  `user_lang` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_timezone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_dateformat` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'd M Y H:i',
  `user_style` mediumint unsigned NOT NULL DEFAULT '0',
  `user_rank` mediumint unsigned NOT NULL DEFAULT '0',
  `user_colour` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_new_privmsg` int NOT NULL DEFAULT '0',
  `user_unread_privmsg` int NOT NULL DEFAULT '0',
  `user_last_privmsg` int unsigned NOT NULL DEFAULT '0',
  `user_message_rules` tinyint unsigned NOT NULL DEFAULT '0',
  `user_full_folder` int NOT NULL DEFAULT '-3',
  `user_emailtime` int unsigned NOT NULL DEFAULT '0',
  `user_topic_show_days` smallint unsigned NOT NULL DEFAULT '0',
  `user_topic_sortby_type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 't',
  `user_topic_sortby_dir` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'd',
  `user_post_show_days` smallint unsigned NOT NULL DEFAULT '0',
  `user_post_sortby_type` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 't',
  `user_post_sortby_dir` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'a',
  `user_notify` tinyint unsigned NOT NULL DEFAULT '0',
  `user_notify_pm` tinyint unsigned NOT NULL DEFAULT '1',
  `user_notify_type` tinyint NOT NULL DEFAULT '0',
  `user_allow_pm` tinyint unsigned NOT NULL DEFAULT '1',
  `user_allow_viewonline` tinyint unsigned NOT NULL DEFAULT '1',
  `user_allow_viewemail` tinyint unsigned NOT NULL DEFAULT '1',
  `user_allow_massemail` tinyint unsigned NOT NULL DEFAULT '1',
  `user_options` int unsigned NOT NULL DEFAULT '230271',
  `user_avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_avatar_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_avatar_width` smallint unsigned NOT NULL DEFAULT '0',
  `user_avatar_height` smallint unsigned NOT NULL DEFAULT '0',
  `user_sig` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `user_sig_bbcode_uid` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_sig_bbcode_bitfield` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_jabber` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_actkey` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_newpasswd` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_form_salt` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `user_new` tinyint unsigned NOT NULL DEFAULT '1',
  `user_reminded` tinyint NOT NULL DEFAULT '0',
  `user_reminded_time` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_clean` (`username_clean`),
  KEY `user_birthday` (`user_birthday`),
  KEY `user_email_hash` (`user_email_hash`),
  KEY `user_type` (`user_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archive_registrations`
--

DROP TABLE IF EXISTS `archive_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive_registrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `competitionId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `personId` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `countryId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `birthYear` smallint unsigned NOT NULL DEFAULT '0',
  `birthMonth` tinyint unsigned NOT NULL DEFAULT '0',
  `birthDay` tinyint unsigned NOT NULL DEFAULT '0',
  `email` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `guests_old` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `guests` int NOT NULL DEFAULT '0',
  `accepted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_registrations_on_competitionId_and_user_id` (`competitionId`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assignments`
--

DROP TABLE IF EXISTS `assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `registration_id` bigint DEFAULT NULL,
  `registration_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `schedule_activity_id` bigint DEFAULT NULL,
  `station_number` int DEFAULT NULL,
  `assignment_code` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_assignments_on_registration_id_and_registration_type` (`registration_id`,`registration_type`),
  KEY `index_assignments_on_schedule_activity_id` (`schedule_activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=79843055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `best_ever_rank`
--

DROP TABLE IF EXISTS `best_ever_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `best_ever_rank` (
  `person_id` varchar(10) NOT NULL,
  `best_ever_rank` json NOT NULL,
  `last_modified` datetime NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bookmarked_competitions`
--

DROP TABLE IF EXISTS `bookmarked_competitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmarked_competitions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_bookmarked_competitions_on_competition_id` (`competition_id`),
  KEY `index_bookmarked_competitions_on_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=312951 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cached_results`
--

DROP TABLE IF EXISTS `cached_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cached_results` (
  `key_params` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` json DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`key_params`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `championships`
--

DROP TABLE IF EXISTS `championships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `championships` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `championship_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_championships_on_competition_id_and_championship_type` (`competition_id`,`championship_type`),
  KEY `index_championships_on_championship_type` (`championship_type`)
) ENGINE=InnoDB AUTO_INCREMENT=935 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_delegates`
--

DROP TABLE IF EXISTS `competition_delegates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_delegates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delegate_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `receive_registration_emails` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_competition_delegates_on_competition_id_and_delegate_id` (`competition_id`,`delegate_id`),
  KEY `index_competition_delegates_on_competition_id` (`competition_id`),
  KEY `index_competition_delegates_on_delegate_id` (`delegate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_events`
--

DROP TABLE IF EXISTS `competition_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fee_lowest_denomination` int NOT NULL DEFAULT '0',
  `qualification` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_competition_events_on_competition_id_and_event_id` (`competition_id`,`event_id`),
  KEY `fk_rails_ba6cfdafb1` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_media`
--

DROP TABLE IF EXISTS `competition_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_media` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `media_type` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `text` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `uri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `submitter_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `submitter_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `submitter_email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `submitted_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `decided_at` timestamp NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11970 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_organizers`
--

DROP TABLE IF EXISTS `competition_organizers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_organizers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `organizer_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `receive_registration_emails` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_competition_organizers_on_competition_id_and_organizer_id` (`competition_id`,`organizer_id`),
  KEY `index_competition_organizers_on_competition_id` (`competition_id`),
  KEY `index_competition_organizers_on_organizer_id` (`organizer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44417 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_payment_integrations`
--

DROP TABLE IF EXISTS `competition_payment_integrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_payment_integrations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `connected_account_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `connected_account_id` bigint NOT NULL,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_competition_payment_integrations_on_competition_id` (`competition_id`),
  KEY `index_competition_payment_integrations_on_connected_account` (`connected_account_type`,`connected_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_series`
--

DROP TABLE IF EXISTS `competition_series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_series` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `wcif_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `short_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=370 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_tabs`
--

DROP TABLE IF EXISTS `competition_tabs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_tabs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `display_order` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_competition_tabs_on_display_order_and_competition_id` (`display_order`,`competition_id`),
  KEY `index_competition_tabs_on_competition_id` (`competition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63307 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competition_venues`
--

DROP TABLE IF EXISTS `competition_venues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competition_venues` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `wcif_id` int NOT NULL,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `latitude_microdegrees` int NOT NULL,
  `longitude_microdegrees` int NOT NULL,
  `timezone_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `country_iso2` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_competition_venues_on_competition_id_and_wcif_id` (`competition_id`,`wcif_id`),
  KEY `index_competition_venues_on_competition_id` (`competition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12531 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `competitions`
--

DROP TABLE IF EXISTS `competitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `competitions` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `city_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `country_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `information` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `venue` varchar(240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `venue_address` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `venue_details` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `external_website` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cell_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `show_at_all` tinyint(1) NOT NULL DEFAULT '0',
  `latitude` int DEFAULT NULL,
  `longitude` int DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `registration_open` datetime DEFAULT NULL,
  `registration_close` datetime DEFAULT NULL,
  `use_wca_registration` tinyint(1) NOT NULL DEFAULT '1',
  `guests_enabled` tinyint(1) NOT NULL DEFAULT '1',
  `results_posted_at` datetime DEFAULT NULL,
  `results_nag_sent_at` datetime DEFAULT NULL,
  `generate_website` tinyint(1) DEFAULT NULL,
  `announced_at` datetime DEFAULT NULL,
  `base_entry_fee_lowest_denomination` int DEFAULT NULL,
  `currency_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'USD',
  `connected_stripe_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `enable_donations` tinyint(1) DEFAULT NULL,
  `competitor_limit_enabled` tinyint(1) DEFAULT NULL,
  `competitor_limit` int DEFAULT NULL,
  `competitor_limit_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `extra_registration_requirements` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `on_the_spot_registration` tinyint(1) DEFAULT NULL,
  `on_the_spot_entry_fee_lowest_denomination` int DEFAULT NULL,
  `refund_policy_percent` int DEFAULT NULL,
  `refund_policy_limit_date` datetime DEFAULT NULL,
  `guests_entry_fee_lowest_denomination` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `results_submitted_at` datetime DEFAULT NULL,
  `early_puzzle_submission` tinyint(1) DEFAULT NULL,
  `early_puzzle_submission_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `qualification_results` tinyint(1) DEFAULT NULL,
  `qualification_results_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `name_reason` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `external_registration_page` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmed_at` datetime DEFAULT NULL,
  `event_restrictions` tinyint(1) DEFAULT NULL,
  `event_restrictions_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `registration_reminder_sent_at` datetime DEFAULT NULL,
  `announced_by` int DEFAULT NULL,
  `results_posted_by` int DEFAULT NULL,
  `main_event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cancelled_at` datetime DEFAULT NULL,
  `cancelled_by` int DEFAULT NULL,
  `waiting_list_deadline_date` datetime DEFAULT NULL,
  `event_change_deadline_date` datetime DEFAULT NULL,
  `guest_entry_status` int NOT NULL DEFAULT '0',
  `allow_registration_edits` tinyint(1) NOT NULL DEFAULT '0',
  `competition_series_id` int DEFAULT NULL,
  `use_wca_live_for_scoretaking` tinyint(1) NOT NULL DEFAULT '0',
  `allow_registration_without_qualification` tinyint(1) DEFAULT '0',
  `guests_per_registration_limit` int DEFAULT NULL,
  `events_per_registration_limit` int DEFAULT NULL,
  `force_comment_in_registration` tinyint(1) DEFAULT NULL,
  `posting_by` int DEFAULT NULL,
  `forbid_newcomers` tinyint(1) NOT NULL DEFAULT '0',
  `forbid_newcomers_reason` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `competitor_can_cancel` int NOT NULL DEFAULT '0',
  `newcomer_month_reserved_spots` int DEFAULT NULL,
  `auto_close_threshold` int DEFAULT NULL,
  `auto_accept_registrations` tinyint(1) NOT NULL DEFAULT '0',
  `auto_accept_disable_threshold` int DEFAULT NULL,
  `auto_accept_preference` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_competitions_on_cancelled_at` (`cancelled_at`),
  KEY `index_Competitions_on_countryId` (`country_id`),
  KEY `index_competitions_on_end_date` (`end_date`),
  KEY `index_competitions_on_start_date` (`start_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `concise_average_results`
--

DROP TABLE IF EXISTS `concise_average_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `concise_average_results` (
  `id` int NOT NULL DEFAULT '0',
  `average` int NOT NULL DEFAULT '0',
  `value_and_id` bigint DEFAULT NULL,
  `person_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `country_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `continent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `year` smallint unsigned NOT NULL DEFAULT '0',
  `month` smallint unsigned NOT NULL DEFAULT '0',
  `day` smallint unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `concise_single_results`
--

DROP TABLE IF EXISTS `concise_single_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `concise_single_results` (
  `id` int NOT NULL DEFAULT '0',
  `best` int NOT NULL DEFAULT '0',
  `value_and_id` bigint DEFAULT NULL,
  `person_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `country_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `continent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `year` smallint unsigned NOT NULL DEFAULT '0',
  `month` smallint unsigned NOT NULL DEFAULT '0',
  `day` smallint unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `connected_paypal_accounts`
--

DROP TABLE IF EXISTS `connected_paypal_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `connected_paypal_accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paypal_merchant_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `permissions_granted` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `consent_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `connected_stripe_accounts`
--

DROP TABLE IF EXISTS `connected_stripe_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `connected_stripe_accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `continents`
--

DROP TABLE IF EXISTS `continents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `continents` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `record_name` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `latitude` int NOT NULL DEFAULT '0',
  `longitude` int NOT NULL DEFAULT '0',
  `zoom` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `continent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `iso2` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `iso2` (`iso2`),
  KEY `fk_continents` (`continent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country_band_details`
--

DROP TABLE IF EXISTS `country_band_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_band_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `due_amount_per_competitor_us_cents` int NOT NULL,
  `due_percent_registration_fee` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country_bands`
--

DROP TABLE IF EXISTS `country_bands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_bands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `iso2` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_country_bands_on_iso2` (`iso2`),
  KEY `index_country_bands_on_number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cronjob_statistics`
--

DROP TABLE IF EXISTS `cronjob_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cronjob_statistics` (
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `run_start` datetime DEFAULT NULL,
  `run_end` datetime DEFAULT NULL,
  `last_run_successful` tinyint(1) NOT NULL DEFAULT '0',
  `last_error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `successful_run_start` datetime DEFAULT NULL,
  `enqueued_at` datetime DEFAULT NULL,
  `recently_rejected` int NOT NULL DEFAULT '0',
  `recently_errored` int NOT NULL DEFAULT '0',
  `times_completed` int NOT NULL DEFAULT '0',
  `average_runtime` bigint DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `delegate_reports`
--

DROP TABLE IF EXISTS `delegate_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `delegate_reports` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `version` int NOT NULL DEFAULT '0',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `equipment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `venue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `organization` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `schedule_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `incidents` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `remarks` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `discussion_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `posted_by_user_id` int DEFAULT NULL,
  `posted_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `nag_sent_at` datetime DEFAULT NULL,
  `wrc_feedback_requested` tinyint(1) NOT NULL DEFAULT '0',
  `wrc_incidents` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wic_feedback_requested` tinyint(1) NOT NULL DEFAULT '0',
  `wic_incidents` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wrc_primary_user_id` int DEFAULT NULL,
  `wrc_secondary_user_id` int DEFAULT NULL,
  `reminder_sent_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_delegate_reports_on_competition_id` (`competition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18176 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `duplicate_checker_job_runs`
--

DROP TABLE IF EXISTS `duplicate_checker_job_runs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `duplicate_checker_job_runs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `run_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_duplicate_checker_job_runs_on_competition_id` (`competition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eligible_country_iso2s_for_championship`
--

DROP TABLE IF EXISTS `eligible_country_iso2s_for_championship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `eligible_country_iso2s_for_championship` (
  `championship_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `eligible_country_iso2` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `index_eligible_iso2s_for_championship_on_type_and_country_iso2` (`championship_type`,`eligible_country_iso2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(54) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rank` int NOT NULL DEFAULT '0',
  `format` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formats`
--

DROP TABLE IF EXISTS `formats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `formats` (
  `id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `sort_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sort_by_second` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `expected_solve_count` int NOT NULL,
  `trim_fastest_n` int NOT NULL,
  `trim_slowest_n` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_metadata_board`
--

DROP TABLE IF EXISTS `groups_metadata_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups_metadata_board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_metadata_councils`
--

DROP TABLE IF EXISTS `groups_metadata_councils`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups_metadata_councils` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `friendly_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_metadata_delegate_regions`
--

DROP TABLE IF EXISTS `groups_metadata_delegate_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups_metadata_delegate_regions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `friendly_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_metadata_teams_committees`
--

DROP TABLE IF EXISTS `groups_metadata_teams_committees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups_metadata_teams_committees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `friendly_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `preferred_contact_mode` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'email',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_metadata_translators`
--

DROP TABLE IF EXISTS `groups_metadata_translators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups_metadata_translators` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `locale` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inbox_persons`
--

DROP TABLE IF EXISTS `inbox_persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbox_persons` (
  `id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `wca_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country_iso2` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '',
  `dob` date NOT NULL,
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `index_InboxPersons_on_competitionId_and_id` (`competition_id`,`id`),
  KEY `InboxPersons_fk_country` (`country_iso2`),
  KEY `InboxPersons_name` (`name`),
  KEY `InboxPersons_id` (`wca_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inbox_results`
--

DROP TABLE IF EXISTS `inbox_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbox_results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `person_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pos` smallint NOT NULL DEFAULT '0',
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `round_type_id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `round_id` int DEFAULT NULL,
  `format_id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `value1` int NOT NULL DEFAULT '0',
  `value2` int NOT NULL DEFAULT '0',
  `value3` int NOT NULL DEFAULT '0',
  `value4` int NOT NULL DEFAULT '0',
  `value5` int NOT NULL DEFAULT '0',
  `best` int NOT NULL DEFAULT '0',
  `average` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `InboxResults_fk_tournament` (`competition_id`),
  KEY `InboxResults_fk_event` (`event_id`),
  KEY `InboxResults_fk_format` (`format_id`),
  KEY `index_inbox_results_on_round_id` (`round_id`),
  KEY `InboxResults_fk_round` (`round_type_id`),
  CONSTRAINT `fk_rails_dbd033d884` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inbox_scramble_sets`
--

DROP TABLE IF EXISTS `inbox_scramble_sets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbox_scramble_sets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `round_number` int NOT NULL,
  `scramble_set_number` int NOT NULL,
  `ordered_index` int NOT NULL,
  `matched_round_id` int DEFAULT NULL,
  `external_upload_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_on_competition_id_event_id_round_number_063e808d5f` (`competition_id`,`event_id`,`round_number`),
  KEY `index_inbox_scramble_sets_on_competition_id` (`competition_id`),
  KEY `fk_rails_7a55abc2f3` (`event_id`),
  KEY `index_inbox_scramble_sets_on_external_upload_id` (`external_upload_id`),
  KEY `index_inbox_scramble_sets_on_matched_round_id` (`matched_round_id`),
  CONSTRAINT `fk_rails_5a4aaa6474` FOREIGN KEY (`external_upload_id`) REFERENCES `scramble_file_uploads` (`id`),
  CONSTRAINT `fk_rails_7a55abc2f3` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  CONSTRAINT `fk_rails_bf0a894336` FOREIGN KEY (`matched_round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inbox_scrambles`
--

DROP TABLE IF EXISTS `inbox_scrambles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `inbox_scrambles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `inbox_scramble_set_id` bigint NOT NULL,
  `is_extra` tinyint(1) NOT NULL DEFAULT '0',
  `scramble_number` int NOT NULL,
  `ordered_index` int NOT NULL,
  `scramble_string` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_on_inbox_scramble_set_id_scramble_number_is_ext_bd518aa059` (`inbox_scramble_set_id`,`scramble_number`,`is_extra`),
  KEY `index_inbox_scrambles_on_inbox_scramble_set_id` (`inbox_scramble_set_id`),
  CONSTRAINT `fk_rails_e2951ac80e` FOREIGN KEY (`inbox_scramble_set_id`) REFERENCES `inbox_scramble_sets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `incident_competitions`
--

DROP TABLE IF EXISTS `incident_competitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `incident_competitions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `incident_id` bigint NOT NULL,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comments` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_incident_competitions_on_incident_id_and_competition_id` (`incident_id`,`competition_id`),
  KEY `index_incident_competitions_on_incident_id` (`incident_id`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `incident_tags`
--

DROP TABLE IF EXISTS `incident_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `incident_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `incident_id` bigint NOT NULL,
  `tag` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_incident_tags_on_incident_id_and_tag` (`incident_id`,`tag`),
  KEY `index_incident_tags_on_incident_id` (`incident_id`),
  KEY `index_incident_tags_on_tag` (`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `incidents`
--

DROP TABLE IF EXISTS `incidents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `private_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `private_wrc_decision` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `public_summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `resolved_at` datetime DEFAULT NULL,
  `digest_worthy` tinyint(1) DEFAULT '0',
  `digest_sent_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jwt_denylist`
--

DROP TABLE IF EXISTS `jwt_denylist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_denylist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `jti` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `exp` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_jwt_denylist_on_jti` (`jti`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_attempt_history_entries`
--

DROP TABLE IF EXISTS `live_attempt_history_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `live_attempt_history_entries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entered_at` datetime(6) NOT NULL,
  `entered_by` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `live_attempt_id` bigint NOT NULL,
  `result` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_live_attempt_history_entries_on_live_attempt_id` (`live_attempt_id`),
  CONSTRAINT `fk_rails_e9c979ea85` FOREIGN KEY (`live_attempt_id`) REFERENCES `live_attempts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_attempts`
--

DROP TABLE IF EXISTS `live_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `live_attempts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `result` int NOT NULL,
  `attempt_number` int NOT NULL,
  `live_result_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_live_attempts_on_live_result_id` (`live_result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_results`
--

DROP TABLE IF EXISTS `live_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `live_results` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `registration_id` bigint NOT NULL,
  `round_id` bigint NOT NULL,
  `last_attempt_entered_at` datetime(6) NOT NULL,
  `ranking` int DEFAULT NULL,
  `best` int NOT NULL,
  `average` int NOT NULL,
  `single_record_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `average_record_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `advancing` tinyint(1) NOT NULL DEFAULT '0',
  `advancing_questionable` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_live_results_on_registration_id_and_round_id` (`registration_id`,`round_id`),
  KEY `index_live_results_on_registration_id` (`registration_id`),
  KEY `index_live_results_on_round_id` (`round_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `latitude_microdegrees` int DEFAULT NULL,
  `longitude_microdegrees` int DEFAULT NULL,
  `notification_radius_km` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_access_grants`
--

DROP TABLE IF EXISTS `oauth_access_grants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_access_grants` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resource_owner_id` int NOT NULL,
  `application_id` int NOT NULL,
  `token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `expires_in` int NOT NULL,
  `redirect_uri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `revoked_at` datetime DEFAULT NULL,
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_oauth_access_grants_on_token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_access_tokens`
--

DROP TABLE IF EXISTS `oauth_access_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_access_tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resource_owner_id` int DEFAULT NULL,
  `application_id` int DEFAULT NULL,
  `token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `refresh_token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expires_in` int DEFAULT NULL,
  `revoked_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_oauth_access_tokens_on_token` (`token`),
  UNIQUE KEY `index_oauth_access_tokens_on_refresh_token` (`refresh_token`),
  KEY `index_oauth_access_tokens_on_resource_owner_id` (`resource_owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_applications`
--

DROP TABLE IF EXISTS `oauth_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_applications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `uid` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `redirect_uri` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `owner_id` int DEFAULT NULL,
  `owner_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dangerously_allow_any_redirect_uri` tinyint(1) NOT NULL DEFAULT '0',
  `confidential` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_oauth_applications_on_uid` (`uid`),
  KEY `index_oauth_applications_on_owner_id_and_owner_type` (`owner_id`,`owner_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_openid_requests`
--

DROP TABLE IF EXISTS `oauth_openid_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth_openid_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `access_grant_id` int NOT NULL,
  `nonce` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_oauth_openid_requests_on_access_grant_id` (`access_grant_id`),
  CONSTRAINT `fk_rails_77114b3b09` FOREIGN KEY (`access_grant_id`) REFERENCES `oauth_access_grants` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_intents`
--

DROP TABLE IF EXISTS `payment_intents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_intents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `holder_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `holder_id` bigint DEFAULT NULL,
  `payment_record_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_record_id` bigint DEFAULT NULL,
  `client_secret` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `error_details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `initiated_by_id` int DEFAULT NULL,
  `confirmed_at` datetime DEFAULT NULL,
  `confirmation_source_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmation_source_id` bigint DEFAULT NULL,
  `canceled_at` datetime DEFAULT NULL,
  `cancellation_source_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cancellation_source_id` bigint DEFAULT NULL,
  `wca_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_stripe_payment_intents_on_canceled_by` (`cancellation_source_type`,`cancellation_source_id`),
  KEY `index_stripe_payment_intents_on_confirmed_by` (`confirmation_source_type`,`confirmation_source_id`),
  KEY `index_payment_intents_on_holder` (`holder_type`,`holder_id`),
  KEY `fk_rails_2dbc373c0c` (`initiated_by_id`),
  KEY `index_payment_intents_on_payment_record_id` (`payment_record_id`),
  CONSTRAINT `fk_rails_d5d856472c` FOREIGN KEY (`initiated_by_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paypal_records`
--

DROP TABLE IF EXISTS `paypal_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `paypal_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paypal_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paypal_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parameters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount_paypal_denomination` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `currency_code` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paypal_record_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_record_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `merchant_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at_remote` datetime(6) DEFAULT NULL,
  `updated_at_remote` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_paypal_records_on_parent_record_id` (`parent_record_id`),
  CONSTRAINT `fk_rails_566d8fdf3e` FOREIGN KEY (`parent_record_id`) REFERENCES `paypal_records` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wca_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `sub_id` tinyint NOT NULL DEFAULT '1',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '',
  `dob` date DEFAULT NULL,
  `comments` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `incorrect_wca_id_claim_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_Persons_on_wca_id_and_subId` (`wca_id`,`sub_id`),
  KEY `Persons_fk_country` (`country_id`),
  KEY `Persons_name` (`name`),
  KEY `index_persons_on_wca_id` (`wca_id`),
  FULLTEXT KEY `index_persons_on_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=269777 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `poll_options`
--

DROP TABLE IF EXISTS `poll_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `poll_options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `poll_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `poll_id` (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `polls`
--

DROP TABLE IF EXISTS `polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `polls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `multiple` tinyint(1) NOT NULL,
  `deadline` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `confirmed_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_tags`
--

DROP TABLE IF EXISTS `post_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `tag` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_post_tags_on_post_id_and_tag` (`post_id`,`tag`),
  KEY `index_post_tags_on_post_id` (`post_id`),
  KEY `index_post_tags_on_tag` (`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=22607 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `slug` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `sticky` tinyint(1) NOT NULL DEFAULT '0',
  `author_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `show_on_homepage` tinyint(1) NOT NULL DEFAULT '1',
  `unstick_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_posts_on_slug` (`slug`),
  KEY `index_posts_on_world_readable_and_created_at` (`created_at`),
  KEY `idx_show_wr_sticky_created_at` (`show_on_homepage`,`sticky`,`created_at`),
  KEY `index_posts_on_world_readable_and_sticky_and_created_at` (`sticky`,`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=14211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `potential_duplicate_persons`
--

DROP TABLE IF EXISTS `potential_duplicate_persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `potential_duplicate_persons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duplicate_checker_job_run_id` bigint NOT NULL,
  `original_user_id` int NOT NULL,
  `duplicate_person_id` int NOT NULL,
  `name_matching_algorithm` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_on_duplicate_checker_job_run_id_12b05a3796` (`duplicate_checker_job_run_id`),
  KEY `index_potential_duplicate_persons_on_duplicate_person_id` (`duplicate_person_id`),
  KEY `index_potential_duplicate_persons_on_original_user_id` (`original_user_id`),
  CONSTRAINT `fk_rails_212c95c62c` FOREIGN KEY (`duplicate_checker_job_run_id`) REFERENCES `duplicate_checker_job_runs` (`id`),
  CONSTRAINT `fk_rails_7ae0e67c87` FOREIGN KEY (`duplicate_person_id`) REFERENCES `persons` (`id`),
  CONSTRAINT `fk_rails_9d6e9b137a` FOREIGN KEY (`original_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preferred_formats`
--

DROP TABLE IF EXISTS `preferred_formats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `preferred_formats` (
  `event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `format_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ranking` int NOT NULL,
  UNIQUE KEY `index_preferred_formats_on_event_id_and_format_id` (`event_id`,`format_id`),
  KEY `fk_rails_c3e0098ed3` (`format_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ranks_average`
--

DROP TABLE IF EXISTS `ranks_average`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranks_average` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `best` int NOT NULL DEFAULT '0',
  `world_rank` int NOT NULL DEFAULT '0',
  `continent_rank` int NOT NULL DEFAULT '0',
  `country_rank` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_events` (`event_id`),
  KEY `fk_persons` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=808426 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ranks_single`
--

DROP TABLE IF EXISTS `ranks_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ranks_single` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `best` int NOT NULL DEFAULT '0',
  `world_rank` int NOT NULL DEFAULT '0',
  `continent_rank` int NOT NULL DEFAULT '0',
  `country_rank` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_events` (`event_id`),
  KEY `fk_persons` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=933527 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `record_evolution`
--

DROP TABLE IF EXISTS `record_evolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `record_evolution` (
  `event_id` varchar(6) NOT NULL,
  `evolution` json NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regional_organizations`
--

DROP TABLE IF EXISTS `regional_organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `regional_organizations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `website` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `directors_and_officers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `area_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `past_and_current_activities` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `future_plans` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `extra_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `index_regional_organizations_on_country` (`country`),
  KEY `index_regional_organizations_on_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regional_records_lookup`
--

DROP TABLE IF EXISTS `regional_records_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `regional_records_lookup` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `result_id` int NOT NULL,
  `country_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `competition_end_date` date NOT NULL,
  `best` int NOT NULL DEFAULT '0',
  `average` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_on_eventId_countryId_average_competitionEndDate_b424c59953` (`event_id`,`country_id`,`average`,`competition_end_date`),
  KEY `idx_on_eventId_countryId_best_competitionEndDate_4e01b1ae38` (`event_id`,`country_id`,`best`,`competition_end_date`),
  KEY `index_regional_records_lookup_on_resultId` (`result_id`),
  CONSTRAINT `fk_rails_7928b195ce` FOREIGN KEY (`result_id`) REFERENCES `results` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_competition_events`
--

DROP TABLE IF EXISTS `registration_competition_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_competition_events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registration_id` int DEFAULT NULL,
  `competition_event_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_registration_competition_events_on_reg_id_and_comp_event_id` (`registration_id`,`competition_event_id`),
  KEY `index_registration_competition_events_on_competition_event_id` (`competition_event_id`),
  KEY `index_reg_events_reg_id_comp_event_id` (`registration_id`,`competition_event_id`),
  KEY `index_registration_competition_events_on_registration_id` (`registration_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6660263 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_history_changes`
--

DROP TABLE IF EXISTS `registration_history_changes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_history_changes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `registration_history_entry_id` bigint DEFAULT NULL,
  `key` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_on_registration_history_entry_id_e1e6e4bed0` (`registration_history_entry_id`),
  CONSTRAINT `fk_rails_c0c65a2353` FOREIGN KEY (`registration_history_entry_id`) REFERENCES `registration_history_entries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_history_entries`
--

DROP TABLE IF EXISTS `registration_history_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_history_entries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `registration_id` bigint DEFAULT NULL,
  `actor_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `actor_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `action` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_registration_history_entries_on_registration_id` (`registration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registration_payments`
--

DROP TABLE IF EXISTS `registration_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration_payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registration_id` int DEFAULT NULL,
  `amount_lowest_denomination` int DEFAULT NULL,
  `currency_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receipt_id` bigint DEFAULT NULL,
  `receipt_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stripe_charge_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `refunded_registration_payment_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_registration_payments_on_receipt` (`receipt_type`,`receipt_id`),
  KEY `idx_reg_payments_on_refunded_registration_payment_id` (`refunded_registration_payment_id`),
  KEY `index_registration_payments_on_registration_id` (`registration_id`),
  KEY `index_registration_payments_on_stripe_charge_id` (`stripe_charge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registrations`
--

DROP TABLE IF EXISTS `registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `registrant_id` int NOT NULL,
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `guests` int NOT NULL DEFAULT '0',
  `accepted_at` datetime DEFAULT NULL,
  `accepted_by` int DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` int DEFAULT NULL,
  `roles` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `is_competing` tinyint(1) DEFAULT '1',
  `administrative_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `competing_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending',
  `registered_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_registrations_on_competition_id_and_registrant_id` (`competition_id`,`registrant_id`),
  UNIQUE KEY `index_registrations_on_competition_id_and_user_id` (`competition_id`,`user_id`),
  KEY `index_registrations_on_competition_id_and_competing_status` (`competition_id`,`competing_status`),
  KEY `index_registrations_on_competition_id` (`competition_id`),
  KEY `index_registrations_on_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1195504 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `result_attempts`
--

DROP TABLE IF EXISTS `result_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `result_attempts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  `attempt_number` int NOT NULL,
  `result_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_result_attempts_on_result_id_and_attempt_number` (`result_id`,`attempt_number`),
  KEY `index_result_attempts_on_result_id` (`result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83493 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `results` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pos` smallint NOT NULL DEFAULT '0',
  `person_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `person_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `round_type_id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `round_id` int DEFAULT NULL,
  `format_id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `value1` int NOT NULL DEFAULT '0',
  `value2` int NOT NULL DEFAULT '0',
  `value3` int NOT NULL DEFAULT '0',
  `value4` int NOT NULL DEFAULT '0',
  `value5` int NOT NULL DEFAULT '0',
  `best` int NOT NULL DEFAULT '0',
  `average` int NOT NULL DEFAULT '0',
  `regional_single_record` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `regional_average_record` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_Results_on_competitionId_and_updated_at` (`competition_id`,`updated_at`),
  KEY `Results_fk_tournament` (`competition_id`),
  KEY `_tmp_index_Results_on_countryId` (`country_id`),
  KEY `Results_eventAndAverage` (`event_id`,`average`),
  KEY `Results_eventAndBest` (`event_id`,`best`),
  KEY `Results_regionalAverageRecordCheckSpeedup` (`event_id`,`competition_id`,`round_type_id`,`country_id`,`average`),
  KEY `Results_regionalSingleRecordCheckSpeedup` (`event_id`,`competition_id`,`round_type_id`,`country_id`,`best`),
  KEY `index_Results_on_eventId_and_value1` (`event_id`,`value1`),
  KEY `index_Results_on_eventId_and_value2` (`event_id`,`value2`),
  KEY `index_Results_on_eventId_and_value3` (`event_id`,`value3`),
  KEY `index_Results_on_eventId_and_value4` (`event_id`,`value4`),
  KEY `index_Results_on_eventId_and_value5` (`event_id`,`value5`),
  KEY `Results_fk_event` (`event_id`),
  KEY `Results_fk_format` (`format_id`),
  KEY `Results_fk_competitor` (`person_id`),
  KEY `index_Results_on_regionalAverageRecord_and_eventId` (`regional_average_record`,`event_id`),
  KEY `index_Results_on_regionalSingleRecord_and_eventId` (`regional_single_record`,`event_id`),
  KEY `index_results_on_round_id` (`round_id`),
  KEY `Results_fk_round` (`round_type_id`),
  CONSTRAINT `fk_rails_8293cc5c42` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7368643 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci PACK_KEYS=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_metadata_banned_competitors`
--

DROP TABLE IF EXISTS `roles_metadata_banned_competitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_metadata_banned_competitors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ban_reason` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scope` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_metadata_councils`
--

DROP TABLE IF EXISTS `roles_metadata_councils`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_metadata_councils` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_metadata_delegate_regions`
--

DROP TABLE IF EXISTS `roles_metadata_delegate_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_metadata_delegate_regions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `location` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_delegated` date DEFAULT NULL,
  `last_delegated` date DEFAULT NULL,
  `total_delegated` int DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1340 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_metadata_officers`
--

DROP TABLE IF EXISTS `roles_metadata_officers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_metadata_officers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles_metadata_teams_committees`
--

DROP TABLE IF EXISTS `roles_metadata_teams_committees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_metadata_teams_committees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=646 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `round_types`
--

DROP TABLE IF EXISTS `round_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `round_types` (
  `id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `rank` int NOT NULL DEFAULT '0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `cell_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `final` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rounds`
--

DROP TABLE IF EXISTS `rounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `rounds` (
  `id` int NOT NULL AUTO_INCREMENT,
  `competition_event_id` int NOT NULL,
  `format_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `number` int NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `time_limit` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `cutoff` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `advancement_condition` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `scramble_set_count` int NOT NULL DEFAULT '1',
  `round_results` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `total_number_of_rounds` int NOT NULL,
  `old_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_rounds_on_competition_event_id_and_number` (`competition_event_id`,`number`)
) ENGINE=InnoDB AUTO_INCREMENT=906497 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sanity_check_categories`
--

DROP TABLE IF EXISTS `sanity_check_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanity_check_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_to` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_sanity_check_categories_on_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sanity_check_exclusions`
--

DROP TABLE IF EXISTS `sanity_check_exclusions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanity_check_exclusions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sanity_check_id` bigint NOT NULL,
  `exclusion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `fk_rails_c9112973d2` (`sanity_check_id`),
  CONSTRAINT `fk_rails_c9112973d2` FOREIGN KEY (`sanity_check_id`) REFERENCES `sanity_checks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sanity_checks`
--

DROP TABLE IF EXISTS `sanity_checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanity_checks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sanity_check_category_id` bigint NOT NULL,
  `topic` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `query` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_sanity_checks_on_topic` (`topic`),
  KEY `fk_rails_fddad5fbb5` (`sanity_check_category_id`),
  CONSTRAINT `fk_rails_fddad5fbb5` FOREIGN KEY (`sanity_check_category_id`) REFERENCES `sanity_check_categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule_activities`
--

DROP TABLE IF EXISTS `schedule_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule_activities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `venue_room_id` bigint NOT NULL,
  `parent_activity_id` bigint DEFAULT NULL,
  `wcif_id` int NOT NULL,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `activity_code` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `round_id` int DEFAULT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `scramble_set_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_schedule_activities_on_venue_room_id_and_wcif_id` (`venue_room_id`,`wcif_id`),
  KEY `index_schedule_activities_on_parent_activity_id` (`parent_activity_id`),
  KEY `index_schedule_activities_on_round_id` (`round_id`),
  KEY `index_schedule_activities_on_venue_room_id` (`venue_room_id`),
  CONSTRAINT `fk_rails_576aac0864` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`),
  CONSTRAINT `fk_rails_7045722310` FOREIGN KEY (`venue_room_id`) REFERENCES `venue_rooms` (`id`),
  CONSTRAINT `fk_rails_999dc22d7e` FOREIGN KEY (`parent_activity_id`) REFERENCES `schedule_activities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=794762 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schema_migrations`
--

DROP TABLE IF EXISTS `schema_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `schema_migrations` (
  `version` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scramble_file_uploads`
--

DROP TABLE IF EXISTS `scramble_file_uploads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `scramble_file_uploads` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uploaded_by` int NOT NULL,
  `uploaded_at` timestamp NOT NULL,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `original_filename` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scramble_program` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `generated_at` timestamp NULL DEFAULT NULL,
  `raw_wcif` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_scramble_file_uploads_on_competition_id` (`competition_id`),
  KEY `index_scramble_file_uploads_on_uploaded_by` (`uploaded_by`),
  CONSTRAINT `fk_rails_9bb0595360` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrambles`
--

DROP TABLE IF EXISTS `scrambles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `scrambles` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `event_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `round_type_id` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `round_id` int DEFAULT NULL,
  `group_id` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_extra` tinyint(1) NOT NULL,
  `scramble_num` int NOT NULL,
  `scramble` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `competitionId` (`competition_id`,`event_id`),
  KEY `index_scrambles_on_round_id` (`round_id`),
  CONSTRAINT `fk_rails_ef5833e6ef` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4915558 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `server_settings`
--

DROP TABLE IF EXISTS `server_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `server_settings` (
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `index_server_settings_on_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `starburst_announcement_views`
--

DROP TABLE IF EXISTS `starburst_announcement_views`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `starburst_announcement_views` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `announcement_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `starburst_announcement_view_index` (`user_id`,`announcement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `starburst_announcements`
--

DROP TABLE IF EXISTS `starburst_announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `starburst_announcements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `start_delivering_at` datetime DEFAULT NULL,
  `stop_delivering_at` datetime DEFAULT NULL,
  `limit_to_users` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `category` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics` (
  `path` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `explanation` varchar(200) DEFAULT NULL,
  `display_mode` varchar(20) NOT NULL,
  `group_name` varchar(20) NOT NULL,
  `statistics` json NOT NULL,
  `last_modified` datetime NOT NULL,
  `export_date` datetime NOT NULL,
  PRIMARY KEY (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `statistics_control`
--

DROP TABLE IF EXISTS `statistics_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics_control` (
  `path` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `message` varchar(200) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `completed_at` datetime DEFAULT NULL,
  PRIMARY KEY (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stripe_records`
--

DROP TABLE IF EXISTS `stripe_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `stripe_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `stripe_record_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stripe_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parameters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount_stripe_denomination` int DEFAULT NULL,
  `currency_code` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stripe_status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `account_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `parent_record_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rails_6ad225b020` (`parent_record_id`),
  KEY `index_stripe_records_on_stripe_id` (`stripe_id`),
  KEY `index_stripe_records_on_stripe_status` (`stripe_status`),
  CONSTRAINT `fk_rails_090c4f8f4b` FOREIGN KEY (`parent_record_id`) REFERENCES `stripe_records` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stripe_webhook_events`
--

DROP TABLE IF EXISTS `stripe_webhook_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `stripe_webhook_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `stripe_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `event_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at_remote` datetime NOT NULL,
  `handled` tinyint(1) NOT NULL DEFAULT '0',
  `stripe_record_id` bigint DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_stripe_webhook_events_on_stripe_record_id` (`stripe_record_id`),
  CONSTRAINT `fk_rails_98d26cebf2` FOREIGN KEY (`stripe_record_id`) REFERENCES `stripe_records` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sum_of_ranks`
--

DROP TABLE IF EXISTS `sum_of_ranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sum_of_ranks` (
  `region_rank` int DEFAULT NULL,
  `region` varchar(100) NOT NULL,
  `region_type` varchar(20) NOT NULL,
  `wca_id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `country_iso2` varchar(100) DEFAULT NULL,
  `result_type` varchar(7) NOT NULL,
  `overall` int DEFAULT NULL,
  `events` json NOT NULL,
  PRIMARY KEY (`region`,`region_type`,`wca_id`,`result_type`),
  KEY `sor_where_clause_idx` (`region`,`region_type`,`result_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sum_of_ranks_meta`
--

DROP TABLE IF EXISTS `sum_of_ranks_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sum_of_ranks_meta` (
  `result_type` varchar(7) NOT NULL,
  `region_type` varchar(20) NOT NULL,
  `region` varchar(100) NOT NULL,
  `total_size` int NOT NULL,
  PRIMARY KEY (`result_type`,`region`,`region_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_comments`
--

DROP TABLE IF EXISTS `ticket_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint NOT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `acting_user_id` int NOT NULL,
  `acting_stakeholder_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_ticket_comments_on_acting_stakeholder_id` (`acting_stakeholder_id`),
  KEY `index_ticket_comments_on_acting_user_id` (`acting_user_id`),
  KEY `index_ticket_comments_on_ticket_id` (`ticket_id`),
  CONSTRAINT `fk_rails_6f1502b630` FOREIGN KEY (`acting_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_rails_b96043ab8e` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`id`),
  CONSTRAINT `fk_rails_e44ff75e43` FOREIGN KEY (`acting_stakeholder_id`) REFERENCES `ticket_stakeholders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_log_changes`
--

DROP TABLE IF EXISTS `ticket_log_changes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_log_changes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_log_id` bigint NOT NULL,
  `field_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `field_value` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_ticket_log_changes_on_ticket_log_id` (`ticket_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_logs`
--

DROP TABLE IF EXISTS `ticket_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint NOT NULL,
  `action_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `acting_user_id` int NOT NULL,
  `acting_stakeholder_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_ticket_logs_on_acting_stakeholder_id` (`acting_stakeholder_id`),
  KEY `index_ticket_logs_on_acting_user_id` (`acting_user_id`),
  KEY `index_ticket_logs_on_ticket_id` (`ticket_id`),
  CONSTRAINT `fk_rails_48612199f8` FOREIGN KEY (`acting_stakeholder_id`) REFERENCES `ticket_stakeholders` (`id`),
  CONSTRAINT `fk_rails_8d14128397` FOREIGN KEY (`acting_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ticket_stakeholders`
--

DROP TABLE IF EXISTS `ticket_stakeholders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_stakeholders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint NOT NULL,
  `stakeholder_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `stakeholder_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `stakeholder_role` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_ticket_stakeholders_on_stakeholder` (`stakeholder_type`,`stakeholder_id`),
  KEY `index_ticket_stakeholders_on_ticket_id` (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `metadata_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `metadata_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_tickets_on_metadata` (`metadata_type`,`metadata_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tickets_competition_result`
--

DROP TABLE IF EXISTS `tickets_competition_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets_competition_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `delegate_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_tickets_competition_result_on_competition_id` (`competition_id`),
  CONSTRAINT `fk_rails_b292c1317c` FOREIGN KEY (`competition_id`) REFERENCES `competitions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tickets_edit_person`
--

DROP TABLE IF EXISTS `tickets_edit_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets_edit_person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `wca_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tickets_edit_person_fields`
--

DROP TABLE IF EXISTS `tickets_edit_person_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets_edit_person_fields` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tickets_edit_person_id` bigint NOT NULL,
  `field_name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_tickets_edit_person_fields_on_tickets_edit_person_id` (`tickets_edit_person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `uploaded_jsons`
--

DROP TABLE IF EXISTS `uploaded_jsons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `uploaded_jsons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `json_str` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `index_uploaded_jsons_on_competition_id` (`competition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_avatars`
--

DROP TABLE IF EXISTS `user_avatars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_avatars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `filename` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thumbnail_crop_x` int DEFAULT NULL,
  `thumbnail_crop_y` int DEFAULT NULL,
  `thumbnail_crop_w` int DEFAULT NULL,
  `thumbnail_crop_h` int DEFAULT NULL,
  `backend` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `approved_by` int DEFAULT NULL,
  `approved_at` datetime DEFAULT NULL,
  `revoked_by` int DEFAULT NULL,
  `revoked_at` datetime DEFAULT NULL,
  `revocation_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_avatars_on_status` (`status`),
  KEY `index_user_avatars_on_user_id` (`user_id`),
  CONSTRAINT `fk_rails_e4b8c035c3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80689 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_groups`
--

DROP TABLE IF EXISTS `user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `group_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_group_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  `is_hidden` tinyint(1) NOT NULL,
  `metadata_id` bigint DEFAULT NULL,
  `metadata_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_groups_on_parent_group_id` (`parent_group_id`),
  CONSTRAINT `fk_rails_d1e69a9bb2` FOREIGN KEY (`parent_group_id`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_preferred_events`
--

DROP TABLE IF EXISTS `user_preferred_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_preferred_events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `event_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_preferred_events_on_user_id_and_event_id` (`user_id`,`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=417575 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `group_id` bigint NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `metadata_id` bigint DEFAULT NULL,
  `metadata_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_roles_on_group_id` (`group_id`),
  KEY `index_user_roles_on_user_id` (`user_id`),
  CONSTRAINT `fk_rails_318345354e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_rails_66cd95bfa8` FOREIGN KEY (`group_id`) REFERENCES `user_groups` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2355 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `encrypted_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `reset_password_token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int NOT NULL DEFAULT '0',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_sign_in_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmation_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmed_at` datetime DEFAULT NULL,
  `confirmation_sent_at` datetime DEFAULT NULL,
  `unconfirmed_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `wca_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `current_avatar_id` bigint DEFAULT NULL,
  `pending_avatar_id` bigint DEFAULT NULL,
  `unconfirmed_wca_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delegate_id_to_handle_wca_id_claim` int DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country_iso2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `results_notifications_enabled` tinyint(1) DEFAULT '0',
  `preferred_locale` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `competition_notifications_enabled` tinyint(1) DEFAULT NULL,
  `receive_delegate_reports` tinyint(1) NOT NULL DEFAULT '0',
  `delegate_reports_region_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delegate_reports_region_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dummy_account` tinyint(1) NOT NULL DEFAULT '0',
  `consumed_timestep` int DEFAULT NULL,
  `otp_required_for_login` tinyint(1) DEFAULT '0',
  `otp_backup_codes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `session_validity_token` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cookies_acknowledged` tinyint(1) NOT NULL DEFAULT '0',
  `registration_notifications_enabled` tinyint(1) DEFAULT '0',
  `otp_secret` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_users_on_email` (`email`),
  UNIQUE KEY `index_users_on_reset_password_token` (`reset_password_token`),
  UNIQUE KEY `index_users_on_wca_id` (`wca_id`),
  KEY `index_users_on_delegate_id_to_handle_wca_id_claim` (`delegate_id_to_handle_wca_id_claim`),
  KEY `index_users_on_delegate_reports_region` (`delegate_reports_region_type`,`delegate_reports_region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=496845 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venue_rooms`
--

DROP TABLE IF EXISTS `venue_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `venue_rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `competition_venue_id` bigint NOT NULL,
  `wcif_id` int NOT NULL,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `color` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_venue_rooms_on_competition_venue_id_and_wcif_id` (`competition_venue_id`,`wcif_id`),
  KEY `index_venue_rooms_on_competition_venue_id` (`competition_venue_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15693 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vote_options`
--

DROP TABLE IF EXISTS `vote_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `vote_options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vote_id` int NOT NULL,
  `poll_option_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `votes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `poll_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_votes_on_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `waiting_lists`
--

DROP TABLE IF EXISTS `waiting_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `waiting_lists` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `holder_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `holder_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entries` json DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_waiting_lists_on_holder` (`holder_type`,`holder_id`)
) ENGINE=InnoDB AUTO_INCREMENT=976 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wcif_extensions`
--

DROP TABLE IF EXISTS `wcif_extensions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wcif_extensions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `extendable_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `extendable_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `extension_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `spec_url` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_wcif_extensions_on_extendable_type_and_extendable_id` (`extendable_type`,`extendable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wfc_dues_redirects`
--

DROP TABLE IF EXISTS `wfc_dues_redirects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wfc_dues_redirects` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `redirect_source_id` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `redirect_source_type` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `redirect_to_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_wfc_dues_redirects_on_redirect_to_id` (`redirect_to_id`),
  CONSTRAINT `fk_rails_48c7f075e6` FOREIGN KEY (`redirect_to_id`) REFERENCES `wfc_xero_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wfc_xero_users`
--

DROP TABLE IF EXISTS `wfc_xero_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wfc_xero_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_combined_invoice` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-08-10 17:45:27
