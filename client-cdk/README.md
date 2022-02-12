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
