CREATE DATABASE smartpill;

CREATE USER 'smartpilladmin'@'localhost' IDENTIFIED BY 'PASSWORD';

mysql> USE smartpill;

mysql> GRANT ALL ON smartpill.* TO 'smartpilladmin'@'localhost';