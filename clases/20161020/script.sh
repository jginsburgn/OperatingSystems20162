#!/bin/bash

cat /etc/passwd | grep -v false | grep -v nologin | grep -v sync | cut -d":" -f1 > /tmp/temporal
cat /tmp/temporal | while read LINEA
do
  echo "usuario: $LINEA caracterfinal,"
done

while read LI
do
  echo "otra: $LI forma,"
done < /tmp/temporal

if [ $1 -gt 4 ]
then
  echo $1 mayor a 4
elif [ `grep habibi /etc/passwd` ]
then
  echo "si hay cuanta moy"
else
  echo "no hay moy"
fi
