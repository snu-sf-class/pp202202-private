rd /s /q classes
mkdir classes
call scalac -classpath classes/ -d classes/ src/hw2/Main.scala
call scalac -classpath classes/ -d classes/ test/hw2/Test.scala