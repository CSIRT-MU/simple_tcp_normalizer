package cz.muni.csirt.seccloud.normalization.cli;

import org.kohsuke.args4j.Option;

public class NormalizerCLI {

    @Option(name = "--config", required = true, usage = "Configuration file path", metaVar = "STRING")
    private String config;

    public String getConfig() {
        return config;
    }
}