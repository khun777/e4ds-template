Ext.define('E4ds.view.navigation.UserSettings', {
	extend: 'Ext.window.Window',
	stateId: 'useroption',
	title: i18n.settings,
	width: 500,
	layout: 'fit',
	resizable: true,
        constrain: true,
	autoShow: true,
	modal: true,
	icon: app_context_path + '/resources/images/edit.png',

	initComponent: function() {
		var me = this;

		me.items = [ {
			xtype: 'form',
			padding: 5,
			bodyPadding: 10,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			fieldDefaults: {
				msgTarget: 'side'
			},

			items: [ {
				name: 'firstName',
				fieldLabel: i18n.user_firstname,
				allowBlank: false
			}, {
				name: 'name',
				fieldLabel: i18n.user_lastname,
				allowBlank: false
			}, {
				name: 'email',
				fieldLabel: i18n.user_email,
				vtype: 'email',
				allowBlank: false
			}, {
				xtype: 'combobox',
				fieldLabel: i18n.user_language,
				name: 'locale',
				store: Ext.create('Ext.data.ArrayStore', {
					fields: [ 'code', 'language' ],
					data: [ [ 'de', i18n.user_language_german ], [ 'en', i18n.user_language_english ] ]
				}),
				valueField: 'code',
				displayField: 'language',
				queryMode: 'local',
				emptyText: i18n.user_selectlanguage,
				allowBlank: false,
				forceSelection: true
			}, {
				xtype: 'label',
				html: '<hr />'
			}, {
				name: 'oldPassword',
				fieldLabel: i18n.user_oldpassword,
				inputType: 'password'
			}, {
				name: 'passwordNew',
				fieldLabel: i18n.user_newpassword,
				inputType: 'password'
			}, {
				name: 'passwordNewConfirm',
				fieldLabel: i18n.user_confirmpassword,
				inputType: 'password'
			} ],

			buttons: [ {
				xtype: 'button',
				itemId: 'editFormSaveButton',
				text: i18n.save,
				action: 'save',
				icon: app_context_path + '/resources/images/save.png',
				formBind: true
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				icon: app_context_path + '/resources/images/cancel.png'
			} ]
		} ];

		me.callParent(arguments);
	}
});
