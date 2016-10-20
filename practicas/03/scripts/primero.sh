#!/bin/bash

cat /var/log/{kern.log,auth.log,syslog} | grep "session opened" | grep "systemd" | grep -v "lightdm" | cut -d" " -f11
