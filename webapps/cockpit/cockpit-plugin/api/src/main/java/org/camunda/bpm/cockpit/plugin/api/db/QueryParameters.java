package org.camunda.bpm.cockpit.plugin.api.db;

import org.camunda.bpm.engine.impl.db.ListQueryParameterObject;

/**
 * Typed query parameters for usage in cockpit plugins
 *
 * @author Nico Rehwaldt
 * @param <T>
 */
public class QueryParameters<T> extends ListQueryParameterObject {

  protected boolean historyEnabled = true;

  public QueryParameters() { }

  public QueryParameters(int firstResult, int maxResults) {
    this.firstResult = firstResult;
    this.maxResults = maxResults;
  }

  public boolean isHistoryEnabled() {
    return historyEnabled;
  }

  public void setHistoryEnabled(boolean historyEnabled) {
    this.historyEnabled = historyEnabled;
  }
}
