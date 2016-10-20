#!/bin/bash

for ((i=1;i<=15;i++))
do
  echo "*** Para $i:"
  ls /proc/irq/$i
done
