CREATE DATABASE smartpill;

CREATE USER 'smartpilladmin'@'localhost' IDENTIFIED BY 'iastate13';

mysql> USE smartpill;

mysql> GRANT ALL ON smartpill.* TO 'smartpilladmin'@'localhost';