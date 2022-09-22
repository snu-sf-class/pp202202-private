#!/usr/bin/env bash

rm -rf classes
mkdir classes
scalac -classpath classes/ -d classes/ src/hw1/Main.scala
scalac -classpath classes/ -d classes/ test/hw1/Test.scala
