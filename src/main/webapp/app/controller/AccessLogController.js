Ext.define('E4ds.controller.AccessLogController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved'
		},
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

	init: function() {
		this.doGridRefresh();
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},

	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
		} else {
			myStore.clearFilter();
		}
	},

	deleteAll: function() {
		accessLogService.deleteAll(function() {
			E4ds.ux.window.Notification.info(i18n.successful, i18n.accesslog_deleted);
			this.doGridRefresh();
		}, this);
	},

	addTestData: function() {
		accessLogService.addTestData(function() {
			E4ds.ux.window.Notification.info(i18n.successful, i18n.accesslog_testinserted);
			this.doGridRefresh();
		}, this);
	},

	doGridRefresh: function() {
		this.getView().getStore().load();
	}

});