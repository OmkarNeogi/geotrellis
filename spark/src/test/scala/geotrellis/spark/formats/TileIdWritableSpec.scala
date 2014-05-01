package geotrellis.spark.formats
import geotrellis.spark.TestEnvironment
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.DataOutputStream
import java.io.DataInputStream
import org.apache.hadoop.io.Writable
import scala.reflect.ClassTag

trait SerializerTester {
  def testJavaSerialization[T](expected: T): T = {
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(expected)
    oos.close()

    val b = baos.toByteArray()
    val bais = new ByteArrayInputStream(b)
    val ois = new ObjectInputStream(bais)

    val actual = ois.readObject().asInstanceOf[T]
    ois.close()
    actual
  }
  
  def testHadoopSerialization[T <: Writable](expected: T)(implicit ct: ClassTag[T]): T = {
    val baos = new ByteArrayOutputStream()
    val dos = new DataOutputStream(baos)
    expected.write(dos)
    dos.close()

    val b = baos.toByteArray()
    val bais = new ByteArrayInputStream(b)
    val dis = new DataInputStream(bais)

    val actual = ct.runtimeClass.newInstance.asInstanceOf[T]
    actual.readFields(dis)
    dis.close()
    actual
  }
}

class TileIdWritableSpec extends FunSpec with ShouldMatchers with SerializerTester {
  describe("java serialization") {
    it("should serdes TileIdWritable") {
      val tw = TileIdWritable(1)
      val actualtw = testJavaSerialization(tw)
      actualtw should be(tw)
    }
  }
  describe("hadoop serialization") {
    it("should serdes TileIdWritable") {
      val tw = TileIdWritable(1)
      val actualtw = testHadoopSerialization(tw)
      actualtw should be(tw)
    }
  }
}