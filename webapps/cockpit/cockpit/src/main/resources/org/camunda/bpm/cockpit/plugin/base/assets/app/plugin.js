'use strict';

define([ "angularModule" ], function(angularModule) {

  var module = angularModule("cockpit-plugin-base");

  function DashboardProcessDefinitionController($scope, Uri) {

  }

  DashboardProcessDefinitionController.$inject = [ '$scope', 'Uri' ];

  var PluginConfiguration = function PluginConfiguration(PluginsProvider) {

    PluginsProvider.registerDefaultPlugin('cockpit.dashboard', {
      id: 'process-definitions',
      label: 'Deployed Processes',
      url: 'app://api/plugin/base/static/partials/dashboard-process-definitions.html',
      controller: DashboardProcessDefinitionController
    });
  };

  PluginConfiguration.$inject = ['PluginsProvider'];

  module.value("FOO", "BAR");

  module.config(PluginConfiguration);

  return module;
});
