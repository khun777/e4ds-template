Ext.define('E4ds.view.poll.CpuLoadChart', {
	extend: 'Ext.chart.Chart',
	alias: 'widget.cpuloadchart',

	animate: true,
	shadow: true,
	store: 'PollChart',
	
	initComponent: function() {
		
		var me = this;
		
		me.axes = [ {
			type: 'Numeric',
			position: 'left',
			fields: [ 'processCpuLoad', 'systemCpuLoad' ],
			maximum: 1,
			minimum: 0,
			majorTickSteps: 9,
			minorTickSteps: 4,
			title: i18n.chart_cpuload,
			grid: true
		}, {
			type: 'Category',
			position: 'bottom',
			fields: [ 'time' ],
			title: i18n.chart_pollingtime
		} ];

		me.series = [ {
			type: 'line',
			tips: {
				width: 130,
				renderer: function(storeItem, item) {
					this.setTitle(i18n.chart_processcpuload + ': ' + storeItem.get('processCpuLoad'));
				}
			},
			axis: 'left',
			xField: 'time',
			yField: 'processCpuLoad'
		}, {
			type: 'line',
			tips: {
				width: 130,
				renderer: function(storeItem, item) {
					this.setTitle(i18n.chart_systemcpuload + ': ' + storeItem.get('systemCpuLoad'));
				}
			},
			axis: 'left',
			xField: 'time',
			yField: 'systemCpuLoad'
		} ];
		
		me.callParent(arguments);
	}

});