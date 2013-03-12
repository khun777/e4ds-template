Ext.define('E4ds.controller.PollChartController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated',
			close: 'onClose'
		},
		startStopButton: {
			click: 'onStartStopClick'
		}
	},

	onData: function(provider, event) {
		var store = this.getView().store;

		if (store.getCount() > 10) {
			store.removeAt(0);
		}

		store.add(Ext.create('E4ds.model.PollChart', event.data));
	},

	onActivated: function(cmp) {
		this.provider = Ext.direct.Manager.getProvider('chartdatapoller');
		this.startPolling();
	},

	onClose: function() {
		this.stopPolling();
	},

	onStartStopClick: function() {
		if (!this.provider.isConnected()) {
			this.startPolling();
		} else {
			this.stopPolling();
		}
	},

	startPolling: function() {
		var button = this.getStartStopButton();
		if (button) {
			button.setText(i18n.chart_stop);
			button.setIcon(app_context_path + '/resources/images/stop.png');
		}

		if (!this.provider.isConnected()) {
			this.provider.addListener('data', this.onData, this);
			this.provider.connect();
		}
	},

	stopPolling: function() {
		var button = this.getStartStopButton();
		if (button) {
			button.setText(i18n.chart_start);
			button.setIcon(app_context_path + '/resources/images/start.png');
		}

		if (this.provider.isConnected()) {
			this.provider.removeListener('data', this.onData);
			this.provider.disconnect();
		}
	}

});
