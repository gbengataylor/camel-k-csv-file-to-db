CREATE TABLE cct (
    time        real,
    V1       float8,
    V2       float8,
  V3       float8,
  V4       float8,
  V5       float8,
  V6       float8,
  V7       float8,
  V8       float8,
  V9       float8,
  V10       float8,
  V11      float8,
  V12      float8,
  V13       float8,
  V14      float8,
     V15       float8,
     V16      float8,
     V17      float8,
     V18      float8,
     V19      float8,
     V20      float8,
     V21       float8,
   V22       float8,
     V23       float8,
     V24      float8,
     V25       float8,
     V26       float8,
       V27       float8,
     V28    float8,
   
    amount real,
class real
);

/* CREATE USER debezium WITHPASWORD "password" SUPERUSER; */
CREATE USER debezium WITH PASSWORD 'password' REPLICATION LOGIN;
/*  CREATE ROLE debezium1 with <SOMEROLE>;  
 GRANT debezium1 to <Someuser>;
 */
