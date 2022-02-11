import { Duration, Stack, StackProps } from "aws-cdk-lib";
import * as sns from "aws-cdk-lib/aws-sns";
import * as subs from "aws-cdk-lib/aws-sns-subscriptions";
import * as sqs from "aws-cdk-lib/aws-sqs";
import { Construct } from "constructs";
import { SecureBucket } from "@fundrise/cdk-lib";

export class ClientCdkStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const queue = new sqs.Queue(this, "ClientCdkQueue", {
      visibilityTimeout: Duration.seconds(300),
    });

    const topic = new sns.Topic(this, "ClientCdkTopic");

    topic.addSubscription(new subs.SqsSubscription(queue));
    const secureBucket = new SecureBucket(this, "custom-sample");
  }
}
