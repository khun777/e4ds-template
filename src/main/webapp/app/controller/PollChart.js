Ext.define('E4ds.controller.PollChart', {
	extend: 'Ext.app.Controller',

	stores: [ 'PollChart' ],
	models: [ 'PollChart' ],
	views: [ 'poll.PollChart' ],

	polling: true,
	
	refs: [ {
		ref: 'pollchart',
		selector: 'pollchart'
	}, {
		ref: 'pollchartCmp',
		selector: 'pollchart chart'
	}, {
		ref: 'controlButton',
		selector: 'pollchart button[action=control]'
	} ],

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

	onAdd: function(cmp) {
		console.log('onAdd');
		var store = this.getPollChartStore(), model = this.getPollChartModel();

		this.provider = Ext.direct.Manager.getProvider('chartdatapoller');
		this.provider.addListener('data', function(provider, event) {
			if (store.getCount() > 20) {
				store.removeAt(0);
			}

			var record = model.create({
				time: event.data.date,
				processCpuLoad: event.data.processCpuLoad,
				systemCpuLoad: event.data.systemCpuLoad
			});

			store.add(record);
		});
		this.startPoll();
	},

	controlPolling: function(button, event) {
		if (button.getText() == 'Start') {
			button.setText(i18n.chart_stop);
			button.setIconCls('icon-stop');
			this.polling = true;
			this.startPoll();
		} else {
			button.setText(i18n.chart_start);
			button.setIconCls('icon-start');
			this.stopPoll();
		}
	},

	startPoll: function() {
		console.log('startPoll');
		if (this.polling) {
			this.provider.connect();
		}
	},

	stopPoll: function() {
		console.log('stopPoll');
		this.polling = false;
		this.provider.disconnect();
	},


});
