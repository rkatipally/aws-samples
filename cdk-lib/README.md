
## Create Library
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
