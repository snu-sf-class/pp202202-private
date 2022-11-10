#!/usr/bin/env bash

rm -rf classes
mkdir classes

scalac -classpath classes/ -d classes/ src/hw3/ndarray/NDArray.scala
scalac -classpath classes/ -d classes/ src/hw3/ndarray/Vector.scala
scalac -classpath classes/ -d classes/ src/hw3/ndarray/Matrix.scala
scalac -classpath classes/ -d classes/ src/hw3/ndarray/StackedArray.scala

scalac -classpath classes/ -d classes/ src/hw3/matmulengine/MatmulEngine.scala
scalac -classpath classes/ -d classes/ src/hw3/matmulengine/Naive.scala
scalac -classpath classes/ -d classes/ src/hw3/matmulengine/SystolicEngine.scala

scalac -classpath classes/ -d classes/ src/hw3/nnmodule/NNModule.scala
scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Linear.scala
scalac -classpath classes/ -d classes/ src/hw3/nnmodule/SystolicLinear.scala
scalac -classpath classes/ -d classes/ src/hw3/nnmodule/ReLU.scala
scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Softmax.scala
scalac -classpath classes/ -d classes/ src/hw3/nnmodule/Sequential.scala

scalac -classpath classes/ -d classes/ src/hw3/ImageReader.scala
scalac -classpath classes/ -d classes/ src/hw3/SimpleNet.scala

scalac -classpath classes/ -d classes/ test/hw3/Test.scala

