#! /bin/bash

while getopts ":u:p:s:t:" opt; do
  case $opt in
    u) mysql_username="$OPTARG"
    ;;
    p) mysql_password="$OPTARG"
    ;;
    s) source_db_name="$OPTARG"
    ;;
    t) target_db_name="$OPTARG"
    ;;
    \?) echo "Invalid option -$OPTARG" >&2
    ;;
  esac
done

if [ "$mysql_username" == "" ] || [ "$mysql_password" == "" ] || [ "$source_db_name" == "" ] || [ "$target_db_name" == "" ]
then
  echo "Usage: $0 -u<mysql_username> -p<mysql_password> -s<source_db_name> -t<target_db_name>"
  exit 1
fi

echo "source db: $source_db_name"
echo "target db: $target_db_name"

echo ">create sql: $source_db_name.sql"
mysqldump -u$mysql_username -p$mysql_password $source_db_name > $source_db_name.sql

echo ">create db: $target_db_name"
mysql -u$mysql_username -p$mysql_password<< eof
DROP DATABASE IF EXISTS $target_db_name;
CREATE DATABASE $target_db_name;
eof

echo ">copy data: $target"
mysql -u$mysql_username -p$mysql_password $target_db_name < $source_db_name.sql

#echo ">delete sql: $source_db_name.sql"
#rm -f $source_db_name.sql

echo ">>copy($source_db_name -> $target_db_name) completed."