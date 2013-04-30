package org.camunda.bpm.cockpit.spi;

import static org.camunda.bpm.cockpit.web.CockpitBootstrap.initCommandExecutor;

import java.util.List;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.cockpit.plugin.core.CorePlugin;
import org.camunda.bpm.engine.ProcessEngine;
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

    return ShrinkWrap.create(WebArchive.class)
        .addAsLibraries(resolver.artifact("org.camunda.bpm.cockpit:camunda-cockpit-plugin-api").resolveAsFiles())
        .addAsLibraries(resolver.artifact("org.camunda.bpm.cockpit:camunda-cockpit-plugin-core").resolveAsFiles())
        .addAsLibraries(resolver.artifact("commons-io:commons-io").resolveAsFiles())
        .addPackages(true, "org.camunda.bpm.cockpit");
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

    initCommandExecutor(processEngine);
  }
}
