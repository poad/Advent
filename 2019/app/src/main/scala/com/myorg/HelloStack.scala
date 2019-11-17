package com.myorg

import software.amazon.awscdk.core.Construct
import software.amazon.awscdk.core.Duration
import software.amazon.awscdk.core.Stack
import software.amazon.awscdk.core.StackProps
import software.amazon.awscdk.services.iam.User
import software.amazon.awscdk.services.iam.UserProps
import software.amazon.awscdk.services.sns.Topic
import software.amazon.awscdk.services.sns.TopicProps
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription
import software.amazon.awscdk.services.sqs.Queue
import software.amazon.awscdk.services.sqs.QueueProps

class HelloStack(val parent: Construct, val id: String, val props: StackProps) extends Stack(parent, id, props) {
  val queue = new Queue(this, "MyFirstQueue", QueueProps.builder.visibilityTimeout(Duration.seconds(300)).build)
  val topic = new Topic(this, "MyFirstTopic", TopicProps.builder.displayName("My First Topic Yeah").build)
  topic.addSubscription(new SqsSubscription(queue))
  val hello = new HelloConstruct(this, "Buckets", HelloConstructProps.builder.bucketCount(5).build)
  val user = new User(this, "MyUser", UserProps.builder.build)
  hello.grantRead(user)

  def this(parent: Construct, id: String) {
    this(parent, id, null)
  }
}