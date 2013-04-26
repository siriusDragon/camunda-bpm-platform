package org.camunda.bpm.cockpit.spi;

import org.camunda.bpm.cockpit.testplugin.SamplePlugin;
import java.util.List;

import org.camunda.bpm.cockpit.plugin.PluginRegistry;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

import org.camunda.bpm.cockpit.CockpitApplication;
import org.camunda.bpm.cockpit.testplugin.MyResource;
import org.fest.assertions.Condition;

/**
 *
 * @author nico.rehwaldt
 */
public class PluginDiscoveryTest {

  protected static Condition<List<?>> CONTAINS_SAMPLE_PLUGIN = new Condition<List<?>>() {

    @Override
    public boolean matches(List<?> value) {
      for (Object o: value) {
        if (o instanceof SamplePlugin) {
          return true;
        }
      }

      return false;
    }
  };

  @Test
  public void shouldDiscoverCockpitPlugin() {

    // given
    // SamplePlugin on class path

    // when
    List<CockpitPlugin> plugins = PluginRegistry.getCockpitPlugins();

    // then
    assertThat(plugins).satisfies(CONTAINS_SAMPLE_PLUGIN);
  }

  @Test
  public void shouldAddPluginRestResources() {

    // given
    // SamplePlugin on class path

    // when
    CockpitApplication application = new CockpitApplication();

    // then
    assertThat(application.getClasses()).contains(MyResource.class);
  }
}
