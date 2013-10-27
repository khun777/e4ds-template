Ext.define('E4ds.store.LogEvents', {
	extend: 'Ext.data.Store',
	model: 'E4ds.model.LogEvent',
	autoLoad: false,
	remoteSort: true,
	remoteFilter: true,
	pageSize: 30,
	sorters: [ {
		property: 'eventDate',
		direction: 'DESC'
	} ]
});