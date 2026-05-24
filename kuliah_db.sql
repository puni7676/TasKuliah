-- Database: kuliah_db
-- Versi sudah diperbaiki agar data jadwal dan tugas kosong untuk akun baru.
-- Setiap data jadwal/tugas disimpan berdasarkan username masing-masing.

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `kuliah_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `kuliah_db`;

DROP TABLE IF EXISTS `tugas`;
DROP TABLE IF EXISTS `jadwal`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `jadwal` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `mata_kuliah` varchar(100) DEFAULT NULL,
  `hari` varchar(20) DEFAULT NULL,
  `jam` varchar(20) DEFAULT NULL,
  `ruangan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tugas` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `nama_tugas` varchar(100) DEFAULT NULL,
  `mata_kuliah` varchar(100) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Akun default untuk login awal.
-- Data jadwal dan tugas sengaja tidak diisi supaya dashboard akun baru kosong.
INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', '123');

ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_jadwal_username` (`username`);

ALTER TABLE `tugas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_tugas_username` (`username`);

ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `jadwal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `tugas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;
