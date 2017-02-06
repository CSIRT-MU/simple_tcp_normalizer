package cz.muni.csirt.seccloud.normalization.cli;

import org.kohsuke.args4j.Option;

public class NormalizerCLI {

    @Option(name = "--listen", required = true, usage = "Listen on PORT", metaVar = "INTEGER")
    private int listenPort = 56789;

    @Option(name = "--topic", usage = "Send messages to TOPIC", metaVar = "STRING")
    private String topic = "ipfix.entry";

    @Option(name = "--bootstrap", usage = "Kafka Bootstrap Servers", metaVar = "STRING")
    private String bootstrapServers = "localhost:9092";

    @Option(name = "--acks", usage = "Kafka ACKS", metaVar = "STRING")
    private String acks = "1";

    @Option(name = "--retries", usage = "Kafka retries", metaVar = "INTEGER")
    private int retries = 0;

    @Option(name = "--batchsize", usage = "Kafka batch size", metaVar = "INTEGER")
    private int batchSize = 16384;

    @Option(name = "--linger", usage = "Kafka linger.ms", metaVar = "INTEGER")
    private int lingerMs = 1;

    @Option(name = "--buffermemory", usage = "Kafka buffer memory", metaVar = "INTEGER")
    private int bufferMemory = 33554432;

    public int getListenPort() {
        return listenPort;
    }

    public String getTopic() {
        return topic;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getAcks() {
        return acks;
    }

    public int getRetries() {
        return retries;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getLingerMs() {
        return lingerMs;
    }

    public int getBufferMemory() {
        return bufferMemory;
    }
}