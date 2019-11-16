# Scala で AWS CDK

Scala をから AWS CDK(Java Developer Preview) を使ってみる。
Qiita の Scala Advent Calender 2019 向けのコードです。

## 環境構築

ローカル環境を汚さない・環境依存を減らす目的で Docker 上で作業することとします。

### Docker イメージの用意

以下の理由により、拙作のUbuntuにzshを入れたイメージ[poad/docker-llvm-python:bionic-zsh](https://hub.docker.com/r/poad/ocker-llvm-python) をベースとしました。

 - debian:buster より、ubuntu:bionic の方がイメージサイズが若干小さい。
 - [Python のオフィシャルイメージ](https://hub.docker.com/_/python) に ubuntu:bionic がない。
 - zshが便利

[Python のオフィシャルイメージ](https://hub.docker.com/_/python) に変えて、zshユーザー(単なるLinuxユーザーです)を追加しても動くはずです。

#### 内容物(zshを除く)
CDK の [Getting Started](https://docs.aws.amazon.com/cdk/latest/guide/getting_started.html#getting_started_prerequisites) Prerequisites を参考に、
以下の内容をインストール

| アプリ | バージョン | 補足 |
|:-----:|:--------:|:---:|
| Node.js | 12.13.0 |  |
| YARN | 1.19.1 | 要らないだろうけどついでに入れました |
| Java | 11.0.5-b10 (AdoptOpenJDK) | 公式ではJava 8前提ですが、きっと動くでしょう |
| Apache Maven | 3.6.2 | |
| sbt | 1.3.3 | |
| Scala | 2.13.1 | build.sbt で指定したものが使われるので、実際には要らないはず |
| Python | 3.8.0 |  |
| awscli | latest |  |
| AWS CDK CLI | latest |  |


### 実行

拙作の公開されているDockerイメージを使用するか、ご自身でビルドと実行を行うことで使用出来ます。

#### 公開されているDockerイメージを使用する

```$sh
docker run --rm -it -v $(pwd)/app poad/scala-aws-cdk:sbt
```

#### ビルド&実行

```$sh
git clone https://github.com/poad/Advent.git
cd Advent
docker build --rm -t cdk:sbt env/
docker run --rm -it -v $(pwd)/app:/home/zsh/cdk cdk:sbt
```

