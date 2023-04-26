package io.redick.cloud.code;

import lombok.Data;

import java.util.List;

/**
 * @author Redick01
 */
@Data
public class GeneratorConfig {

    private String url;

    private String username;

    private String password;

    private String author = "";

    private String outputDir;

    private String parentPackage;

    private String mapperPath;

    private List<String> tables;

    private String tablePrefix = "";

    private String service = "%sService";

    private String serviceImpl = "%sServiceImpl";
}
