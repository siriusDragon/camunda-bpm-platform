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
   * Returns the id of this plugin
   *
   * @return
   */
  public String getId();
}
