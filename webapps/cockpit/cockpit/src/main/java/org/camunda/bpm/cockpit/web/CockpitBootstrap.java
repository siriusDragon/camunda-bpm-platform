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
package org.camunda.bpm.cockpit.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.camunda.bpm.cockpit.plugin.DefaultRuntimeDelegate;
import org.camunda.bpm.cockpit.plugin.api.Cockpit;

/**
 *
 * @author Nico Rehwaldt
 */
@WebListener
public class CockpitBootstrap implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Cockpit.setCockpitRuntimeDelegate(new DefaultRuntimeDelegate());

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }

}
