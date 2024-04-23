




* export
````shell
mysqldump --column-statistics=0 -uroot -h 127.0.0.1 -p remotemeeting > remotemeeting.sql
````

* import
````shell
mysql -uroot -p111111 -h127.0.0.1 remotemeeting_tenant < remotemeeting.sql

````

* wait_timeout (28800, 8시간)
```text
SHOW VARIABLES LIKE 'wait_timeout';
```