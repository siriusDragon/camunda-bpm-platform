'use strict';

define([ "angularModule" ], function(angularModule) {

  var cockpitCore = [
      'cockpit.pages',
      'cockpit.directives',
      'cockpit.resources',
      'cockpit.plugin' ];

  var commons = [
      'common.directives',
      'common.extensions',
      'common.resources',
      'common.services' ];

  var plugins = PLUGIN_DEPENDENCIES || [];

  var module =
    angularModule("cockpit", [ "ng", "ngResource" ].concat(commons, cockpitCore, plugins));

  console.log(module);

  var Controller = function ($scope, Errors) {

    $scope.appErrors = function () {
      return Errors.errors;
    };

    $scope.removeError = function (error) {
      Errors.clear(error);
    };

    // needed for form validation
    // DO NOT REMOVE FROM DEFAULT CONTROLLER!
    $scope.errorClass = function(form) {
      return form.$valid || !form.$dirty ? '' : 'error';
    };

  };

  Controller.$inject = ["$scope", "Errors"];

  var ModuleConfig = function($routeProvider, $httpProvider) {
    $httpProvider.responseInterceptors.push('httpStatusInterceptor');
    $routeProvider.otherwise({ redirectTo: "/dashboard" });
  };

  ModuleConfig.$inject = ["$routeProvider", "$httpProvider"];

  module
    .config(ModuleConfig)
    .controller('DefaultCtrl', Controller);

  return module;

});
