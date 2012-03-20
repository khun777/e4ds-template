Ext.define('E4ds.controller.PollChart', {
	extend: 'Ext.app.Controller',

	stores: [ 'PollChart' ],
	models: [ 'PollChart' ],
	views: [ 'poll.PollChart' ],

	polling: true,

	init: function() {
		this.control({
			'pollchart': {
				add: this.onAdd,
				destroy: this.stopPoll,
				beforeactivate: this.startPoll,
				beforedeactivate: this.stopPoll
			},
			'pollchart button[action=control]': {
				click: this.controlPolling
			}
		});
	},

	onData: function(provider, event) {
		var store = this.getPollChartStore(), model = this.getPollChartModel();

		if (store.getCount() > 10) {
			store.removeAt(0);
		}

		store.add(model.create({
			id: event.data.id,
			time: event.data.date,
			processCpuLoad: event.data.processCpuLoad,
			systemCpuLoad: event.data.systemCpuLoad
		}));
	},

	onAdd: function(cmp) {
		this.provider = Ext.direct.Manager.getProvider('chartdatapoller');
		this.provider.addListener('data', this.onData, this);
		this.polling = true;
		this.startPoll();
	},

	controlPolling: function(button, event) {
		if (!this.polling) {
			button.setText(i18n.chart_stop);
			button.setIconCls('icon-stop');
			this.polling = true;
			this.startPoll();
		} else {
			button.setText(i18n.chart_start);
			button.setIconCls('icon-start');
			this.polling = false;
			this.stopPoll();
		}
	},

	startPoll: function() {
		if (this.polling) {
			this.provider.connect();
		}
	},

	stopPoll: function() {
		this.provider.removeListener('data', this.onData);
		this.provider.disconnect();
	},

});
