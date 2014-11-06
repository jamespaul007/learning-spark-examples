/**
 * Load some tweets stored as JSON data and explore them.
 */
package com.oreilly.learningsparkexamples.scala

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext


object SparkSQLTwitter {
    def main(args: Array[String]) {
      if (args.length < 1) {
        println("Usage inputFile outputFile")
      }
      val inputFile = args(0)
      val outputFile = args(1)
      val conf = new SparkConf()
      val sc = new SparkContext(conf)
      val sqlCtx = new SQLContext(sc)
      import sqlCtx._
      // Load some tweets
      val input = sqlCtx.jsonFile(inputFile)
      // Print the schema
      input.printSchema()
      // Register the input schema RDD
      input.registerTempTable("tweets")
      // Select tweets based on the retweetCount
      val topTweets = sqlCtx.sql("SELECT text, retweetCount FROM tweets ORDER BY retweetCount LIMIT 10")
      topTweets.collect().map(println(_))
      sc.stop()
    }
}