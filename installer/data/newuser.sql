CREATE USER 'newuser'@'localhost' IDENTIFIED BY 'password';
GRANT INSERT ON locanda.* TO 'newuser'@'localhost';
GRANT DELETE ON locanda.* TO 'newuser'@'localhost';
GRANT SELECT ON locanda.* TO 'newuser'@'localhost';
GRANT UPDATE ON locanda.* TO 'newuser'@'localhost';


FLUSH PRIVILEGES;

