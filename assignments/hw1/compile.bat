rd /s /q classes
mkdir classes
call scalac -classpath classes/ -d classes/ src/hw1/Main.scala
call scalac -classpath classes/ -d classes/ test/hw1/Test.scala