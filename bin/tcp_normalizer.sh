#!/usr/bin/env bash
DIR="${BASH_SOURCE%/*}"
java -XX:+UseConcMarkSweepGC -Xms1024m -Xmx1024m -jar ${DIR}/../target/simple_tcp_normalizer-0.1-SNAPSHOT-jar-with-dependencies.jar "$@"
