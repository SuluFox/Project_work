-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 04, 2022 at 08:48 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `trytwojavafx`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_accounts`
--

CREATE TABLE `admin_accounts` (
  `admin_id` int(255) NOT NULL,
  `adminfirstname` varchar(255) NOT NULL,
  `adminlastname` varchar(255) NOT NULL,
  `adminusername` varchar(255) NOT NULL,
  `adminpassword` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_accounts`
--

INSERT INTO `admin_accounts` (`admin_id`, `adminfirstname`, `adminlastname`, `adminusername`, `adminpassword`) VALUES
(1, 'Newton', 'Sefa', 'newsefa', '123456789'),
(11, 'Esther', 'Amenu', 'eva', '123456789');

-- --------------------------------------------------------

--
-- Table structure for table `equipments`
--

CREATE TABLE `equipments` (
  `equipment_id` int(11) NOT NULL,
  `equipment_type` varchar(120) NOT NULL,
  `number_of_equipment` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `equipments`
--

INSERT INTO `equipments` (`equipment_id`, `equipment_type`, `number_of_equipment`) VALUES
(1, 'dam bell', 12),
(2, 'ball', 4),
(5, 'Treadmile', 4),
(6, 'tyres', 19),
(11, 'Spin Bike', 15),
(12, 'chest press plate ', 6),
(13, 'treadmill', 21),
(14, 'elliptical trainer', 14);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `number_of_product` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `number_of_product`) VALUES
(1, 'Protein Shake', 12),
(3, 'Muscle Max', 16),
(4, 'Booster Fox', 18),
(5, 'EverMax', 6);

-- --------------------------------------------------------

--
-- Table structure for table `trainers`
--

CREATE TABLE `trainers` (
  `trainer_id` int(100) NOT NULL,
  `trainer_firstname` varchar(255) NOT NULL,
  `trainer_lastname` varchar(255) NOT NULL,
  `trainer_phonenumber` int(10) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `trainers`
--

INSERT INTO `trainers` (`trainer_id`, `trainer_firstname`, `trainer_lastname`, `trainer_phonenumber`, `email`) VALUES
(1, 'Kwame', 'Mensah', 209875615, 'mensah45@gmail.com'),
(3, 'William', 'Blay', 123456789, 'blah23@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `usersdetails`
--

CREATE TABLE `usersdetails` (
  `user_id` int(255) NOT NULL,
  `First_Name` varchar(255) NOT NULL,
  `Last_Name` varchar(255) NOT NULL,
  `Age` int(100) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Date_of_birth` text NOT NULL,
  `Phone_Number` int(10) NOT NULL,
  `Joined_on` datetime NOT NULL DEFAULT current_timestamp(),
  `Email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `usersdetails`
--

INSERT INTO `usersdetails` (`user_id`, `First_Name`, `Last_Name`, `Age`, `Gender`, `Date_of_birth`, `Phone_Number`, `Joined_on`, `Email`) VALUES
(1, 'Obed', 'Gakpa', 23, 'Male', '1999-08-18', 203402795, '2022-08-19 22:01:14', 'ogakpa@yahoo.com '),
(2, 'Jeffrey', 'Owusu', 22, 'Male', '2000-03-12', 545745139, '2022-08-23 19:12:31', 'jeffreyowusu39@gmail.com'),
(4, 'Esther', 'Otu', 25, 'Male', '1997-12-08', 243434282, '2022-08-23 23:37:38', 'esther23@gmail.com'),
(5, 'Raymond', 'Kwakwe', 45, 'Male', '1996-02-27', 549618468, '2022-08-24 07:34:54', 'raymond23@gmail.com'),
(7, 'William', 'Fiifi', 26, 'Male', '1997-04-04', 208145962, '2022-08-24 09:14:34', 'fiifi45@gmail.com'),
(8, 'Ben', 'Ofori', 24, 'Male', '2002-08-09', 1223698512, '2022-08-24 11:39:02', 'ben90@gmail.com'),
(9, 'Jeffrey', 'Owusu', 22, 'Male', '2000-12-03', 545745836, '2022-08-24 11:43:16', 'jeff123@gmail.com'),
(11, 'Favor', 'Otuu', 24, 'Female', '2000-03-12', 249536778, '2022-08-26 01:05:20', 'favor360@gmail.com'),
(13, 'Alice', 'Mensah', 23, 'Female', '2008-09-18', 208120769, '2022-08-26 01:35:50', 'mensah09@gmail.com'),
(14, 'Clifford', 'Amenu', 25, 'Male', '2002-01-12', 550707598, '2022-08-26 01:38:40', 'clifamenu@gmail.com'),
(15, 'Abigail', 'Tutu', 21, 'Female', '2002-07-27', 554523691, '2022-08-26 22:48:22', 'tutu120@gmail.com'),
(16, 'Nelson', 'Deli', 45, 'Male', '1995-02-14', 123456789, '2022-08-26 22:55:09', 'deli23@gmail.com'),
(17, 'Caleb', 'Amenu', 22, 'Male', '2021-12-06', 123456789, '2022-08-26 23:01:01', 'calamenu18@gmail.com'),
(18, 'Mattew', 'Addo', 25, 'Male', '1997-01-11', 123456789, '2022-08-27 01:37:18', 'addo34@gmail.com'),
(19, 'Kowa', 'Mohammed', 23, 'Male', '1999-07-08', 550707504, '2022-08-27 18:04:16', 'kpwamuhammad7@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_accounts`
--
ALTER TABLE `admin_accounts`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `username` (`adminusername`);

--
-- Indexes for table `equipments`
--
ALTER TABLE `equipments`
  ADD PRIMARY KEY (`equipment_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `trainers`
--
ALTER TABLE `trainers`
  ADD PRIMARY KEY (`trainer_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `usersdetails`
--
ALTER TABLE `usersdetails`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_accounts`
--
ALTER TABLE `admin_accounts`
  MODIFY `admin_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `equipments`
--
ALTER TABLE `equipments`
  MODIFY `equipment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `trainers`
--
ALTER TABLE `trainers`
  MODIFY `trainer_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `usersdetails`
--
ALTER TABLE `usersdetails`
  MODIFY `user_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
