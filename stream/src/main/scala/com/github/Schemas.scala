package com.github

import org.apache.spark.sql.types._

object Schemas {

  val clickEventSchema = new StructType()
    .add("requestId", StringType)
    .add("clickTime", TimestampType)

  val impressionEventSchema = new StructType()
    .add("requestId", StringType)
    .add("adId", StringType)
    .add("adTitle", StringType)
    .add("advertiserCost", DoubleType)
    .add("appId", StringType)
    .add("appTitle", StringType)
    .add("impressionTime", TimestampType)

}
