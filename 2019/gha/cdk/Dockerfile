FROM alpine:3.10

LABEL maintainer="Kenji Saito<ken-yo@mbr.nifty.com>"

RUN apk --update add --no-cache --virtual .build-deps \
        python3 \
        py3-setuptools \
        nodejs-current \
        npm \
 && pip3 install -U pip \
 && pip install -U awscli \
 && npm i -g aws-cdk
