Ext.onReady(function() {
	Ext.QuickTips.init();

	var header = Ext.create('Ext.container.Container', {
		region: 'north',
		height: 35,
		layout: {
			type: 'hbox',
			align: 'stretch'
		},

		items: [ {
			html: 'e4ds-template',
			cls: 'appHeader'
		} ]
	});

	var login = Ext.create('Ext.form.Panel', {
		frame: true,
		title: i18n.login_title,
		url: 'j_spring_security_check',
		width: 400,
		padding: 5,
		icon: app_context_path + '/resources/images/key.png',

		standardSubmit: true,

		defaults: {
			anchor: '100%'
		},

		defaultType: 'textfield',

		fieldDefaults: {
			msgTarget: 'side'
		},

		items: [ {
			fieldLabel: i18n.user_username,
			name: 'j_username',
			allowBlank: false,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: i18n.user_password,
			name: 'j_password',
			inputType: 'password',
			allowBlank: false,
			listeners: {
				specialkey: function(field, e) {
					if (e.getKey() === e.ENTER) {
						submitForm();
					}
				}
			}
		}, {
			fieldLabel: i18n.login_rememberme,
			name: '_spring_security_remember_me',
			xtype: 'checkbox'
		} ],

		buttons: [ /* <_debug> */{
			text: i18n.login_withuser,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					j_username: 'user',
					j_password: 'user'
				});
				form.submit();
			}
		}, {
			text: i18n.login_withadmin,
			handler: function() {
				var form = this.up('form').getForm();
				form.setValues({
					j_username: 'admin',
					j_password: 'admin'
				});
				form.submit();
			}
		},/* </debug> */{
			text: i18n.login,
			handler: function() {
				submitForm();
			}
		} ]
	});

	Ext.create('Ext.container.Viewport', {
		renderTo: Ext.getBody(),

		layout: {
			type: 'border',
			padding: 5
		},

		items: [ header, Ext.create('Ext.Container', {
			layout: {
				type: 'vbox',
				align: 'center',
				pack: 'center'
			},
			region: 'center',
			items: login
		}) ]
	});

	function submitForm() {
		var form = login.getForm();
		if (form.isValid()) {
			form.submit();
		}
	}

	login.getForm().findField('j_username').focus();

});