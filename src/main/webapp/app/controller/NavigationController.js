Ext.define('E4ds.controller.NavigationController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		menuTree: {
			itemclick: 'onTreeItemClick'
		},
		tabPanel: {
			tabchange: 'onTabChange'
		},
		loggedOnLabel: true,
		settingsButton: {
			click: 'getUser'
		}
	},

	init: function() {
		var me = this;
		securityService.getLoggedOnUsername(this.showLoggedOnUser, this);

		History.Adapter.bind(window, 'statechange', function() {
			var state = History.getState();
			me.showTab(state.data);
		});
		
		Ext.direct.Manager.on('event', function(e) {
			me.handleDirectResponse(e);
		});
		
		var state = History.getState();
		if (state && state.data) {
			this.showTab(state.data);
		}
	},

	showLoggedOnUser: function(fullname) {
		this.getLoggedOnLabel().setText(fullname);
	},

	getPath: function(node) {
		return node.parentNode ? this.getPath(node.parentNode) + "/" + node.getId() : "/" + node.getId();
	},

	getUser: function() {
		userService.getLoggedOnUser(this.openSettingsWindow, this);
	},

	openSettingsWindow: function(result) {
		if (result) {
			var userSettingsWindow = Ext.create('E4ds.view.navigation.UserSettings');
			userSettingsWindow.down('form').loadRecord(Ext.create('E4ds.model.User', result));
			userSettingsWindow.down('#editFormSaveButton').addListener('click', this.onUserSettingsSaveButtonClick, this);
		}
	},

	onUserSettingsSaveButtonClick: function(button) {
		var win = button.up('window');
		var form = win.down('form');
	
		userService.updateSettings(form.getForm().getFieldValues(), function(result) {
			if (result.success) {
				win.close();
				E4ds.ux.window.Notification.info(i18n.successful, i18n.settings_saved);
			}
		});

	},
	
//	updateUser: function(editWindow) {
//		var form = editWindow.getForm(), record = form.getRecord();
//
//		form.submit({
//			params: {
//				id: record ? record.data.id : ''
//			},
//			scope: this,
//			success: function() {
//				editWindow.close();
//				E4ds.ux.window.Notification.info(i18n.successful, i18n.settings_saved);
//			}
//		});
//	},

	onTreeItemClick: function(treeview, record, item, index, event, options) {
		this.pushHistoryState(record);
	},

	onTabChange: function(tabPanel, newCard) {
		var record = this.syncNavigation();
		if (record) {
			this.pushHistoryState(record);
		}
	},

	pushHistoryState: function(record) {
		var state = {
			view: record.raw.view,
			viewConfig: {
				icon: record.get('icon'),
				treePath: this.getPath(record),
				navigationId: record.getId()
			}
		};

		History.pushState(state, i18n.app_title + ': ' + record.get('text'), "?vid=" + record.getId());
	},

	showTab: function(state) {
		var view = state.view;
		if (view) {
			var tab = this.getTabPanel().child('panel[navigationId=' + state.viewConfig.navigationId + ']');
			if (!tab) {
				var viewObject = Ext.create(view, state.viewConfig);
				tab = this.getTabPanel().add(viewObject);
			}
			this.getTabPanel().setActiveTab(tab);
		}
	},

	syncNavigation: function(e) {
		var record = null;
		var activeTab = this.getTabPanel().getActiveTab();
		var selectionModel = this.getMenuTree().getSelectionModel();
		this.getMenuTree().expandPath(activeTab.treePath);

		var activeTabId = activeTab.navigationId;
		var selection = selectionModel.getLastSelected();
		var currentId = selection && selection.raw.id;

		if (activeTabId !== currentId) {
			record = this.getMenuTree().getStore().getNodeById(activeTabId);
			selectionModel.select(record);
		}

		return record;
	},
	
	handleDirectResponse: function(event) {
		var me = this;
		Ext.getBody().unmask();
		if (event.type === 'rpc') {
			if (event.result && !event.result.success) {
				if (event.result.validations) {
					me.showValidationMessage(event.result.validations, 'Please correct the following errors');
				}
			}
		}
	},

	showValidationMessage: function(data, message) {
		var errorString = '<ul>';

		for (var i in data) {
			var error = data[i];
			errorString += '<li>' + error.message + '</li>';
			var fieldMatch = Ext.ComponentQuery.query('field[name=' + error.field + ']');
			if (fieldMatch.length) {
				fieldMatch[0].markInvalid(error.message);
			}
		}
		errorString += '</ul>';
		Ext.Msg.alert(message, errorString);
	}	

});
