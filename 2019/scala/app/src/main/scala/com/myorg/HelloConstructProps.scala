package com.myorg

object HelloConstructProps {
  def builder(): HelloConstructPropsBuilder = {
    new HelloConstructPropsBuilder
  }

  object HelloConstructPropsBuilder {
    def aHelloConstructProps = new HelloConstructProps.HelloConstructPropsBuilder
  }

  final class HelloConstructPropsBuilder private[HelloConstructProps]() {
    private var bucketCount: Int = 0

    def bucketCount(bucketCount: Int = 0): HelloConstructPropsBuilder = {
      this.bucketCount = bucketCount
      this
    }

    def build = {
      val helloConstructProps = new HelloConstructProps
      helloConstructProps.setBucketCount(bucketCount)
      helloConstructProps
    }
  }

}

class HelloConstructProps {

  private var bucketCount = 0

  def getBucketCount = bucketCount

  def setBucketCount(bucketCount: Int) = this.bucketCount = bucketCount
}