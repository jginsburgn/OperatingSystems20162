echo "Exporting PATH environment variable..."
export PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin
#Default set path:
#/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games

LIB_GEN_UNIX=/lib:/usr/lib:/usr/local/lib
LIB_FEDORA=/lib64:/lib:/usr/lib64:/usr/lib:/usr/local/lib64:/usr/local/lib
LIB_UBUNTU=/lib64:/lib:/usr/lib:/usr/local/lib:/usr/lib32
echo "For UNIX generic: $LIB_GEN_UNIX"
echo "For Fedora: $LIB_FEDORA"
echo "For Ubuntu: $LIB_UBUNTU"
echo -n "What is the current OS (to export LD_LIBRARY_PATH)? (genericunix,ubuntu,fedora): "
read OS
echo "Selected $OS."
echo -n "Exporting LD_LIBRARY_PATH environment variable"
case $OS in
	genericunix)
		echo " for UNIX Generic..."
		export LD_LIBRARY_PATH=$LIB_GEN_UNIX;
		;;
	ubuntu)
		echo " for Ubuntu..."
		export LD_LIBRARY_PATH=$LIB_UBUNTU;
		;;
	fedora)
		echo " for Fedora..."
		export LD_LIBRARY_PATH=$LIB_FEDORA;
		;;
esac
