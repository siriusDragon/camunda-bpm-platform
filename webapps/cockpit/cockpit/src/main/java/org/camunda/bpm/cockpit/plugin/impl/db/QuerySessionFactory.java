package org.camunda.bpm.cockpit.plugin.impl.db;

import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 *
 * @author drobisch
 *
 */
public class QuerySessionFactory extends StandaloneProcessEngineConfiguration {

  private List<String> mappingFiles;

  protected void init() {
    throw new IllegalArgumentException(
            "Normal 'init' on process engine only used for extended MyBatis mappings is not allowed, please use 'initFromProcessEngineConfiguration'. You cannot construct a process engine with this configuration.");
  }

  /**
   * initialize the {@link ProcessEngineConfiguration} from an existing one,
   * just using the database settings and initialize the database / MyBatis
   * stuff.
   */
  public void initFromProcessEngineConfiguration(ProcessEngineConfigurationImpl processEngineConfiguration, List<String> mappings) {
    this.mappingFiles = mappings;

    setDatabaseType(processEngineConfiguration.getDatabaseType());
    setDataSource(processEngineConfiguration.getDataSource());
    setDatabaseTablePrefix(processEngineConfiguration.getDatabaseTablePrefix());

    initDataSource();
    initVariableTypes();
    initCommandContextFactory();
    initTransactionFactory();
    initTransactionContextFactory();
    initCommandExecutors();
    initSqlSessionFactory();
    initSessionFactories();
  }

  @Override
  protected InputStream getMyBatisXmlConfigurationSteam() {
    return new ReaderInputStream(new StringReader(buildMappings(mappingFiles)));
  }

  protected String buildMappings(List<String> mappingFiles) {

    StringBuilder builder = new StringBuilder();
    for (String mappingFile: mappingFiles) {
      builder.append(String.format("<mapper resource=\"%s\" />\n", mappingFile));
    }

    String mappingsFileTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "\n" +
        "<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
        "\n" +
        "<configuration>\n" +
        "	<settings>\n" +
        "		<setting name=\"lazyLoadingEnabled\" value=\"false\" />\n" +
        "	</settings>\n" +
        "	<mappers>\n" +
        "%s\n" +
        "	</mappers>\n" +
        "</configuration>";

    return String.format(mappingsFileTemplate, builder.toString());
  }
}

