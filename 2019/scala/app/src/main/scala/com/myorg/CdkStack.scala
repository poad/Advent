package com.myorg

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.core.Stack
import software.amazon.awscdk.core.StackProps

class CdkStack(val scope: Construct, val id: String, val props: StackProps) // The code that defines your stack goes here
  extends Stack(scope, id, props) {
  def this(scope: Construct, id: String) {
    this(scope, id, null)
  }
}