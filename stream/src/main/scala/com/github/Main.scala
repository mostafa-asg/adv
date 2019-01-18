package com.github

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions.{col, _}
import org.elasticsearch.hadoop.cfg.ConfigurationOptions

object Main {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
                            .master("local[*]")
                            .appName("advertisement")
                            .config(ConfigurationOptions.ES_NODES, "127.0.0.1")
                            .config(ConfigurationOptions.ES_PORT, "9200")
                            .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    val clicks = spark.readStream
                       .format("kafka")
                       .option("kafka.bootstrap.servers", "127.0.0.1:9092")
                       .option("subscribe", "clicks")
                       .load()
                       .select(
                          col("key").cast("string"),
                          from_json(col("value").cast("string"), Schemas.clickEventSchema).as("c")
                       )
                       .withColumn("clickTime", new Column("c.clickTime"))
                       .withWatermark("clickTime", "1 hour")

    val impressions = spark.readStream
                        .format("kafka")
                        .option("kafka.bootstrap.servers", "127.0.0.1:9092")
                        .option("subscribe", "impressions")
                        .load()
                        .select(
                          col("key").cast("string"),
                          from_json(col("value").cast("string"), Schemas.impressionEventSchema).as("i")
                        )
                        .withColumn("impressionTime", new Column("i.impressionTime"))
                        .withWatermark("impressionTime", "1 hour")

    val result = impressions.join(clicks, expr(
          """
                 i.requestId = c.requestId AND
                 clickTime >= impressionTime AND
                 clickTime <= impressionTime + interval 1 minute
          """))
                            .select(
                              col("i.requestId").as("requestId"),
                              col("i.adId").as("adId"),
                              col("i.adTitle").as("adTitle"),
                              col("i.advertiserCost").as("advertiserCost"),
                              col("i.appId").as("appId"),
                              col("i.appTitle").as("appTitle"),
                              col("impressionTime"),
                              col("clickTime")
                            )

        result.writeStream
          .format("org.elasticsearch.spark.sql")
          .option("checkpointLocation", "/tmp/es/checkpoint")
          .start("ad/clicks")
          .awaitTermination()

  }

}
