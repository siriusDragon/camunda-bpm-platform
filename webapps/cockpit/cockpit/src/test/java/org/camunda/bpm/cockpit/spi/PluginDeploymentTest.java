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

import java.util.List;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.cockpit.plugin.api.Cockpit;
import org.camunda.bpm.cockpit.plugin.api.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.api.db.QueryService;
import org.camunda.bpm.cockpit.plugin.core.CorePlugin;
import org.camunda.bpm.cockpit.testplugin.SampleProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.Execution;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Nico Rehwaldt
 */
@RunWith(Arquillian.class)
public class PluginDeploymentTest {

  @Deployment
  public static Archive<?> createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers
        .use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
    
    resolver.goOffline();

    return ShrinkWrap.create(WebArchive.class)
        .addAsLibraries(resolver.artifact("org.camunda.bpm.cockpit:camunda-cockpit-plugin-api").resolveAsFiles())
        .addAsLibraries(resolver.artifact("org.camunda.bpm.cockpit:camunda-cockpit-plugin-core").resolveAsFiles())
        .addAsLibraries(resolver.artifact("commons-io:commons-io").resolveAsFiles())
        .addPackages(true, "org.camunda.bpm.cockpit")
        .addClass(SampleProcessApplication.class)
        .addAsResource("processes/fox-invoice_en.bpmn")
        .addAsResource("META-INF/processes.xml", "META-INF/processes.xml");
  }

  @Test
  public void shouldContainCorePlugin () {
    List<CockpitPlugin> plugins = PluginRegistry.getCockpitPlugins();
    Assert.assertEquals(1, plugins.size());
    
    Assert.assertTrue(plugins.get(0) instanceof CorePlugin);
  }
  
  @Test
  public void shouldInitCommandExecutor() {
    ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();
    
    QueryService queryService = Cockpit.getQueryService(processEngine.getName());
    
    List<Execution> result = queryService.executeQuery("cockpit.core.selectExecution", new QueryParameters<Execution>());
    
    Assert.assertEquals(1, result.size());
  }
}
