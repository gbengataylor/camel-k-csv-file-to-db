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
CREATE USER debezium WITH PASSWORD 'dbz' REPLICATION LOGIN;
CREATE USER debezium WITH PASSWORD 'dbz' SUPERUSER LOGIN;
/*  CREATE ROLE debezium1 with <SOMEROLE>;  
 GRANT debezium1 to <Someuser>;
 */



CREATE TABLE outbox_events (
	event_id SERIAL PRIMARY KEY,
	aggregate_id varchar(255),
	aggregate_type varchar(255),
	event_type varchar(255),
	payload jsonb,
	payload_string varchar(50000)
);
/*
INSERT INTO  outbox_events (aggregate_id, aggregate_type, event_type, payload_string, payload) values ( 'creditcard', 'creditcard',  'transaction', '84,-0.481375994326719,1.00340670082876,0.90618366899059,-0.423863928247347,0.760670627923092,0.377627418532502,0.587373158048792,0.133884480438326,-0.207870789641016,-0.240405330868221,-1.4532614362208,-0.526595859321807,0.0738445671686201,0.0873495585049685,1.2305762628008,0.311246138780342,-0.620259311525045,-0.329208342285684,0.187643004419645,0.0823029907311874,-0.277507154421431,-0.752696358424248,-0.145992422065669,-1.35351451619785,-0.0955157965052921,0.175811028239865,0.14422836072096,0.0996827431157687,4.99,0' , ' { "strData" : " 84,-0.481375994326719,1.00340670082876,0.90618366899059,-0.423863928247347,0.760670627923092,0.377627418532502,0.587373158048792,0.133884480438326,-0.207870789641016,-0.240405330868221,-1.4532614362208,-0.526595859321807,0.0738445671686201,0.0873495585049685,1.2305762628008,0.311246138780342,-0.620259311525045,-0.329208342285684,0.187643004419645,0.0823029907311874,-0.277507154421431,-0.752696358424248,-0.145992422065669,-1.35351451619785,-0.0955157965052921,0.175811028239865,0.14422836072096,0.0996827431157687,4.99,0 "} '  );

select * from outbox_events;
drop table outbox_events;
*/