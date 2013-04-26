package org.camunda.bpm.cockpit.plugin.impl.db;

import org.camunda.bpm.cockpit.plugin.api.db.QueryParameters;
import java.util.List;

import org.camunda.bpm.engine.impl.db.ListQueryParameterObject;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;

public class QueryService {

  private CommandExecutor commandExecutor;

  public QueryService(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  public <T> List<T> executeQuery(final String statement, final QueryParameters<T> parameter) {
    List<T> queryResult = commandExecutor.executeQueryCommand(new Command<List<T>>() {

      @SuppressWarnings("unchecked")
      public List<T> execute(CommandContext commandContext) {
        return (List<T>) commandContext.getDbSqlSession().selectList(statement, parameter);
      }

    });
    return queryResult;
  }

  public <T> T executeQuery(final String statement, final Object parameter, final Class<T> clazz) {
      T queryResult = commandExecutor.executeQueryCommand(new Command<T>() {

      @SuppressWarnings("unchecked")
      public T execute(CommandContext commandContext) {
        return (T) commandContext.getDbSqlSession().selectOne(statement, parameter);
      }

    });
    return queryResult;
  }

  public Long executeQueryRowCount(final String statement, final ListQueryParameterObject parameter) {
    Long queryResult = commandExecutor.executeQueryCommand(new Command<Long>() {

      public Long execute(CommandContext commandContext) {
        return (Long) commandContext.getDbSqlSession().selectOne(statement, parameter);
      }

    });
    return queryResult;
  }
}
