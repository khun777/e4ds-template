Ext.define('E4ds.controller.AccessLogController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		pagingtoolbar: true,
		deleteAllButton: {
			click: 'deleteAll'
		},
		/* <debug> */
		testButton: {
			click: 'addTestData'
		},
		/* </debug> */
		filterField: {
			filter: 'handleFilter'
		}
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.remoteFilter = false;
			myStore.clearFilter(true);
			myStore.remoteFilter = true;
			myStore.filter('filter', newValue);
		} else {
			myStore.clearFilter();
		}
	},

	deleteAll: function() {
		accessLogService.deleteAll(function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.accesslog_deleted);
			this.doGridRefresh();
		}, this);
	},

	addTestData: function() {
		accessLogService.addTestData(function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.accesslog_testinserted);
			this.doGridRefresh();
		}, this);
	},

	onActivated: function() {
		this.doGridRefresh();
	},

	doGridRefresh: function() {
		this.getPagingtoolbar().doRefresh();
	}

});