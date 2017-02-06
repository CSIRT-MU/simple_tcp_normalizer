#!/usr/bin/env bash
java -XX:+UseConcMarkSweepGC -Xms1024m -Xmx1024m -jar target/simple_tcp_normalizer-0.1-SNAPSHOT-jar-with-dependencies.jar "$@"