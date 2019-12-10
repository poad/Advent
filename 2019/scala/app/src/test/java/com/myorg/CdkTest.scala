package com.myorg

import org.scalatest.FunSuite
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import software.amazon.awscdk.core.App

object CdkTest {
  private val JSON = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
}

class CdkTest extends FunSuite {
  test("Create stack is successed") {
    val app = new App
    val stack = new CdkStack(app, "test")
    // synthesize the stack to a CloudFormation template and compare against
    // a checked-in JSON file.
    val actual = CdkTest.JSON.valueToTree(app.synth.getStackArtifact(stack.getArtifactId).getTemplate)
    assert(actual.equals(new ObjectMapper().createObjectNode))
  }
}