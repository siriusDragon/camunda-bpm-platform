package org.camunda.bpm.engine.rest;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.helper.EqualsMap;
import org.camunda.bpm.engine.rest.helper.MockProvider;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractProcessEngineRestServiceTest extends
    AbstractRestServiceTest {

  protected static final String ENGINES_URL = TEST_RESOURCE_ROOT_PATH + "/engine";
  protected static final String SINGLE_ENGINE_URL = ENGINES_URL + "/{name}";
  protected static final String PROCESS_DEFINITION_URL = SINGLE_ENGINE_URL + "/process-definition/{id}";
  protected static final String PROCESS_INSTANCE_URL = SINGLE_ENGINE_URL + "/process-instance/{id}";
  protected static final String TASK_URL = SINGLE_ENGINE_URL + "/task/{id}";
  protected static final String IDENTITY_GROUPS_URL = SINGLE_ENGINE_URL + "/identity/groups";
  protected static final String MESSAGE_URL = SINGLE_ENGINE_URL + "/message";
  
  protected String EXAMPLE_ENGINE_NAME = "anEngineName";

  private ProcessEngine namedProcessEngine;
  private RepositoryService mockRepoService;
  private RuntimeService mockRuntimeService;
  private TaskService mockTaskService;
  private IdentityService mockIdentityService;

  @Before
  public void setUpRuntimeData() {
    namedProcessEngine = getProcessEngine(EXAMPLE_ENGINE_NAME);
    mockRepoService = mock(RepositoryService.class);
    mockRuntimeService = mock(RuntimeService.class);
    mockTaskService = mock(TaskService.class);
    mockIdentityService = mock(IdentityService.class);
    
    when(namedProcessEngine.getRepositoryService()).thenReturn(mockRepoService);
    when(namedProcessEngine.getRuntimeService()).thenReturn(mockRuntimeService);
    when(namedProcessEngine.getTaskService()).thenReturn(mockTaskService);
    when(namedProcessEngine.getIdentityService()).thenReturn(mockIdentityService);
    
    createProcessDefinitionMock();
    createProcessInstanceMock();
    createTaskMock();
    createIdentityMocks();
  }
  

  private void createProcessDefinitionMock() {
    ProcessDefinition mockDefinition = MockProvider.createMockDefinition();
    
    when(mockRepoService.getProcessDefinition(eq(MockProvider.EXAMPLE_PROCESS_DEFINITION_ID))).thenReturn(mockDefinition);
  }
  
  private void createProcessInstanceMock() {
    ProcessInstance mockInstance = MockProvider.createMockInstance();
    
    ProcessInstanceQuery mockInstanceQuery = mock(ProcessInstanceQuery.class);
    when(mockInstanceQuery.processInstanceId(eq(MockProvider.EXAMPLE_PROCESS_INSTANCE_ID))).thenReturn(mockInstanceQuery);
    when(mockInstanceQuery.singleResult()).thenReturn(mockInstance);
    when(mockRuntimeService.createProcessInstanceQuery()).thenReturn(mockInstanceQuery);
  }
  
  private void createTaskMock() {
    Task mockTask = MockProvider.createMockTask();
    
    TaskQuery mockTaskQuery = mock(TaskQuery.class);
    when(mockTaskQuery.taskId(eq(MockProvider.EXAMPLE_TASK_ID))).thenReturn(mockTaskQuery);
    when(mockTaskQuery.singleResult()).thenReturn(mockTask);
    when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
  }
  
  private void createIdentityMocks() {
    // identity
    UserQuery sampleUserQuery = mock(UserQuery.class);
    GroupQuery sampleGroupQuery = mock(GroupQuery.class);
    List<User> mockUsers = new ArrayList<User>();
    User mockUser = MockProvider.createMockUser();
    mockUsers.add(mockUser);
    
    // user query
    when(sampleUserQuery.memberOfGroup(anyString())).thenReturn(sampleUserQuery);
    when(sampleUserQuery.list()).thenReturn(mockUsers);
    
    // group query
    List<Group> mockGroups = MockProvider.createMockGroups();
    when(sampleGroupQuery.groupMember(anyString())).thenReturn(sampleGroupQuery);
    when(sampleGroupQuery.orderByGroupName()).thenReturn(sampleGroupQuery);
    when(sampleGroupQuery.asc()).thenReturn(sampleGroupQuery);
    when(sampleGroupQuery.list()).thenReturn(mockGroups);
    
    when(mockIdentityService.createGroupQuery()).thenReturn(sampleGroupQuery);
    when(mockIdentityService.createUserQuery()).thenReturn(sampleUserQuery);
  }
  

  @Test
  public void testNonExistingEngineAccess() {
    given().pathParam("name", MockProvider.NON_EXISTING_PROCESS_ENGINE_NAME)
      .pathParam("id", MockProvider.EXAMPLE_PROCESS_DEFINITION_ID)
    .then().expect()
      .statusCode(Status.BAD_REQUEST.getStatusCode())
    .when().get(PROCESS_DEFINITION_URL);
  }
  
  @Test
  public void testEngineNamesList() {
    expect()
      .statusCode(Status.OK.getStatusCode())
      .body("$.size()", is(2))
      .body("[0].name", equalTo(MockProvider.EXAMPLE_PROCESS_ENGINE_NAME))
      .body("[1].name", equalTo(MockProvider.ANOTHER_EXAMPLE_PROCESS_ENGINE_NAME))
    .when().get(ENGINES_URL);
  }
  
  @Test
  public void testProcessDefinitionServiceEngineAccess() {
    given().pathParam("name", EXAMPLE_ENGINE_NAME)
      .pathParam("id", MockProvider.EXAMPLE_PROCESS_DEFINITION_ID)
    .then().expect()
      .statusCode(Status.OK.getStatusCode())
    .when().get(PROCESS_DEFINITION_URL);
    
    verify(namedProcessEngine).getRepositoryService();
    verify(mockRepoService).getProcessDefinition(eq(MockProvider.EXAMPLE_PROCESS_DEFINITION_ID));
    verifyZeroInteractions(processEngine);
  }
  
  @Test
  public void testProcessInstanceServiceEngineAccess() {
    given().pathParam("name", EXAMPLE_ENGINE_NAME)
      .pathParam("id", MockProvider.EXAMPLE_PROCESS_INSTANCE_ID)
    .then().expect()
      .statusCode(Status.OK.getStatusCode())
    .when().get(PROCESS_INSTANCE_URL);
    
    verify(namedProcessEngine).getRuntimeService();
    verifyZeroInteractions(processEngine);
  }
  
  @Test
  public void testTaskServiceEngineAccess() {
    given().pathParam("name", EXAMPLE_ENGINE_NAME)
      .pathParam("id", MockProvider.EXAMPLE_TASK_ID)
    .then().expect()
      .statusCode(Status.OK.getStatusCode())
    .when().get(TASK_URL);
    
    verify(namedProcessEngine).getTaskService();
    verifyZeroInteractions(processEngine);
  }
  
  @Test
  public void testIdentityServiceEngineAccess() {
    given().pathParam("name", EXAMPLE_ENGINE_NAME)
      .queryParam("userId", "someId")
    .then().expect()
      .statusCode(Status.OK.getStatusCode())
    .when().get(IDENTITY_GROUPS_URL);
    
    verify(namedProcessEngine).getIdentityService();
    verifyZeroInteractions(processEngine);
  }
  
  @Test
  public void testMessageServiceEngineAccess() {
    String messageName = "aMessage";
    Map<String, Object> messageParameters = new HashMap<String, Object>();
    messageParameters.put("messageName", messageName);
    
    given().contentType(POST_JSON_CONTENT_TYPE).body(messageParameters).pathParam("name", EXAMPLE_ENGINE_NAME)
      .then().expect().statusCode(Status.NO_CONTENT.getStatusCode())
      .when().post(MESSAGE_URL);

    verify(mockRuntimeService).correlateMessage(eq(messageName), eq((String) null), 
        argThat(new EqualsMap(null)), argThat(new EqualsMap(null)));
    verifyZeroInteractions(processEngine);
  }
}
