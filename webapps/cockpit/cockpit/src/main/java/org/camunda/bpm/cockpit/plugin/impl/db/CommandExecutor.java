package org.camunda.bpm.cockpit.plugin.impl.db;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;

/**
 *
 * @author drobisch
 * @author nico.rehwaldt
 */
public class CommandExecutor {

  private static final long serialVersionUID = 1L;

  private QuerySessionFactory sessionFactory;

  public CommandExecutor() { }

  public CommandExecutor(ProcessEngineConfigurationImpl processEngineConfiguration, List<String> mappingFiles) {
    sessionFactory = new QuerySessionFactory();
    sessionFactory.initFromProcessEngineConfiguration(processEngineConfiguration, mappingFiles);
  }

  public <T> T executeQueryCommand(Command<T> command) {
    return sessionFactory.getCommandExecutorTxRequired().execute(command);
  }

  /**
   * Create a new executor from the given engine
   *
   * @param engine
   * @return
   */
  public static CommandExecutor createFromEngine(ProcessEngine engine, List<String> mappingFiles) {
    if (!(engine instanceof ProcessEngineImpl)) {
      throw new IllegalArgumentException("Argument must be an instance of " + ProcessEngineImpl.class.getName());
    }

    ProcessEngineConfigurationImpl processEngineConfiguration = ((ProcessEngineImpl) engine).getProcessEngineConfiguration();
    return new CommandExecutor(processEngineConfiguration, mappingFiles);
  }
}

