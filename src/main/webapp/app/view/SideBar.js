Ext.define('E4ds.view.SideBar', {
	extend: 'Ext.panel.Panel',

	title: i18n.navigation,
	collapsible: true,
	layout: 'fit',
	minWidth: 100,
	maxWidth: 200,

	initComponent: function() {
		var me = this;
		me.items = [ {
			xtype: 'treepanel',
			itemId: 'menuTree',
			border: 0,
			store: Ext.create('E4ds.store.Navigation'),
			rootVisible: false,
			animate: false
		} ];

		me.callParent(arguments);
	}
});