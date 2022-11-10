rd /s /q classes
mkdir classes

call scalac -classpath classes/ -d classes/ src/hw3/ndarray/NDArray.scala
call scalac -classpath classes/ -d classes/ src/hw3/ndarray/Vector.scala
call scalac -classpath classes/ -d classes/ src/hw3/ndarray/Matrix.scala
call scalac -classpath classes/ -d classes/ src/hw3/ndarray/StackedArray.scala

call scalac -classpath classes/ -d classes/ src/hw3/matmulengine/MatmulEngine.scala
call scalac -classpath classes/ -d classes/ src/hw3/matmulengine/Naive.scala
call scalac -classpath classes/ -d classes/ src/hw3/matmulengine/SystolicEngine.scala

call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/NNModule.scala
call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Linear.scala
call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/SystolicLinear.scala
call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/ReLU.scala
call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Softmax.scala
call scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Sequential.scala

call scalac -classpath classes/ -d classes/ src/hw3/ImageReader.scala
call scalac -classpath classes/ -d classes/ src/hw3/SimpleNet.scala

call scalac -classpath classes/ -d classes/ test/hw3/Test.scala