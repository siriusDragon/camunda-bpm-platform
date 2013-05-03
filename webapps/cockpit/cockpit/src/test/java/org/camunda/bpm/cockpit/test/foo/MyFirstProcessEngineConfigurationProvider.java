package org.camunda.bpm.cockpit.test.foo;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 *
 * @author nico.rehwaldt
 */
@ProcessEngineConfiguration
public class MyFirstProcessEngineConfigurationProvider implements ProcessEngineConfigurationProvider {

  public ProcessEngineConfiguration createProcessEngineConfiguration() {

    StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();

    // configure the configuration

    return configuration;
  }
}
