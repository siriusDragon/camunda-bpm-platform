"use strict";

define([ "angularModule" ], function(angularModule) {

  return angularModule("cockpit.plugin", [
    "cockpit/plugin/service",
    "cockpit/plugin/directive",
  ]);
});
