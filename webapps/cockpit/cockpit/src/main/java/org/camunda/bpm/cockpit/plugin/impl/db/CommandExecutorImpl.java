/* Licensed under the Apache License, Version 2.0 import java.util.List;

import org.camunda.bpm.cockpit.plugin.api.db.CommandExecutor;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
icable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.cockpit.plugin.impl.db;

import java.util.List;

import org.camunda.bpm.cockpit.plugin.api.db.CommandExecutor;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;

/**
 *
 * @author drobisch
 * @author nico.rehwaldt
 */
public class CommandExecutorImpl implements CommandExecutor {

  private QuerySessionFactory sessionFactory;

  public CommandExecutorImpl() { }

  public CommandExecutorImpl(ProcessEngineConfigurationImpl processEngineConfiguration, List<String> mappingFiles) {
    sessionFactory = new QuerySessionFactory();
    sessionFactory.initFromProcessEngineConfiguration(processEngineConfiguration, mappingFiles);
  }

  @Override
  public <T> T executeCommand(Command<T> command) {
    return sessionFactory.getCommandExecutorTxRequired().execute(command);
  }
}

