CREATE TABLE accidents (
  id        SERIAL  PRIMARY KEY,
  name      TEXT    NOT NULL,
  text      TEXT    NOT NULL,
  address   TEXT    NOT NULL,
  type_id   INT     NOT NULL REFERENCES types(id)
);