package org.camunda.bpm.cockpit.core.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.cockpit.plugin.core.Registry;
import org.camunda.bpm.cockpit.plugin.core.spi.CockpitPlugin;

/**
 *
 * @author nico.rehwaldt
 */
@WebFilter(urlPatterns = { "/app/bootstrap.js", "/app/cockpit.js" }, dispatcherTypes = DispatcherType.REQUEST)
public class PluginBootstrapFilter implements Filter {

  private final String PLUGIN_DEPENDENCIES = "PLUGIN_DEPENDENCIES";
  private final String PLUGIN_PACKAGES = "PLUGIN_PACKAGES";

  @Override
  public void init(FilterConfig fc) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    filterInjectPlugins((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }

  public void filterInjectPlugins(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


    InputStream is = null;

    try {
      is = request.getServletContext().getResourceAsStream(request.getRequestURI().replaceFirst(request.getContextPath(), ""));
      System.out.println(is);
      System.out.println(request.getRequestURI());
      System.out.println(request.getRequestURI().replaceFirst(request.getContextPath(), ""));

      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      StringWriter writer = new StringWriter();
      String line = null;

      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.append("\n");
      }

      String data = writer.toString();

      data = data.replace(PLUGIN_PACKAGES, createPluginPackagesStr());

      data = data.replace(PLUGIN_DEPENDENCIES, createPluginDependenciesStr());

      response.setContentLength(data.getBytes("UTF-8").length);
      response.setContentType("text/javascript;charset=UTF-8");

      response.getWriter().append(data);
    } finally {
      if (is != null) {
        try { is.close(); } catch (IOException e) {}
      }
    }
  }

  @Override
  public void destroy() {
  }

  private CharSequence createPluginPackagesStr() {
    final List<CockpitPlugin> plugins = Registry.getCockpitPlugins();

    StringBuilder builder = new StringBuilder();

    for (CockpitPlugin plugin : plugins) {
      if (builder.length() > 0) {
        builder.append(", ").append("\n");
      }

      String definition = String.format("{ name: 'cockpit-plugin-%s', location: 'api/plugin/%s/static/app', main: 'plugin.js' }", plugin.getId(), plugin.getId());

      builder.append(definition);
    }

    return "[" + builder.toString() + "]";
  }

  private CharSequence createPluginDependenciesStr() {
     final List<CockpitPlugin> plugins = Registry.getCockpitPlugins();

    StringBuilder builder = new StringBuilder();

    for (CockpitPlugin plugin : plugins) {
      if (builder.length() > 0) {
        builder.append(", ").append("\n");
      }

      String definition = String.format("'cockpit-plugin-%s'", plugin.getId());

      builder.append(definition);
    }

    return "[" + builder.toString() + "]";
  }
}
