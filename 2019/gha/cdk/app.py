#!/usr/bin/env python3

from aws_cdk import core

from work.work_stack import WorkStack


app = core.App()
WorkStack(app, "work")

app.synth()
