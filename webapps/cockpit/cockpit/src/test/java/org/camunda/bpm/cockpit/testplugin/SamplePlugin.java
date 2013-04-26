package org.camunda.bpm.cockpit.testplugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.spi.CockpitPlugin;

/**
 *
 * @author Nico Rehwaldt
 */
public class SamplePlugin implements CockpitPlugin {

  @Override
  public String getId() {
    return "sample-plugin";
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    final HashSet<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(MyResource.class);
    classes.add(SamplePluginResource.class);

    return classes;
  }

  @Override
  public List<String> getMappingFiles() {
    return Collections.EMPTY_LIST;
  }
}
