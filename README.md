# Simple TCP Normalization Component

Build:

<pre>
mvn package
</pre>

Minimal usage example:
<pre>
bin/tcp_normalizer.sh --listen 56789
</pre>

Usage:
 
<pre>
tcp_normalizer [options...]
 --acks STRING          : Kafka ACKS (default: 1)
 --batchsize INTEGER    : Kafka batch size (default: 16384)
 --bootstrap STRING     : Kafka Bootstrap Servers (default: localhost:9092)
 --buffermemory INTEGER : Kafka buffer memory (default: 33554432)
 --linger INTEGER       : Kafka linger.ms (default: 1)
 --listen INTEGER       : Listen on PORT
 --retries INTEGER      : Kafka retries (default: 0)
 --topic STRING         : Send messages to TOPIC (default: ipfix.entry)

Example: tcp_normalizer --acks STRING --batchsize INTEGER --bootstrap STRING --buffermemory INTEGER --linger INTEGER --listen INTEGER --retries INTEGER --topic STRING
</pre>