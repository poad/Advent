package com.myorg

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.services.iam.IGrantable
import software.amazon.awscdk.services.s3.Bucket
import software.amazon.awscdk.services.s3.BucketProps
import java.util

/**
 * Example of a reusable construct. This one defines N buckets.
 */
class HelloConstruct(val parent: Construct, val name: String, val props: HelloConstructProps) extends Construct(parent, name) {
  List.range(0, props.getBucketCount)
    .foreach(i => buckets.add(new Bucket(this, "Bucket" + String.valueOf(i), BucketProps.builder.build)))
  final private val buckets = new util.ArrayList[Bucket]

  /**
   * Given an principal, grants it READ access on all buckets.
   *
   * @param grantee The principal (User, Group, Role)
   */
  def grantRead(grantee: IGrantable) = buckets.forEach((b: Bucket) => b.grantRead(grantee, "*"))
}