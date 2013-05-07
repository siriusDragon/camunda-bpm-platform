package org.camunda.bpm.cockpit.plugin.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.plugin.base.resources.BaseRootResource;
import org.camunda.bpm.cockpit.plugin.core.spi.impl.AbstractCockpitPlugin;

/**
 *
 * @author nico.rehwaldt
 */
public class BasePlugin extends AbstractCockpitPlugin {

  public static final String ID = "base";

  private static final String[] MAPPING_FILES = {
    "org/camunda/bpm/cockpit/plugin/base/queries/simple.xml"
  };

  @Override
  public Set<Class<?>> getResourceClasses() {
    HashSet<Class<?>> classes = new HashSet();

    classes.add(BaseRootResource.class);

    return classes;
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public List<String> getMappingFiles() {
    return Arrays.asList(MAPPING_FILES);
  }
}
