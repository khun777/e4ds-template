Ext.define('E4ds.controller.UserController', {
	extend: 'Deft.mvc.ViewController',
	inject: 'rolesStore',
	requires: [ 'E4ds.view.user.Edit' ],
	control: {
		view: {
			removed: 'onRemoved',
			itemdblclick: 'onItemDblClick',
			itemcontextmenu: 'onItemContextMenu'
		},
		createButton: {
			click: 'onCreateButtonClick'
		},
		filterField: {
			filter: 'onFilterField'
		},
		exportButton: true
	},

	init: function() {
		var store = this.getView().getStore();
		store.clearFilter(true);
		store.load();
	},

	destroy: function() {		
		if (this.actionMenu) {
			this.actionMenu.destroy();
		}		
		return this.callParent();
	},

	onItemContextMenu: function(view, record, item, index, e, eOpts) {
		e.stopEvent();
		this.showContextMenu(record, e.getXY());
	},

	showContextMenu: function(record, xy, item) {
		var me = this;
		var items = [ {
			text: i18n.edit_action,
			icon: app_context_path + '/resources/images/Data-Edit-16.png',
			handler: Ext.bind(me.editUser, me, [record])
		}, {
			text: i18n.delete_action,
			icon: app_context_path + '/resources/images/Garbage-16.png',
			handler: Ext.bind(me.destroyUser, me, [record])
		}, {
			xtype: 'menuseparator'
		}, {
			text: i18n.user_switchto,
			icon: app_context_path + '/resources/images/Policeman-16.png',
			handler: Ext.bind(me.switchTo, me, [record])
		} ];

		if (this.actionMenu) {
			this.actionMenu.destroy();
		}

		this.actionMenu = Ext.create('Ext.menu.Menu', {
			items: items,
			border: true
		});

		if (xy) {
			this.actionMenu.showAt(xy);
		} else {
			this.actionMenu.showBy(item);
		}
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	onItemDblClick: function(grid, record) {
		this.editUser(record);
	},

	onCreateButtonClick: function() {
		this.editUser();
	},

	editUser: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create('E4ds.view.user.Edit', {
			rolesStore: this.rolesStore
		});

		var form = editWindow.down('form');
		if (record) {
			form.loadRecord(record);
		} else {
			form.loadRecord(Ext.create('E4ds.model.User'));
		}

		form.isValid();

		editWindow.down('#userNameTextField').focus();
		editWindow.down('#editFormSaveButton').addListener('click', this.onEditFormSaveButtonClick, this);
	},

	destroyUser: function(record) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm(i18n.attention, i18n.user_deleteconfirm, function(buttonId, text, opt) {
			if (buttonId === 'yes') {
				store.remove(record);
				store.sync({
					success: function() {
						E4ds.ux.window.Notification.info(i18n.successful, i18n.user_deleted);
					},
					failure: function(records, operation) {
						store.rejectChanges();
						E4ds.ux.window.Notification.error(i18n.error, i18n.user_lastAdminUserError);
					}
				});
			}
		});
	},

	onFilterField: function(field, newValue) {
		var store = this.getView().getStore();
		if (newValue) {
			store.clearFilter(true);
			store.filter('filter', newValue);
			this.getExportButton().setParams({
				filter: newValue
			});
		} else {
			store.clearFilter();
			this.getExportButton().setParams();
		}
	},

	onEditFormSaveButtonClick: function(button) {
		var win = button.up('window');
		var form = win.down('form');
		var store = this.getView().getStore();

		form.updateRecord();
		var record = form.getRecord();

		if (!record.dirty) {
			win.close();
			return;
		}

		if (record.phantom) {
			store.rejectChanges();
			store.add(record);
		}

		store.sync({
			success: function(records, operation) {
				E4ds.ux.window.Notification.info(i18n.successful, i18n.user_saved);
				win.close();
			},
			failure: function(records, operation) {
				store.rejectChanges();
			}
		});

	},

	switchTo: function(record) {
		if (record) {
			securityService.switchUser(record.data.id, function(ok) {
				if (ok) {
					History.pushState({}, i18n.app_title, "?");
					window.location.reload();
				}
			}, this);
		}
	}

});