package maml.ast

import maml.ast.codec.MapAlgebraCodec

import io.circe._

case class Histogram(counts: Map[Double, Int])
case class ColorRamp(colors: Vector[Int])

case class NodeMetadata(
  label: Option[String] = None,
  description: Option[String] = None,
  histogram: Option[Histogram] = None,
  colorRamp: Option[ColorRamp] = None,
  classMap: Option[ClassMap] = None,
  breaks: Option[Vector[Double]] = None
) {

  /** A helper method for merging default values with overrides */
  def fallbackTo(that: NodeMetadata): NodeMetadata = {
    NodeMetadata(
      this.label.orElse(that.label),
      this.description.orElse(that.description),
      this.histogram.orElse(that.histogram),
      this.colorRamp.orElse(that.colorRamp),
      this.classMap.orElse(that.classMap)
    )
  }
}

object NodeMetadata extends MapAlgebraCodec {
  implicit val nodeMetadataEncoder: Encoder[NodeMetadata] =
    Encoder.forProduct6("label", "description", "histogram", "colorRamp", "classMap", "breaks")(nmd =>
      (nmd.label, nmd.description, nmd.histogram, nmd.colorRamp, nmd.classMap, nmd.breaks)
    )
  implicit val nodeMetadataDecoder: Decoder[NodeMetadata] =
    Decoder.forProduct6("label", "description", "histogram", "colorRamp", "classMap", "breaks")(NodeMetadata.apply)
}
