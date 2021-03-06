package org.camunda.bpm.engine.impl.cmd;

import org.camunda.bpm.application.ProcessApplicationReference;
import org.camunda.bpm.application.ProcessApplicationRegistration;
import org.camunda.bpm.engine.impl.application.ProcessApplicationManager;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

/**
 * 
 * 
 * @author Daniel Meyer
 *
 */
public class ActivateDeploymentForApplicationCmd implements Command<ProcessApplicationRegistration> {

  protected ProcessApplicationReference reference;
  protected String deploymentId;

  public ActivateDeploymentForApplicationCmd(String deploymentId, ProcessApplicationReference reference) {
    this.deploymentId = deploymentId;
    this.reference = reference;
  }

  public ProcessApplicationRegistration execute(CommandContext commandContext) {
    
    final ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();    
    final ProcessApplicationManager processApplicationManager = processEngineConfiguration.getProcessApplicationManager();
    
    return processApplicationManager.registerProcessApplicationForDeployment(deploymentId, reference);
    
  }

}
