ARG JAVA_MAJOR_VERSION="8"
ARG JAVA_UPDATE="242"
ARG JAVA_BUILD_NUMBER="08"
ARG JAVA_MAJOR_MINOR_REVISION="${JAVA_MAJOR_VERSION}u${JAVA_UPDATE}"
ARG JAVA_VERSION="jdk${JAVA_MAJOR_MINOR_REVISION}-b${JAVA_BUILD_NUMBER}"
ARG JAVA_HOME="/usr/lib/jvm/java-${JAVA_VERSION}-openjdk-amd64"
ARG ESUM="f39b523c724d0e0047d238eb2bb17a9565a60574cf651206c867ee5fc000ab43"
ARG SBT_VERSION=1.3.7
ARG SBT_HOME=/usr/local/sbt
ARG MAVEN_VERSION="3.6.3"
ARG MAVEN_URL="https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz"
ARG MAVEN_SHA512SUM="c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0"

ARG NODE_VERSION=12.14.1
ARG YARN_VERSION=1.19.2

ARG GPG_KEY=E3FF2839C048B25C084DEBE9B26995E310250568
ARG PYTHON_VERSION=3.8.1
ARG PYTHON_PIP_VERSION=20.0.2
# https://github.com/pypa/get-pip
ARG PIP_DOWNLOAD_HASH="42ad3426cb1ef05863521d7988d5f7fec0c99560"
ARG PYTHON_GET_PIP_URL=https://github.com/pypa/get-pip/raw/${PIP_DOWNLOAD_HASH}/get-pip.py
ARG PYTHON_GET_PIP_SHA256=da288fc002d0bb2b90f6fbabc91048c1fa18d567ad067ee713c6e331d3a32b45


FROM alpine:latest AS downloader

ARG GPG_KEY
ARG PYTHON_VERSION

# if this is called "PIP_VERSION", pip explodes with "ValueError: invalid truth value '<VERSION>'"
ARG PYTHON_PIP_VERSION
# https://github.com/pypa/get-pip
ARG PYTHON_GET_PIP_URL
ARG PYTHON_GET_PIP_SHA256

ARG JAVA_MAJOR_VERSION
ARG JAVA_MINOR_REVISION
ARG JAVA_MAJOR_MINOR_REVISION
ARG JAVA_BUILD_NUMBER
ARG JAVA_VERSION
ARG JAVA_URL="https://github.com/AdoptOpenJDK/openjdk${JAVA_MAJOR_VERSION}-binaries/releases/download/${JAVA_VERSION}/OpenJDK${JAVA_MAJOR_VERSION}U-jdk_x64_linux_hotspot_${JAVA_MAJOR_MINOR_REVISION}b${JAVA_BUILD_NUMBER}.tar.gz"
ARG ESUM
ARG JAVA_HOME
ARG SBT_VERSION
ARG SBT_HOME
ARG MAVEN_VERSION
ARG MAVEN_URL
ARG MAVEN_SHA512SUM
ARG NODE_VERSION
ARG YARN_VERSION


ENV PATH=/usr/local/python/bin:/usr/local/bin:$PATH \
    JAVA_HOME=${JAVA_HOME}

RUN apk add --no-cache --virtual .build-deps \
    curl \
    gnupg \
    xz 

WORKDIR /tmp
RUN wget -O get-pip.py "$PYTHON_GET_PIP_URL" \
 && echo "$PYTHON_GET_PIP_SHA256 *get-pip.py" | sha256sum -c - \
 && curl -Lso /tmp/openjdk.tar.gz ${JAVA_URL} \
 && sha256sum /tmp/openjdk.tar.gz \
 && mkdir -p ${JAVA_HOME} \
 && cd ${JAVA_HOME} \
 && echo "${ESUM}  /tmp/openjdk.tar.gz" | sha256sum -c - \
 && tar -xf /tmp/openjdk.tar.gz \
 && jdir=$(dirname $(dirname $(find ${JAVA_HOME} -name javac))) \
 && mv ${jdir}/* ${JAVA_HOME} \
 && rm -rf ${jdir} /tmp/openjdk.tar.gz

RUN mkdir -p /tmp/sbt \
 && curl -sL "https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz" | tar -xz -C /tmp/sbt --strip-components=1

RUN cd /tmp \
 && curl -Lso /tmp/maven.tar.gz ${MAVEN_URL} \
 && sha512sum /tmp/maven.tar.gz \
 && echo "${MAVEN_SHA512SUM}  /tmp/maven.tar.gz" | sha512sum -c - \
 && tar -xf /tmp/maven.tar.gz

RUN ARCH= && dpkgArch="x64" \
 # gpg keys listed at https://github.com/nodejs/node#release-keys
 && set -ex \
 && for key in \
    94AE36675C464D64BAFA68DD7434390BDBE9B9C5 \
    FD3A5288F042B6850C66B31F09FE44734EB7990E \
    71DCFD284A79C3B38668286BC97EC7A07EDE3FC1 \
    DD8F2338BAE7501E3DD5AC78C273792F7D83545D \
    C4F0DFFF4E8C1A8236409D08E73BC641CC11F4C8 \
    B9AE9905FFD7803F25714661B63B535A4C206CA9 \
    77984A986EBC2AA786BC0F66B01FBB92821C587A \
    8FCCA13FEF1D0C2E91008E09770F7A9A5AE15600 \
    4ED778F539E3634C779C87C6D7062848A1AB005C \
    A48C2BEE680E841632CD4E44F07496B3EB3C1762 \
    B9E2F5981AA6E0CD28160D9FF13993A75599653C \
 ; do \
    gpg --batch --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys "$key" || \
    gpg --batch --keyserver hkp://ipv4.pool.sks-keyservers.net --recv-keys "$key" || \
    gpg --batch --keyserver hkp://pgp.mit.edu:80 --recv-keys "$key" ; \
 done \
 && mkdir -p /tmp/node \
 && curl -fsSLO --compressed "https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-x64.tar.xz" \
 && curl -fsSLO --compressed "https://nodejs.org/dist/v$NODE_VERSION/SHASUMS256.txt.asc" \
 && gpg --batch --decrypt --output SHASUMS256.txt SHASUMS256.txt.asc \
 && grep " node-v$NODE_VERSION-linux-x64.tar.xz\$" SHASUMS256.txt | sha256sum -c - \
 && tar -xJf "node-v$NODE_VERSION-linux-x64.tar.xz" -C /tmp/node --strip-components=1 --no-same-owner \
 && rm "node-v$NODE_VERSION-linux-x64.tar.xz" SHASUMS256.txt.asc SHASUMS256.txt

RUN set -ex \
 && for key in \
    6A010C5166006599AA17F08146C2130DFD2497F5 \
 ; do \
    gpg --batch --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys "$key" || \
    gpg --batch --keyserver hkp://ipv4.pool.sks-keyservers.net --recv-keys "$key" || \
    gpg --batch --keyserver hkp://pgp.mit.edu:80 --recv-keys "$key" ; \
 done \
 && curl -fsSLO --compressed "https://yarnpkg.com/downloads/$YARN_VERSION/yarn-v$YARN_VERSION.tar.gz" \
 && curl -fsSLO --compressed "https://yarnpkg.com/downloads/$YARN_VERSION/yarn-v$YARN_VERSION.tar.gz.asc" \
 && gpg --batch --verify yarn-v$YARN_VERSION.tar.gz.asc yarn-v$YARN_VERSION.tar.gz \
 && mkdir -p /tmp/yarn \
 && tar -xzf yarn-v$YARN_VERSION.tar.gz --strip-components=1 -C /tmp/yarn \
 && rm yarn-v$YARN_VERSION.tar.gz.asc yarn-v$YARN_VERSION.tar.gz

RUN set -ex \
 \
 && wget -O python.tar.xz "https://www.python.org/ftp/python/${PYTHON_VERSION%%[a-z]*}/Python-$PYTHON_VERSION.tar.xz" \
 && wget -O python.tar.xz.asc "https://www.python.org/ftp/python/${PYTHON_VERSION%%[a-z]*}/Python-$PYTHON_VERSION.tar.xz.asc" \
 && export GNUPGHOME="$(mktemp -d)" \
 && for key in \
    "$GPG_KEY" \
 ; do \
   gpg --batch --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys "$key" || \
   gpg --batch --keyserver hkp://ipv4.pool.sks-keyservers.net --recv-keys "$key" || \
   gpg --batch --keyserver hkp://pgp.mit.edu:80 --recv-keys "$key" ; \
 done \
 && gpg --batch --verify python.tar.xz.asc python.tar.xz \
 && { command -v gpgconf > /dev/null && gpgconf --kill all || :; } \
 && rm -rf "$GNUPGHOME" python.tar.xz.asc \
 && mkdir -p /usr/src/python \
 && tar -xJC /usr/src/python --strip-components=1 -f python.tar.xz \
 && rm python.tar.xz \
 && mkdir -p /usr/local/python


FROM poad/docker-llvm-python:bionic-zsh

LABEL maintainer="Kenji Saito<ken-yo@mbr.nifty.com>"

ARG MAVEN_VERSION
ARG JAVA_HOME
ARG PYTHON_PIP_VERSION
ARG SBT_HOME

USER root

ENV MAVEN_HOME=/usr/lib/maven \
    SBT_HOME=${SBT_HOME} \
    PATH=/usr/local/bin:/usr/local/yarn/bin:/usr/local/node/bin:${MAVEN_HOME}/bin:${SBT_HOME}/bin:${JAVA_HOME}/bin:${PATH}

ENV LANG=C.UTF-8 \
    DEBIAN_FRONTEND=noninteractive

COPY --from=downloader /tmp/node /usr/local/node
COPY --from=downloader /tmp/yarn /usr/local/yarn
COPY --from=downloader ${JAVA_HOME} ${JAVA_HOME}
COPY --from=downloader /tmp/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME}
COPY --from=downloader /tmp/sbt ${SBT_HOME}

RUN for tool_path in ${JAVA_HOME}/bin/*; do \
          tool=`basename $tool_path` \
       && update-alternatives --install /usr/bin/$tool $tool $tool_path 10000 \
       && update-alternatives --set $tool $tool_path; \
    done

RUN apt-get update -qq \
 && apt-get install -qqy --no-install-recommends \
		libexpat1 \
      libssl1.1 \
 && apt-get install -qqy --no-install-recommends ca-certificates bash curl git \
 && ln -s /usr/local/node/bin/node /usr/local/node/bin/nodejs \
 && npm install n -g \
 && n stable \
 && apt-get autoremove --purge -qqy ca-certificates curl \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/* /tmp/* /var/log/apt/* /var/log/alternatives.log /var/log/dpkg.log /var/log/faillog /var/log/lastlog \
 && rm -rf /tmp/* \
 && npm install -g aws-cdk

USER zsh

ENV MAVEN_HOME=/usr/lib/maven \
    JAVA_HOME=${JAVA_HOME} \
    SBT_HOME=${SBT_HOME} \
    PATH=/home/zsh/.local/bin:/usr/local/yarn/bin:/usr/local/node/bin:${MAVEN_HOME}/bin:${SBT_HOME}/bin:${JAVA_HOME}/bin:${PATH}

WORKDIR /home/zsh

RUN pip install -U awscli --user
RUN mkdir -p cdk

WORKDIR /home/zsh/cdk
