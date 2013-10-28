Ext.define('BitP.view.bedarf.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'BitP.controller.BedarfController', 'BitP.ux.form.field.FilterField' ],
	controller: 'BitP.controller.BedarfController',

	title: 'Bedarfe',
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('BitP.store.Bedarfe');

		me.columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg==',
				tooltip: i18n.actions
			} ]
		}, {
			text: 'Titel',
			dataIndex: 'titel',
			flex: 1
		}, {
			text: 'Status',
			dataIndex: 'status',
			flex: 1
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.user_new,
				hidden: !(BitP.user.role === 'ROLE_BEDARF'),
				itemId: 'createButton',
				glyph: 0xe807
			}, '->', {
				itemId: 'filterField',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store
		} ];

		me.callParent(arguments);

	}

});