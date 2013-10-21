Ext.define('E4ds.view.navigation.Header', {
	extend: 'Ext.container.Container',
	height: 35,
	layout: {
		type: 'hbox',
		align: 'stretch'
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			html: i18n.app_title,
			cls: 'appHeader'
		}, {
			xtype: 'tbspacer',
			flex: 1
		}, {
			xtype: 'button',
			itemId: 'loggedOnLabel',
			text: '',
			ui: 'default-toolbar',
			margins: {
				top: 2,
				right: 0,
				bottom: 10,
				left: 0
			},
			menu: {
				items: [ {
					text: i18n.settings,
					icon: app_context_path + '/resources/images/Customize-02-16.png',
					itemId: 'settingsButton'
				}, {
					text: i18n.logout,
					handler: function() {
						Ext.Ajax.request({
							url: 'logout',
							method: 'POST',
							success: function(response) {
								window.location = 'login.html?logout';
							}
						});
					}
				} ]
			}
		} ];

		me.callParent(arguments);

	}

});