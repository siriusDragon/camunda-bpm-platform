package org.camunda.bpm.cockpit.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.cockpit.plugin.api.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.impl.db.CommandExecutor;
import org.camunda.bpm.cockpit.plugin.impl.db.QueryService;
import org.camunda.bpm.cockpit.spi.CockpitPlugin;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;

/**
 *
 * @author Nico Rehwaldt
 */
@WebListener
public class CockpitBootstrap implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();

    initCommandExecutor(processEngine);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }

  public static void initCommandExecutor(ProcessEngine processEngine) {
    List<CockpitPlugin> cockpitPlugins = PluginRegistry.getCockpitPlugins();

    List<String> mappingFiles = new ArrayList<String>();
    for (CockpitPlugin plugin: cockpitPlugins) {
      mappingFiles.addAll(plugin.getMappingFiles());
    }

    CommandExecutor commandExecutor = new CommandExecutor(((ProcessEngineImpl)processEngine).getProcessEngineConfiguration(), mappingFiles);

    QueryService service = new QueryService(commandExecutor);

    List<HistoricProcessInstance> instances = service.executeQuery("cockpit.core.selectHistoricProcessInstance", new QueryParameters<HistoricProcessInstance>());

    System.out.println(instances);
  }

}
