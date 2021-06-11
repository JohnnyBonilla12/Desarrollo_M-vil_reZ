-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-06-2021 a las 04:06:46
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `re-z`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta`
--

CREATE TABLE `receta` (
  `id` int(11) NOT NULL,
  `imagen` varchar(300) NOT NULL,
  `titulo` varchar(60) NOT NULL,
  `dificultad` int(1) NOT NULL,
  `porciones` int(1) NOT NULL,
  `duracion` varchar(10) NOT NULL,
  `ingredientes` varchar(150) NOT NULL,
  `preparacion` varchar(150) NOT NULL,
  `usuario_email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `receta`
--

INSERT INTO `receta` (`id`, `imagen`, `titulo`, `dificultad`, `porciones`, `duracion`, `ingredientes`, `preparacion`, `usuario_email`) VALUES
(1, 'http://localhost/android/re-z/mis_recetas/uploads/image_60b4f3f0e4837.png', 'Papas a la Francesa', 1, 1, '30 min', '*Papas\r\n*Aceite\r\n', '*Pelar papas.\r\n*Congelar papas.\r\n*Freír papas.', 'johnny@gmail.com'),
(2, 'http://localhost/android/re-z/mis_recetas/uploads/image_60b4f3f6ec162.png', 'Huevo Frito', 2, 1, '10 min', '*Huevo.\r\n*Aceite.\r\n*Sal.', '*Romper huevos.\r\n*Calentar aceite.\r\n*Revolver huevos.\r\n*Freír huevos.', 'johnny@gmail.com'),
(62, 'http://localhost/android/re-z/mis_recetas/uploads/image_60b491c317573.png', 'Tacos al Pastor', 2, 0, '40 min.', 'Carne, Tortillas', 'Cocinar Carne, Poner en Tortillas', 'johnny@gmail.com'),
(66, 'http://localhost/android/re-z/mis_recetas/uploads/image_60b4fdfd93e2a.png', 'Pescado Frito', 3, 0, '50 min.', 'Pescado, Aceite', 'Calentar Aceite, Freir Pescado', 'johnny@gmail.com'),
(68, 'http://localhost/android/re-z/mis_recetas/uploads/image_60c044f23ae78.png', 'Pollo Asado', 3, 0, '60 min.', 'Pollo, Leña.', 'Limpiar pollo, Asar pollo.', 'johnny@gmail.com'),
(115, 'http://localhost/android/re-z/mis_recetas/uploads/image_60c2c0268964b.png', 'Camarones Enchipotlados', 2, 1, '60 min.', 'Camarones, chile chipotle.', 'Hervir camarones, preparar salsa y juntar.', 'jeffrey@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(5) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `email`, `password`) VALUES
(1, 'correo@correo.com', 'password1'),
(2, 'manu@correo.mx', 'password'),
(3, 'johnny@gmail.com', 'admin'),
(4, 'prueba@gmail.com', 'admin'),
(5, 'jeffrey@gmail.com', 'admin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `receta`
--
ALTER TABLE `receta`
  ADD PRIMARY KEY (`id`,`usuario_email`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`,`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `receta`
--
ALTER TABLE `receta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=117;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
