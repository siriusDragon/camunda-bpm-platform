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
package org.camunda.bpm.cockpit.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.camunda.bpm.cockpit.spi.CockpitPlugin;

/**
 *
 * @author Nico Rehwaldt
 */
public class PluginRegistry {

  public static List<CockpitPlugin> getCockpitPlugins() {

    ServiceLoader<CockpitPlugin> loader = ServiceLoader.load(CockpitPlugin.class);

    Iterator<CockpitPlugin> iterator = loader.iterator();

    ArrayList<CockpitPlugin> plugins = new ArrayList<CockpitPlugin>();

    while (iterator.hasNext()) {
      plugins.add(iterator.next());
    }

    return plugins;
  }
}
