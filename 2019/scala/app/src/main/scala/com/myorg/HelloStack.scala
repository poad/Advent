package com.myorg

import software.amazon.awscdk.core.{Construct, Stack, StackProps}
import software.amazon.awscdk.services.iam.{User, UserProps}
import software.amazon.awscdk.services.s3.{Bucket, BucketProps}

class HelloStack(val parent: Construct, val id: String, val props: StackProps = null) extends Stack(parent, id, props) {
  val hello = new HelloConstruct(this, "Buckets", HelloConstructProps.builder().bucketCount(5).build)
  val user = new User(this, "MyUser", UserProps.builder.build)
  hello.grantRead(user)

  if (props != null) {
    new Bucket(this, "MyFirstBucket", BucketProps.builder()
      .versioned(true)
      .build())
  }
}