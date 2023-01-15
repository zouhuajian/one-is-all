#!/bin/bash
source ~/.bashrc

#待检测的HDFS目录
data_file_1=/csoss/traces/PRO
data_file_2=/csoss/traces/PE
data_file_3=/csoss/traces/SHT4PE
data_file_4=/csoss/traces/TE
data_file_5=/csoss/traces/DEV
#将待检测的目录(可以为多个)加载至数组中
array_check=($data_file_1 $data_file_2 $data_file_3 $data_file_4 $data_file_5)

expire_days=7
# 当前时间戳
today_timestamp=$(date -d "$(date +"%Y-%m-%d %H:%M")" +%s)

#Func: 删除指定时间之前的过期，7天
removeOutDate(){
        /usr/local/service/hadoop/bin/hadoop fs -ls $1 > ~/csoss/temp.txt
        cat ~/csoss/temp.txt | while read quanxian temp user group size day hour filepath
        do
            current_file_time="$day $hour"
            current_file_timestamp=$(date -d "$current_file_time" +%s)
            if [ $(($today_timestamp-$current_file_timestamp)) -ge $(($expire_days*3600*24)) ];then
                echo "$(date +'%Y-%m-%d %H:%M:%S') $filepath"
                /usr/local/service/hadoop/bin/hadoop fs -rm -r -skipTrash $filepath > /dev/null 2>&1
            fi
        done
}

#Func: 执行删除
execute(){
        echo -e "\n\n"
        echo "$(date +'%Y-%m-%d %H:%M:%S') start to remove outdate files in hdfs"
        echo "$(date +'%Y-%m-%d %H:%M:%S') today is: $(date +"%Y-%m-%d %H:%M:%S")"

        for i in ${array_check[@]}
        do
            echo "$(date +'%Y-%m-%d %H:%M:%S') processing filepath: $i"
            removeOutDate $i
            echo -e "\n"
        done

        echo "$(date +'%Y-%m-%d %H:%M:%S') remove outdate files in hdfs finished"
        echo -e "\n\n"
}

# 开始执行
execute