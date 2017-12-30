#!/bin/bash
pid=`ps aux | grep 'match_api_start.sh'| grep -v 'grep' | awk '{print $2}'`
kill ${pid}