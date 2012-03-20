Ext.define('E4ds.model.PollChart', {
	extend: 'Ext.data.Model',
	fields: [ 'id', {
		name: 'time',
		type: 'string'
	}, {
		name: 'processCpuLoad',
		type: 'float'
	}, {
		name: 'systemCpuLoad',
		type: 'float'
	} ]
});