Ext.define('E4ds.view.poll.PollChart', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.pollchart',
	stateId: 'pollChart',
	title: i18n.chart_title,

	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	closable: true,

	requires: [ 'Ext.chart.*', 
	            'E4ds.view.poll.CpuLoadChart',
	            'E4ds.view.poll.PhysicalMemoryChart'],

	initComponent: function() {

		var me = this;

		me.dockedItems = [ {
			xtype: 'toolbar',
			items: [ {
				text: i18n.chart_stop,
				iconCls: 'icon-stop',
				action: 'control'
			} ]
		} ];

		me.items = [ {
			xtype: 'panel',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},			
			items: [ {
				xtype: 'cpuloadchart',
				flex: 1
			}, {
				xtype: 'physicalmemorychart',
				flex: 1
			} ]
		}, {
			xtype: 'panel',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},			
			items: [ {
				xtype: 'cpuloadchart',
				flex: 1
			}, {
				xtype: 'cpuloadchart',
				flex: 1
			} ]
		} ];

		me.callParent(arguments);
	}
});
