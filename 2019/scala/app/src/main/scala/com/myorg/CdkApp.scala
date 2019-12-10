package com.myorg

import software.amazon.awscdk.core.App

object CdkApp {
  def main(args: Array[String]): Unit = {
    val app = new App
    new CdkStack(app, "CdkStack")
    app.synth
  }
}