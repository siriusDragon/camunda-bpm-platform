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
package org.camunda.bpm.engine.rest.spi.impl;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.rest.exception.RestException;
import org.camunda.bpm.engine.rest.spi.ProcessEngineProvider;

public abstract class AbstractProcessEngineAware {

  protected ProcessEngine processEngine;
  
  public AbstractProcessEngineAware() {
    processEngine = lookupProcessEngine(null);
  }
  
  public AbstractProcessEngineAware(String engineName) {
    processEngine = lookupProcessEngine(engineName);
  }
  
  protected ProcessEngine lookupProcessEngine(String engineName) {
    
    ServiceLoader<ProcessEngineProvider> serviceLoader = ServiceLoader.load(ProcessEngineProvider.class);
    Iterator<ProcessEngineProvider> iterator = serviceLoader.iterator();
    
    if(iterator.hasNext()) {
      ProcessEngineProvider provider = iterator.next();
      if (engineName == null) {
        return provider.getDefaultProcessEngine();
      } else {
        return provider.getProcessEngine(engineName);
      }
    } else {
      throw new RestException("Could not find an implementation of the "+ProcessEngineProvider.class+"- SPI");
    }

  }
  
}
