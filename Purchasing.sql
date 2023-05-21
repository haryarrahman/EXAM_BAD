-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 21, 2023 at 03:23 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Purchasing`
--

-- --------------------------------------------------------

--
-- Table structure for table `Product`
--

CREATE TABLE `Product` (
  `Product_ID` int(11) NOT NULL,
  `Product_Name` char(50) NOT NULL,
  `Stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Product`
--

INSERT INTO `Product` (`Product_ID`, `Product_Name`, `Stock`) VALUES
(1, 'AAAA', 100),
(2, 'BBBB', 100),
(3, 'CCCC', 50),
(4, 'DDDD', 100),
(5, 'EEEE', 75),
(6, 'Test', 12);

-- --------------------------------------------------------

--
-- Table structure for table `Purchase`
--

CREATE TABLE `Purchase` (
  `Purchase_ID` int(11) NOT NULL,
  `Purchase_Date` text NOT NULL,
  `Vendor_ID` int(11) NOT NULL,
  `Vendor_RefNo` char(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Purchase`
--

INSERT INTO `Purchase` (`Purchase_ID`, `Purchase_Date`, `Vendor_ID`, `Vendor_RefNo`) VALUES
(1, '12-12-2023', 1, '1231213');

-- --------------------------------------------------------

--
-- Table structure for table `Purchase_Detail`
--

CREATE TABLE `Purchase_Detail` (
  `Purchase_Detail_ID` int(11) NOT NULL,
  `Purchase_ID` int(11) NOT NULL,
  `Product_ID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Vendor`
--

CREATE TABLE `Vendor` (
  `Vendor_ID` int(11) NOT NULL,
  `Vendor_Name` char(150) NOT NULL,
  `Vendor_Add` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Vendor`
--

INSERT INTO `Vendor` (`Vendor_ID`, `Vendor_Name`, `Vendor_Add`) VALUES
(1, 'ABCD, PT', 'XYZ'),
(2, 'BCDE, CV', 'LMNO'),
(3, 'CDEF', 'IJKL'),
(4, 'DEFG', 'MNOP'),
(5, 'EFGH', 'QRST'),
(6, 'IJKL', 'MNBV');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Product`
--
ALTER TABLE `Product`
  ADD PRIMARY KEY (`Product_ID`);

--
-- Indexes for table `Purchase`
--
ALTER TABLE `Purchase`
  ADD PRIMARY KEY (`Purchase_ID`),
  ADD KEY `Vendor_ID` (`Vendor_ID`);

--
-- Indexes for table `Purchase_Detail`
--
ALTER TABLE `Purchase_Detail`
  ADD PRIMARY KEY (`Purchase_Detail_ID`),
  ADD KEY `Purchase_ID` (`Purchase_ID`),
  ADD KEY `Product_ID` (`Product_ID`);

--
-- Indexes for table `Vendor`
--
ALTER TABLE `Vendor`
  ADD PRIMARY KEY (`Vendor_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Product`
--
ALTER TABLE `Product`
  MODIFY `Product_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `Purchase`
--
ALTER TABLE `Purchase`
  MODIFY `Purchase_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Purchase_Detail`
--
ALTER TABLE `Purchase_Detail`
  MODIFY `Purchase_Detail_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Vendor`
--
ALTER TABLE `Vendor`
  MODIFY `Vendor_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Purchase`
--
ALTER TABLE `Purchase`
  ADD CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`Vendor_ID`) REFERENCES `Vendor` (`Vendor_ID`);

--
-- Constraints for table `Purchase_Detail`
--
ALTER TABLE `Purchase_Detail`
  ADD CONSTRAINT `purchase_detail_ibfk_1` FOREIGN KEY (`Purchase_ID`) REFERENCES `Purchase` (`Purchase_ID`),
  ADD CONSTRAINT `purchase_detail_ibfk_2` FOREIGN KEY (`Product_ID`) REFERENCES `Product` (`Product_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
