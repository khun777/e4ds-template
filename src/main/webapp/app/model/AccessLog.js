Ext.define("E4ds.model.AccessLog",
{
  extend : "Ext.data.Model",
  fields : [ {
    name : "userName",
    type : "string"
  }, {
    name : "logIn",
    dateFormat : "c",
    type : "date"
  }, {
    name : "logOut",
    dateFormat : "c",
    type : "date"
  }, {
    name : "duration",
    type : "string"
  }, {
    name : "browser",
    type : "string"
  }, {
    name : "id",
    useNull : true,
    convert : null,
    type : "int"
  } ],
  proxy : {
    type : "direct",
    directFn : "accessLogService.read",
    reader : {
      root : "records"
    }
  }
});