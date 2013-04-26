package org.camunda.bpm.cockpit.spi;

import static org.camunda.bpm.cockpit.web.CockpitBootstrap.initCommandExecutor;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
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
        .addPackages(true, "org.camunda.bpm.cockpit.plugin");
  }

  @Test
  public void shouldInitCommandExecutor() {
    ProcessEngine processEngine = BpmPlatform.getDefaultProcessEngine();

    initCommandExecutor(processEngine);
  }
}
