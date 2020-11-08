CREATE TABLE Pet
(
  id   BIGINT,
  name VARCHAR(20),
  breed VARCHAR(20),
  species VARCHAR(20)
);

CREATE SEQUENCE hibernate_sequence
  START WITH 2
  INCREMENT BY 1;

INSERT INTO Pet (id, name, breed, species)
VALUES (1, 'Baabaa', 'Black', 'Sheep');