-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2021 at 01:18 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `re-z`
--

-- --------------------------------------------------------

--
-- Table structure for table `comentarios`
--

CREATE TABLE `comentarios` (
  `id_comentario` int(100) NOT NULL,
  `id_receta` int(100) NOT NULL,
  `email` varchar(70) NOT NULL,
  `comentario` varchar(200) NOT NULL,
  `posted` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `comentarios`
--

INSERT INTO `comentarios` (`id_comentario`, `id_receta`, `email`, `comentario`, `posted`) VALUES
(3, 1, 'jeffrey@gmail.com', 'yesyesxyes', '2021-06-13'),
(4, 1, 'jeffrey@gmail.com', 'Comentario prueba', '2021-06-13'),
(5, 1, 'jeffrey@gmail.com', 'AAAAAAAAAAAAAAAAHHHHHHHHHH', '2021-06-13'),
(6, 1, 'jeffrey@gmail.com', 'Este es otro comentario', '2021-06-13'),
(7, 1, 'jeffrey@gmail.com', 'Pero que buena receta hombre', '2021-06-13'),
(8, 1, 'jeffrey@gmail.com', 'Las mejores papapikas', '2021-06-13'),
(9, 1, 'jeffrey@gmail.com', 'Mmmm... Papas', '2021-06-13'),
(10, 0, 'jeffrey@gmail.com', 'Son huevitos con catsun xd', '2021-06-13'),
(11, 0, 'jeffrey@gmail.com', 'Yesyesyes', '2021-06-13'),
(12, 2, 'jeffrey@gmail.com', 'asdfgrghjt', '2021-06-13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`id_comentario`),
  ADD KEY `id_recetafk1` (`id_receta`),
  ADD KEY `emailfk2` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comentarios`
--
ALTER TABLE `comentarios`
  MODIFY `id_comentario` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
