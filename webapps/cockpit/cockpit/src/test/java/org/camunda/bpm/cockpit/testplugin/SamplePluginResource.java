package org.camunda.bpm.cockpit.testplugin;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.plugin.resource.PluginResourceApi;

/**
 *
 * @author Nico Rehwaldt
 */
@Path("sample")
public class SamplePluginResource extends PluginResourceApi {

  @Path("{engine}/my-resource")
  public MyResource getMyResource(@PathParam("engine") String engine) {
    return subResource(new MyResource(engine), engine);
  }
}
