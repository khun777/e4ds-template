Ext.define('E4ds.controller.ConfigController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			removed: 'onRemoved'
		},		
		logLevelCombobox: {
			change: 'onChange'
		}
	},

	init: function() {
		loggingEventService.getCurrentLevel(this.showCurrentLevel, this);
	},

	onRemoved: function() {
		History.pushState({}, i18n.app_title, "?");
	},	
	
	showCurrentLevel: function(logLevel) {
		this.getLogLevelCombobox().suspendEvents(false);
		this.getLogLevelCombobox().setValue(logLevel);
		this.getLogLevelCombobox().resumeEvents();
	},

	onChange: function(field, newValue, oldValue) {
		loggingEventService.changeLogLevel(newValue, function() {
			E4ds.ux.window.Notification.info(i18n.successful, i18n.config_loglevelchanged);
		});
	}

});
