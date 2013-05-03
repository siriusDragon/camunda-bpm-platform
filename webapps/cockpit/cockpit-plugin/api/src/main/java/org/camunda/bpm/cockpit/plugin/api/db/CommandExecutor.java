/* Licensed under the Apache License, Version 2.0import org.camunda.bpm.engine.impl.interceptor.Command;
 compliance with the License.
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
package org.camunda.bpm.cockpit.plugin.api.db;

import org.camunda.bpm.engine.impl.interceptor.Command;

/**
 *
 * @author Nico Rehwaldt
 */
public interface CommandExecutor {

  /**
   * Execute the given command and return the result
   * of the execution
   *
   * @param <T>
   * @param command
   *
   * @return
   */
  public <T> T executeCommand(Command<T> command);
}