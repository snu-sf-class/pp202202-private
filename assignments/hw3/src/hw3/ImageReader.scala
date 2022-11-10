package hw3

import hw3.ndarray.{Vector, Matrix}

import java.io.File
import javax.imageio.ImageIO

object ImageReader {
  def read(path: String): Matrix = {
    val image = ImageIO.read(new File(path))
    val raster = image.getData()
    val width = image.getWidth
    val height = image.getHeight

    Matrix(
      (0 until height).map(y => Vector(
        (0 until width).map(x => raster.getSample(x, y, 0) / 255.0f))))
  }
}
