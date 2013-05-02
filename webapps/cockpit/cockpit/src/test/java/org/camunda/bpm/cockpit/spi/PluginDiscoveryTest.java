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
package org.camunda.bpm.cockpit.spi;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.camunda.bpm.cockpit.CockpitApplication;
import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.cockpit.testplugin.MyResource;
import org.camunda.bpm.cockpit.testplugin.SamplePlugin;
import org.fest.assertions.Condition;
import org.junit.Test;

/**
 *
 * @author nico.rehwaldt
 */
public class PluginDiscoveryTest {

  protected static Condition<List<?>> CONTAINS_SAMPLE_PLUGIN = new Condition<List<?>>() {

    @Override
    public boolean matches(List<?> value) {
      for (Object o: value) {
        if (o instanceof SamplePlugin) {
          return true;
        }
      }

      return false;
    }
  };

  @Test
  public void shouldDiscoverCockpitPlugin() {

    // given
    // SamplePlugin on class path

    // when
    List<CockpitPlugin> plugins = PluginRegistry.getCockpitPlugins();

    // then
    assertThat(plugins).satisfies(CONTAINS_SAMPLE_PLUGIN);
  }

  @Test
  public void shouldAddPluginRestResources() {

    // given
    // SamplePlugin on class path

    // when
    CockpitApplication application = new CockpitApplication();

    // then
    assertThat(application.getClasses()).contains(MyResource.class);
  }
}
