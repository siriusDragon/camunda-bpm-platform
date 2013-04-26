package org.camunda.bpm.cockpit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.camunda.bpm.engine.rest.exception.ExceptionHandler;
import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator;
import org.camunda.bpm.cockpit.resources.PluginsResource;
import org.camunda.bpm.cockpit.spi.CockpitPlugin;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 *
 * @author Nico Rehwaldt
 */
public class CockpitApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(PluginsResource.class);

    classes.add(JacksonConfigurator.class);
    classes.add(JacksonJsonProvider.class);
    classes.add(ExceptionHandler.class);

    addPluginResourceClasses(classes);
    return classes;
  }

  private void addPluginResourceClasses(Set<Class<?>> classes) {

    List<CockpitPlugin> plugins = PluginRegistry.getCockpitPlugins();

    for (CockpitPlugin plugin : plugins) {
      classes.addAll(plugin.getResourceClasses());
    }
  }
}
