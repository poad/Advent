package com.myorg

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.services.iam.IGrantable
import software.amazon.awscdk.services.s3.Bucket
import software.amazon.awscdk.services.s3.BucketProps
import scala.jdk.CollectionConverters._

/**
 * Example of a reusable construct. This one defines N buckets.
 */
class HelloConstruct(val parent: Construct, val name: String, val props: HelloConstructProps) extends Construct(parent, name) {
  final private val buckets = List.range(0, props.getBucketCount)
    .map(i => new Bucket(this, "Bucket" + String.valueOf(i), BucketProps.builder.build)).asJava

  /**
   * Given an principal, grants it READ access on all buckets.
   *
   * @param grantee The principal (User, Group, Role)
   */
  def grantRead(grantee: IGrantable) = buckets.forEach((b: Bucket) => b.grantRead(grantee, "*"))
}