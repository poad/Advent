package com.myorg

import org.scalatest.FunSuite
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import software.amazon.awscdk.core.App

object HelloStackTest {
  private val JSON = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
}

class HelloStackTest extends FunSuite {
  test("Create stack is successed") {
    val app = new App
    val stack = new HelloStack(app, "test")

    val actual = HelloStackTest.JSON.valueToTree(app.synth.getStackArtifact(stack.getArtifactId).getTemplate)
    val expected = HelloStackTest.JSON.readTree(getClass.getResource("expected.cfn.json"))
    assert(actual.equals(expected))
  }
}