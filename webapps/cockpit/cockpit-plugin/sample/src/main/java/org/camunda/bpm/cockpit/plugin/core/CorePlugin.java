package org.camunda.bpm.cockpit.plugin.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

/**
 *
 * @author Nico Rehwaldt
 */
public class CorePlugin extends AbstractCockpitPlugin {

  private static final String[] MAPPING_FILES = {
//    "org/camunda/bpm/cockpit/plugin/sample/db/mappings/auditMapping.xml",
//    "org/camunda/bpm/cockpit/plugin/sample/db/mappings/processInstanceMapping.xml",
//    "org/camunda/bpm/cockpit/plugin/sample/db/mappings/variableMapping.xml",

    "org/camunda/bpm/cockpit/plugin/sample/db/mappings/simple.xml"
  };
  
  @Override
  public String getId() {
    return "core";
  }

  @Override
  public List<String> getMappingFiles() {
    return Arrays.asList(MAPPING_FILES);
  }


}
