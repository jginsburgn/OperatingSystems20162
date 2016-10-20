#!/bin/bash

if [ `find /lib -name "libGL.so"` ]
then
	echo "Librería de OpenGL en /lib"
elif [ `find /usr/lib -name "libGL.so"` ]
then
	echo "Librería de OpenGL en /usr/lib"
elif [ `find /usr/local/lib -name "libGL.so"` ]
then
	echo "Librería de OpenGL en /usr/local/lib"
else
	echo "No se encontró la librería de OpenGL"
fi
