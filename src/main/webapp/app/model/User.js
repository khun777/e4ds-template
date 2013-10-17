Ext.define("E4ds.model.User",
{
  extend : "Ext.data.Model",
  uses : [ "E4ds.model.Role" ],
  fields : [ {
    name : "userName",
    type : "string"
  }, {
    name : "name",
    type : "string"
  }, {
    name : "firstName",
    type : "string"
  }, {
    name : "email",
    type : "string"
  }, {
    name : "passwordNew",
    type : "string"
  }, {
    name : "passwordNewConfirm",
    type : "string"
  }, {
    name : "oldPassword",
    type : "string"
  }, {
    name : "locale",
    type : "string"
  }, {
    name : "enabled",
    type : "boolean"
  }, {
    name : "roleIds",
    type : "auto"
  }, {
    name : "id",
    type : "int",
    useNull : true
  } ],
  associations : [ {
    type : "hasMany",
    model : "E4ds.model.Role",
    associationKey : "roles",
    foreignKey : "user_id",
    name : "roles"
  } ],
  proxy : {
    type : "direct",
    api : {
      read : "userService.read",
      create : "userService.create",
      update : "userService.update",
      destroy : "userService.destroy"
    },
    reader : {
      root : "records"
    }
  }
});