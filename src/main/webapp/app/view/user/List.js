Ext.define('BitP.view.user.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'BitP.controller.User', 'BitP.ux.form.field.FilterField', 'BitP.store.Users' ],
	controller: 'BitP.controller.User',
	inject: 'rolesStore',
	title: i18n.user_users,
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('BitP.store.Users');

		me.columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg==',
				tooltip: i18n.actions
			} ]
		}, {
			text: i18n.user_username,
			dataIndex: 'userName',
			flex: 1
		}, {
			text: i18n.user_firstname,
			dataIndex: 'firstName',
			flex: 1
		}, {
			text: i18n.user_lastname,
			dataIndex: 'name',
			flex: 1
		}, {
			text: i18n.user_email,
			dataIndex: 'email',
			flex: 1
		}, {
			text: 'Role',
			dataIndex: 'role',
            flex: 1
		}, {
			text: i18n.user_enabled,
			dataIndex: 'enabled',
			width: 85,
			renderer: function(value) {
				if (value === true) {
					return i18n.yes;
				}
				return i18n.no;
			}
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.create,
				itemId: 'createButton',
				glyph: 0xe807
			}, '-', {
				text: i18n.excelexport,
				itemId: 'exportButton',
				glyph: 0xe813,
				href: 'usersExport.xlsx',
				hrefTarget: '_self'
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

		me.listeners = {
			filterchange: function(store) {
				me.down('#exportButton').setDisabled(store.getCount() > 0);
			}
		};

		me.callParent(arguments);

	}

});