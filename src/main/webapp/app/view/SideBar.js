Ext.define('E4ds.view.SideBar', {
	extend: 'Ext.panel.Panel',
	requires: [ 'E4ds.view.poll.PollChart', 'E4ds.view.user.List', 'E4ds.view.accesslog.List', 'E4ds.view.logevent.List', 'E4ds.view.config.Edit' ],
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