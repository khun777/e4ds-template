Ext.define('E4ds.view.Viewport', {
	extend: 'Ext.Viewport',
	controller: 'E4ds.controller.NavigationController',
	requires: [ 'Ext.ux.TabReorderer', 'Ext.ux.TabCloseMenu', 'E4ds.view.navigation.Header', 'E4ds.view.navigation.SideBar', 'E4ds.store.Navigation' ],

	style: {
		backgroundColor: 'white'
	},

	layout: {
		type: 'border',
		padding: 5
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		var me = this;

		var tabCloseMenu = Ext.create('Ext.ux.TabCloseMenu');
		tabCloseMenu.closeTabText = i18n.tabclosemenu_close;
		tabCloseMenu.closeOthersTabsText = i18n.tabclosemenu_closeother;
		tabCloseMenu.closeAllTabsText = i18n.tabclosemenu_closeall;

		me.items = [ Ext.create('E4ds.view.navigation.Header', {
			region: 'north',
			split: false
		}), {
			region: 'center',
			xtype: 'tabpanel',
			itemId: 'tabPanel',
			plugins: [ Ext.create('Ext.ux.TabReorderer'), tabCloseMenu ],
			plain: true
		}, Ext.create('E4ds.view.navigation.SideBar', {
			region: 'west',
			width: 180
		}) ];

		me.callParent(arguments);
	}

});