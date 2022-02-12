# Welcome to your CDK TypeScript project!

You should explore the contents of this project. It demonstrates a CDK app with an instance of a stack (`ClientCdkStack`)
which contains an Amazon SQS queue that is subscribed to an Amazon SNS topic.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Useful commands

 * `npm run build`   compile typescript to js
 * `npm run watch`   watch for changes and compile
 * `npm run test`    perform the jest unit tests
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk synth`       emits the synthesized CloudFormation template

## Consume Library
* Create a folder client-cdk
* CD to client-cdk
* Initialize CDK sample-app with Typescript (cdk init sample-app --language typescript)
* If local AWS profile is set, add following scripts to package.json, (https://docs.aws.amazon.com/codeartifact/latest/ug/npm-auth.html)
  * "prepare": "npm run co:login", 
  * "co:login": "aws codeartifact login --profile=dh --tool npm --repository cdk-lib --domain fundrise --namespace @fundrise"
* Add "@fundrise/cdk-lib": "^0.1.0" to dependencies in package.json
* Run npm install
* Use Custom construct like AWS constructs
* Run cdk synth to verify
