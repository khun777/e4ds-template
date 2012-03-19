Ext.define('E4ds.view.poll.PollChart', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.pollchart',
	stateId: 'pollChart',
	title: i18n.chart_title,

	layout: 'fit',
	closable: true,

	requires: [ 'Ext.chart.*' ],

	initComponent: function() {
		var me = this;

		var store = Ext.StoreManager.get('PollChart');
		if (!store.getCount()) {
			for ( var i = 0; i < 20; i++) {
				store.add([ {
					'time': '00:00:00',
					'processCpuLoad': 0,
					'systemCpuLoad': 0
				} ]);
			}
		}

		me.dockedItems = [ {
			xtype: 'toolbar',
			items: [ {
				text: i18n.chart_stop,
				iconCls: 'icon-stop',
				action: 'control'
			} ]
		} ];

		me.items = [ {
			xtype: 'chart',
			animate: true,
			shadow: true,
			store: store,
			height: 600,
			axes: [ {
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
			} ],
			series: [ {
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
			} ]
		} ];

		me.callParent(arguments);
	}
});
