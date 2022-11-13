rd /s /q classes
mkdir classes

call scalac -classpath classes/ -d classes/ \
       src/hw3/ndarray/NDArray.scala \
       src/hw3/ndarray/Vector.scala \
       src/hw3/ndarray/Matrix.scala \
       src/hw3/ndarray/StackedArray.scala

call scalac -classpath classes/ -d classes/ \
       src/hw3/matmulengine/MatmulEngine.scala \
       src/hw3/matmulengine/NaiveEngine.scala \
       src/hw3/matmulengine/SystolicEngine.scala

call scalac -classpath classes/ -d classes/ \
       src/hw3/nnmodule/NNModule.scala \
       src/hw3/nnmodule/Linear.scala \
       src/hw3/nnmodule/SystolicLinear.scala \
       src/hw3/nnmodule/ReLU.scala \
       src/hw3/nnmodule/Softmax.scala \
       src/hw3/nnmodule/Sequential.scala \

call scalac -classpath classes/ -d classes/ src/hw3/ImageReader.scala
call scalac -classpath classes/ -d classes/ src/hw3/SimpleNet.scala

call scalac -classpath classes/ -d classes/ test/hw3/Test.scala