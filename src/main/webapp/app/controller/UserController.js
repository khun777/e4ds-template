Ext.define('E4ds.controller.UserController', {
	extend: 'Deft.mvc.ViewController',
	requires: [ 'E4ds.view.user.Edit' ],
	control: {
		view: {
			removed: 'onRemoved',
			itemdblclick: 'onItemDblClick'
		},
		createButton: {
			click: 'onCreateButtonClick'
		},
		editButton: {
			click: 'onEditButtonClick'
		},
		destroyButton: {
			click: 'onDestroyButtonClick'
		},
		filterField: {
			filter: 'onFilterField'
		},
		switchButton: {
			click: 'onSwitchButtonClick'
		},
		exportButton: true
	},

	init: function() {
		var store = this.getView().getStore();
		store.clearFilter(true);
		store.load();
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

	onEditButtonClick: function() {
		this.editUser(this.getView().getSelectionModel().getSelection()[0]);
	},

	editUser: function(record) {
		this.getView().getStore().rejectChanges();

		var editWindow = Ext.create('E4ds.view.user.Edit', {
			rolesStore: Ext.create('E4ds.store.Roles')
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

	onDestroyButtonClick: function(button) {
		var me = this;
		var store = me.getView().getStore();

		Ext.Msg.confirm(i18n.attention, i18n.user_deleteconfirm, function(buttonId, text, opt) {
			if (buttonId === 'yes') {
				var record = me.getView().getSelectionModel().getSelection()[0];
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

	onSwitchButtonClick: function() {
		var record = this.getView().getSelectionModel().getSelection()[0];
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