#!/bin/sh

DASHES="";
for (( i = 0; i < $(tput cols); i++ )); do
    DASHES+="-";
done
THIS_DIR=$(dirname $0);

function line() {
    echo $DASHES;
}

function header() {
    echo "Jonathan Ginsburg and Jacobo Calderon © 2016.";
    echo "cec.sh, i.e. Compile Execute and Clean."
}

function compile() {
    echo "Compiling...";
    javac $THIS_DIR/Source\ Codes/server/*.java;
    echo "Finished."
}

function execute() {
    echo "Executing";
    line;
    cd $THIS_DIR/Source\ Codes/server;
    java Main $@;
    echo;
    line;
    echo "Finished.";
}

function clean() {
    echo "Cleaning...";
    rm *.class;
    echo "Finished.";
}
clear;
header;
compile;
execute $@;
clean;
echo "Enter to clear...";
read ui;
clear;
