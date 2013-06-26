# schema
 
# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
    id integer NOT NULL DEFAULT nextval('task_id_seq'),
    label varchar(255)
);

CREATE TABLE account (
    id         text NOT NULL PRIMARY KEY,
    email      text NOT NULL UNIQUE,
    password   text NOT NULL,
    name       text NOT NULL,
    permission text NOT NULL
);

# --- !Downs
 
DROP TABLE task;
DROP SEQUENCE task_id_seq;
DROP TABLE account;

