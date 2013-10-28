Ext.define('E4ds.store.LogLevels', {
	extend: 'Ext.data.ArrayStore',
	fields: [ 'level' ],
	data: [ [ 'OFF' ], [ 'FATAL' ], [ 'ERROR' ], [ 'WARN' ], [ 'INFO' ], [ 'DEBUG' ], [ 'TRACE' ], [ 'ALL' ] ]
});