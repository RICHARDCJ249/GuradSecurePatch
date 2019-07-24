!#/system/bin/sh
if [#1 == 1]
then
    cosu 0 0 "service call bluetooth_manager 6"
elif [#1 == 2]
then 
    cosu 0 0 "service call bluetooth_manager 8"
elif [#1 == 3] 
then 
    mv /system/lib64/libWantJoinFireWall.so /system/lib64/libWantJoinFireWal.so 
elif [#1 == 4] 
then 
    mv /system/lib64/libWantJoinFireWal.so /system/lib64/libWantJoinFireWall.so 
fi