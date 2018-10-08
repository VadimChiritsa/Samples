@echo off

start cmd /k CALL java -jar selenium-server-standalone-3.0.1.jar -role hub

start cmd /k CALL java -jar selenium-server-standalone-3.0.1.jar -role node  -hub http://localhost:4444/grid/register

exit