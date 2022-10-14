#!/usr/bin/env bash

rm -rf classes
mkdir classes
scalac -classpath classes/ -d classes/ src/hw2/Data.scala
scalac -classpath classes/ -d classes/ src/hw2/Main.scala
scalac -classpath classes/ -d classes/ test/hw2/Test.scala
