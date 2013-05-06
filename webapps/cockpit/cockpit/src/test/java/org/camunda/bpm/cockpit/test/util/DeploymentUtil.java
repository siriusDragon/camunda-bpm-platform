package org.camunda.bpm.cockpit.test.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.cockpit.core.web.CockpitApplication;
import org.camunda.bpm.cockpit.core.web.CockpitBootstrap;
import org.camunda.bpm.cockpit.plugin.spi.CockpitPlugin;
import org.camunda.bpm.cockpit.test.pa.TestProcessApplication;
import org.camunda.bpm.cockpit.test.plugin.TestPlugin;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

/**
 *
 * @author nico.rehwaldt
 */
public class DeploymentUtil {

  /**
   * Resolver to resolve external maven dependencies
   *
   * @return
   */
  public static MavenDependencyResolver resolver() {

    return DependencyResolvers
        .use(MavenDependencyResolver.class)
        .goOffline()
        .loadMetadataFromPom("pom.xml");
  }

  /**
   * Cockpit plugin core classes
   *
   * @return
   */
  public static List<Class<?>> pluginCore() {

    return Arrays.asList();
  }

  /**
   * Cockpit plugin api
   *
   * @return
   */
  public static File[] pluginApi() {
    return mavenDependency("org.camunda.bpm.cockpit:camunda-cockpit-plugin-api");
  }

  public static File[] resteasy() {

    return resolver().artifacts("org.jboss.resteasy:resteasy-jaxrs").exclusion("org.apache.httpcomponents:httpclient").resolveAsFiles();
  }

  public static File[] festAssertions() {
    return mavenDependency("org.easytesting:fest-assert");
  }

  public static File[] resteasyJaxRs() {
    return mavenDependency("org.jboss.resteasy:resteasy-jaxrs");
  }

  public static File[] mavenDependency(String name) {
    return resolver().artifact(name).resolveAsFiles();
  }

  public static File[] mavenDependencies(String... names) {
    return resolver().artifacts(names).resolveAsFiles();
  }

  public static JavaArchive cockpitCore() {
    JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "cockpit-core")
        .addClass(CockpitBootstrap.class)
        .addClass(CockpitApplication.class);

    return archive;
  }

  public static WebArchive cockpitWar() {
    return ShrinkWrap
        .create(WebArchive.class, "test")
          .addAsLibraries(pluginApi())
          .addAsLibraries(cockpitCore())
          .addPackages(true, "org.camunda.bpm.cockpit.core")
          .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
  }

  public static JavaArchive testProcessArchive() {
    JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "sample-process-application")
        .addClass(TestProcessApplication.class);

    addFiles(archive, "", new File("src/test/resources/process-archive"));

    return archive;
  }

  public static File[] testProcessArchiveJar() {
    testProcessArchive().as(ZipExporter.class)
        .exportTo(new File("target/test-process-archive.jar"), true);

    return new File[] { new File("target/test-process-archive.jar") };
  }

  public static JavaArchive testPlugin() {
    JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "sample-plugin")
        .addPackages(true, TestPlugin.class.getPackage())
        .addAsServiceProvider(CockpitPlugin.class, TestPlugin.class);

    addFiles(archive, "org/camunda/bpm/cockpit/test/plugin", new File("src/test/resources/org/camunda/bpm/cockpit/test/plugin"));

    return archive;
  }

  public static File[] testPluginJar() {
    testPlugin().as(ZipExporter.class)
        .exportTo(new File("target/test-plugin.jar"), true);

    return new File[] { new File("target/test-plugin.jar") };
  }

  private static void addFiles(JavaArchive archive, String prefix, File dir) {
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException("not a directory");
    }

    addFiles(archive, prefix, dir.getPath(), dir);
  }

  private static void addFiles(JavaArchive archive, String prefix, String rootPath, File dir) {

    for (File f : dir.listFiles()) {
      if (f.isFile()) {
        String filePath = f.getPath().replace(rootPath, "").replace("\\", "/");

        archive.addAsResource(f, prefix + filePath);
      } else {
        addFiles(archive, prefix, rootPath, f);
      }
    }
  }
}
