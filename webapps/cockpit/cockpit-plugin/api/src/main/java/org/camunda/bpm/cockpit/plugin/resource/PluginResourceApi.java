package org.camunda.bpm.cockpit.plugin.resource;

import java.net.URI;
import java.util.Iterator;
import java.util.ServiceLoader;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.camunda.bpm.engine.rest.impl.AbstractRestProcessEngineAware;
import org.camunda.bpm.engine.rest.spi.ProcessEngineProvider;

/**
 *
 * @author Nico Rehwaldt
 */
public class PluginResourceApi {

  protected <T extends AbstractRestProcessEngineAware> T subResource(T subResource, String engineName) {
    String rootResourcePath = getRelativeEngineUri(engineName).toASCIIString();
    subResource.setRelativeRootResourceUri(rootResourcePath);

    return subResource;
  }

  private URI getRelativeEngineUri(String engineName) {
    return UriBuilder.fromResource(PluginResourceApi.class).path("{name}").build(engineName);
  }

  private ProcessEngineProvider getProcessEngineProvider() {
    ServiceLoader<ProcessEngineProvider> serviceLoader = ServiceLoader.load(ProcessEngineProvider.class);
    Iterator<ProcessEngineProvider> iterator = serviceLoader.iterator();

    if(iterator.hasNext()) {
      ProcessEngineProvider provider = iterator.next();
      return provider;
    } else {
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    }
  }
}
