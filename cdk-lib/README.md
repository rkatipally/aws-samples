# Welcome to your CDK TypeScript project!

You should explore the contents of this project. It demonstrates a CDK app with an instance of a stack (`CdkLibStack`)
which contains an Amazon SQS queue that is subscribed to an Amazon SNS topic.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Useful commands

 * `npm run build`   compile typescript to js
 * `npm run watch`   watch for changes and compile
 * `npm run test`    perform the jest unit tests
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk synth`       emits the synthesized CloudFormation template


##Create Library
* Create domain in CodeArtifact, if does not exist already
* Create a repository (cdk-lib) in above domain
* Create a folder cdk-lib
* CD to cdk-lib
* Initialize CDK sample-app with Typescript (cdk init sample-app --language typescript)
* Initialize npm for scope (npm init --scope=@fundrise -y)
* Change “main” and add “types” in package.json
  * "main": "dist/index.js", 
  * "types": "dist/index.d.ts"
* If local AWS profile is set, add following scripts to package.json,  (https://docs.aws.amazon.com/codeartifact/latest/ug/npm-auth.html)
  * "prepare": "npm run co:login", 
  * "co:login": "aws codeartifact login --profile=dh --tool npm --repository cdk-lib --domain fundrise --namespace @fundrise"
* Modify tsconfig.json
  * Add “outDir” : “dist” to “compilerOptions”
  * Add “include”: [“lib”] to root
  * Add “exclude” : [“!lib”] to root
* Add Custom constructs to lib folder
* Add index.ts to export above created constructs
  * Example: export * from "./secure-bucket.construct";
* Run tsc command to build, dist folder will be created
* Run npm publish to push publish the artifact to CodeArtifact
