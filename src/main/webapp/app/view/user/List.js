Ext.define('E4ds.view.user.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'E4ds.controller.UserController', 'E4ds.ux.form.field.FilterField', 'E4ds.model.Role' ],
	controller: 'E4ds.controller.UserController',
	inject: 'rolesStore',
	title: i18n.user_users,
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('E4ds.store.Users');

		me.columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: app_context_path + '/resources/images/Arrowhead-Down-01-16.png',
				tooltip: 'Actions',
				handler: function(grid, rowIndex, colIndex, item, e, record, row) {
					this.getController().showContextMenu(record, null, row);
				},
				scope: this
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
			text: 'Roles',
			dataIndex: 'roleIds',
			sortable: false,
			width: 160,
			renderer: function(value, metadata, record) {
				var result = '';
				if (value) {
					for (var i = 0; i < value.length; i++) {
						if (result.length > 0) {
							result += ', ';
						}
						var role = this.rolesStore.getById(value[i]);
						if (role) {
							result += role.get('name');
						}
					}
				}
				metadata.tdAttr = 'data-qtip="' + result + '"';
				return result;
			}
		}, {
			text: i18n.user_enabled,
			dataIndex: 'enabled',
			width: 70,
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
				text: i18n.new_action,
				itemId: 'createButton',
				icon: app_context_path + '/resources/images/Add-New-16.png'
			}, '-', {
				text: i18n.excelexport,
				itemId: 'exportButton',
				icon: app_context_path + '/resources/images/Microsoft-Excel-2013-02-16.png',
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