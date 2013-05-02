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
package org.camunda.bpm.cockpit.core.plugin;

import static org.camunda.bpm.cockpit.test.util.CockpitResources.festAssertions;
import static org.camunda.bpm.cockpit.test.util.CockpitResources.resteasy;
import static org.camunda.bpm.cockpit.test.util.CockpitResources.pluginApi;
import static org.camunda.bpm.cockpit.test.util.CockpitResources.testPluginJar;

import org.camunda.bpm.cockpit.test.plugin.TestPlugin;
import java.util.List;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

import org.camunda.bpm.cockpit.core.web.CockpitApplication;
import org.camunda.bpm.cockpit.plugin.spi.CockpitPlugin;
import org.camunda.bpm.cockpit.test.plugin.resources.TestResource;
import org.fest.assertions.Condition;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

/**
 *
 * @author nico.rehwaldt
 */
@RunWith(Arquillian.class)
public class DiscoveryTest {

  @Deployment
  public static Archive<?> createDeployment() {
    WebArchive archive = ShrinkWrap.create(WebArchive.class)
                      .addAsLibraries(festAssertions())
                      .addAsLibraries(pluginApi())
                      .addAsLibraries(resteasy())
                      .addAsLibraries(testPluginJar())
                      .addAsServiceProvider(CockpitPlugin.class, TestPlugin.class)
                      .addPackages(true, "org.camunda.bpm.cockpit.core");

    return archive;
  }

  protected static Condition<List<?>> CONTAINS_PLUGIN = new Condition<List<?>>() {

    @Override
    public boolean matches(List<?> value) {
      for (Object o: value) {
        if (o instanceof TestPlugin) {
          return true;
        }
      }

      return false;
    }
  };

  @Test
  public void shouldDiscoverCockpitPlugin() {

    // given
    // plugin on class path

    // when
    List<CockpitPlugin> plugins = Registry.getCockpitPlugins();

    // then
    assertThat(plugins).satisfies(CONTAINS_PLUGIN);
  }

  @Test
  public void shouldAddPluginRestResources() {

    // given
    // TestPlugin on class path

    // when
    CockpitApplication application = new CockpitApplication();

    // then
    assertThat(application.getClasses()).contains(TestResource.class);
  }
}
