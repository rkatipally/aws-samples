#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { ClientCdkStack } from '../lib/client-cdk-stack';

const app = new cdk.App();
new ClientCdkStack(app, 'ClientCdkStack');
