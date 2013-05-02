/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.cockpit.core.web.resources.plugin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.camunda.bpm.cockpit.core.plugin.Registry;
import org.camunda.bpm.cockpit.plugin.spi.CockpitPlugin;

/**
 *
 * @author nico.rehwaldt
 */
@Path("plugin")
public class PluginResources {

  @GET
  public List<CockpitPlugin> getPlugins() {
    return Registry.getCockpitPlugins();
  }

  @GET
  @Path("{pluginName}")
  public PluginAssetsResource getResource(@PathParam("pluginName") String pluginName) {
    CockpitPlugin plugin = Registry.getCockpitPlugin(pluginName);

    if (plugin == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }

    return new PluginAssetsResource(plugin);
  }
}
