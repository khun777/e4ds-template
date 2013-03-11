Ext.define('E4ds.view.config.Edit', {
	extend: 'Ext.form.Panel',
	controller: 'E4ds.controller.ConfigController',

	title: i18n.config,
	closable: true,

	fieldDefaults: {
		msgTarget: 'side'
	},

	bodyPadding: 10,

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'combobox',
			itemId: 'logLevelCombobox',
			fieldLabel: i18n.config_loglevel,
			name: 'logLevel',
			labelWidth: 110,
			store: Ext.create('E4ds.store.LogLevels'),
			valueField: 'level',
			displayField: 'level',
			queryMode: 'local',
			forceSelection: true,
			value: 'ERROR'
		} ];

		me.callParent(arguments);
	}

});