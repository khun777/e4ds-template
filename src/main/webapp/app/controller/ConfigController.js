Ext.define('E4ds.controller.ConfigController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		logLevelCombobox: {
			change: 'onChange'
		}
	},

	onActivated: function() {
		loggingEventService.getCurrentLevel(this.showCurrentLevel, this);
	},

	showCurrentLevel: function(logLevel) {
		this.getLogLevelCombobox().suspendEvents(false);
		this.getLogLevelCombobox().setValue(logLevel);
		this.getLogLevelCombobox().resumeEvents();
	},

	onChange: function(field, newValue, oldValue) {
		loggingEventService.changeLogLevel(newValue, function() {
			Ext.ux.window.Notification.info(i18n.successful, i18n.config_loglevelchanged);
		});
	}

});
