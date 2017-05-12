package geotrellis.spark.distance

import com.vividsolutions.jts.geom.Coordinate
import org.apache.spark.rdd.RDD

import geotrellis.spark._
import geotrellis.vector.{MultiPoint, Point}

object Implicits extends Implicits

trait Implicits {
  implicit class withEuclideanDistanceRDDMethods(val self: RDD[(SpatialKey, Array[Coordinate])]) extends EuclideanDistanceRDDMethods

  implicit class withEuclideanDistancePointRDDMethods(val self: RDD[(SpatialKey, Array[Point])]) extends EuclideanDistancePointRDDMethods

  implicit class withEuclideanDistancePairRDDMethods(val self: RDD[(SpatialKey, Array[(Double, Double)])]) extends EuclideanDistancePairRDDMethods

  implicit class withEuclideanDistanceTripleRDDMethods(val self: RDD[(SpatialKey, Array[(Double, Double, Double)])]) extends EuclideanDistanceTripleRDDMethods

  implicit class withEuclideanDistanceMultiPointRDDMethods(val self: RDD[(SpatialKey, MultiPoint)]) extends EuclideanDistanceMultiPointRDDMethods
}
