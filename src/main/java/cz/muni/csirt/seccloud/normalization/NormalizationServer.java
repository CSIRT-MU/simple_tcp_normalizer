/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cz.muni.csirt.seccloud.normalization;

import cz.muni.csirt.seccloud.normalization.cli.Configuration;
import cz.muni.csirt.seccloud.normalization.cli.NormalizerCLI;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@SuppressWarnings("SameParameterValue")
public final class NormalizationServer {

    public static void main(String[] args) throws Exception {
        NormalizerCLI cli = new NormalizerCLI();
        CmdLineParser parser = new CmdLineParser(cli);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("tcp_normalizer [options...]");
            parser.printUsage(System.err);
            System.err.println();
            // print option sample. This is useful some time
            System.err.println("Example: tcp_normalizer" + parser.printExample(OptionHandlerFilter.PUBLIC));
            System.exit(1);
        }

        Yaml yaml = new Yaml();
        Configuration config = yaml.loadAs(Files.newInputStream(Paths.get(cli.getConfig())), Configuration.class );

        final String LISTEN_ADDRESS = config.getListenAddress();
        final int LISTEN_PORT = config.getListenPort();
        final String TOPIC = config.getTopic();
        final String BOOTSTRAP_SERVERS = config.getBootstrapServers();
        final String ACKS = config.getAcks();
        final int RETRIES = config.getRetries();
        final int BATCH_SIZE = config.getBatchSize();
        final int LINGER_MS = config.getLingerMs();
        final int BUFFER_MEMORY = config.getBufferMemory();

        Producer<String, String> producer = new KafkaProducer<>(kafkaProperties(BOOTSTRAP_SERVERS, ACKS, RETRIES, BATCH_SIZE, LINGER_MS, BUFFER_MEMORY));

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NormalizationServerInitializer(producer, TOPIC));

            serverBootstrap.bind(LISTEN_ADDRESS, LISTEN_PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static Properties kafkaProperties(String bootstrapServers, String acks, int retries, int batchSize, int lingerMS, int bufferMemory) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", acks);
        props.put("retries", retries);
        props.put("batch.size", batchSize);
        props.put("linger.ms", lingerMS);
        props.put("buffer.memory", bufferMemory);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
