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
			html: 'e4ds-template',
			cls: 'appHeader'
		}, {
			xtype: 'tbspacer',
			flex: 1
		}, {
			xtype: 'label',
			itemId: 'loggedOnLabel',
			text: '',
			cls: 'userName',
			width: 200,
			margins: {
				top: 6,
				right: 0,
				bottom: 0,
				left: 0
			}
		}, {
			xtype: 'tbspacer',
			width: 20
		}, {
			xtype: 'button',
			text: i18n.options,
			icon: app_context_path + '/resources/images/gear.png',
			itemId: 'optionButton',
			margins: {
				top: 2,
				right: 0,
				bottom: 10,
				left: 0
			},
			ui: 'default-toolbar'
		}, {
			xtype: 'tbspacer',
			width: 20
		}, {
			xtype: 'button',
			text: i18n.logout,
			icon: app_context_path + '/resources/images/logout.png',
			href: 'j_spring_security_logout',
			hrefTarget: '_self',
			margins: {
				top: 2,
				right: 0,
				bottom: 10,
				left: 0
			},
			ui: 'default-toolbar'
		} ];

		me.callParent(arguments);

	}

});