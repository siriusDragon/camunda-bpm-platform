package org.camunda.bpm.cockpit.spi;

import java.util.List;
import java.util.Set;

/**
 *
 * @author nico.rehwaldt
 */
public interface CockpitPlugin {

  /**
   * Returns a set of resource classes
   *
   * @return
   */
  public Set<Class<?>> getResourceClasses();

  /**
   * Returns a list of mapping files for custom queries
   * provided by this plugin
   *
   * @return
   */
  public List<String> getMappingFiles();

  /**
   * Returns a uri to a plugins asset directory.
   * The directory must be unique across all plugins.
   *
   * Typically <code>org.camunda.bpm.cockpit.plugin.{name}.assets</code>.
   *
   * @return the directory to search assets under
   */
  public String getAssetDirectory();

  /**
   * Returns the id of this plugin
   *
   * @return
   */
  public String getId();
}
