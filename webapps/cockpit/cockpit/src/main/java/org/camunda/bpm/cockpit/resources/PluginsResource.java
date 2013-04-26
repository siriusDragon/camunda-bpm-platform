package org.camunda.bpm.cockpit.resources;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.cockpit.spi.CockpitPlugin;

/**
 *
 * @author nico.rehwaldt
 */
@Path("/plugins")
public class PluginsResource {

  @GET
  public List<CockpitPlugin> getPlugins() {

    return new PluginRegistry().getCockpitPlugins();
  }
}
