CREATE TABLE accidents_rules (
    id              SERIAL  PRIMARY KEY,
    accident_id     INT     NOT NULL REFERENCES accidents(id),
    rule_id         INT     NOT NULL REFERENCES rules(id)
)