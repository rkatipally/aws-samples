#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { CdkLibStack } from '../lib/cdk-lib-stack';

const app = new cdk.App();
new CdkLibStack(app, 'CdkLibStack');
