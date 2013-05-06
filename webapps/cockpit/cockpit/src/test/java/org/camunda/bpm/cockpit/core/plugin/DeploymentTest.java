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

import static org.camunda.bpm.cockpit.test.util.DeploymentUtil.*;

import static org.fest.assertions.Assertions.assertThat;

import org.camunda.bpm.cockpit.plugin.api.Cockpit;
import org.camunda.bpm.cockpit.plugin.api.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.api.db.QueryService;

import java.util.List;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.cockpit.plugin.spi.CockpitPlugin;
import org.camunda.bpm.cockpit.test.plugin.TestPlugin;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Execution;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author nico.rehwaldt
 */
@RunWith(Arquillian.class)
public class DeploymentTest {

  @Deployment
  public static Archive<?> createDeployment() {

    WebArchive archive = cockpitWar()
          .addAsLibraries(festAssertions())
          .addAsLibraries(resteasyJaxRs())
          .addAsLibraries(testPluginJar())
          .addAsLibraries(testProcessArchiveJar());

    System.out.println(archive.toString(true));
    System.out.println(testPlugin().toString(true));
    System.out.println(testProcessArchive().toString(true));

    return archive;
  }

  @Test
  public void shouldContainCorePlugin () {
    List<CockpitPlugin> plugins = Registry.getCockpitPlugins();
    assertThat(plugins).hasSize(1);

    assertThat(plugins.get(0)).isInstanceOf(TestPlugin.class);
  }

  @Test
  public void shouldInitCommandExecutor() {
    ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();

    QueryService queryService = Cockpit.getQueryService(processEngine.getName());

    List<Execution> result = queryService.executeQuery("cockpit.core.selectExecution", new QueryParameters<Execution>());

    assertThat(result).hasSize(1);
  }
}
