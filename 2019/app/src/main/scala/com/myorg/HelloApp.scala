package com.myorg

import software.amazon.awscdk.core.App

object HelloApp {
  def main(args: Array[String]): Unit = {
    val app = new App
    new HelloStack(app, "HelloCdkStack")
    // required until https://github.com/aws/jsii/issues/456 is resolved
    app.synth
  }
}