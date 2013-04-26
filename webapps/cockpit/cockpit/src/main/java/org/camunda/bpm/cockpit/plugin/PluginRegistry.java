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
