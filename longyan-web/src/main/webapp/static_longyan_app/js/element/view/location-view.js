/**
 * 位置表示组件.
 **/
define('js/element/view/location-view', [
        'text!js/element/template/location-view.tpl',
        'js/api/common'
        // 'js/components/picker'
    ],
    function(LocationTpl, CommonApi) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = _.extend({}, config);
                t.roomMount = t.config.roomMount = t.config.roomMount || 0;
                t.alreadyInputAmount = t.config.alreadyInputAmount = t.config.alreadyInputAmount || 0;
                t.address = t.config.address;
                t.fieldName = t.config.fieldName;
                t.createAuth = t.config.createAuth;
                t.events = events;
                t.render();

                t.initEvents();
            },
            computeCompleteWidth: function() {
                var t = this;
                var baseWidth = t.$el.find('.' + this.fieldName + ' .base').width();
                _log(t.$el.find('.' + this.fieldName + ' .base').width())
                var completeWidth = t.alreadyInputAmount/t.roomMount * baseWidth;
                if(isNaN(completeWidth) || completeWidth < 170) {
                    completeWidth = 170;
                }
                return completeWidth;
            },
            render: function() {
                var t = this;
                t.$current = t.$el.append(tpl(LocationTpl, {
                    data: t.config
                })).find('.' + t.fieldName);
                if(t.config.createAuth) {
                    t.$el.find('.auth-info').show();
                }
                var completeWidth = this.computeCompleteWidth();
                t.$el.find('.' + this.fieldName + ' .complete').width(completeWidth);
            },
            initEvents: function() {
            },
            reRender: function() {
                var t = this;
                t.$current.replaceWith(tpl(LocationTpl, {
                    data: t.config
                }));
                if(t.config.createAuth) {
                    t.$el.find('.auth-info').show();
                }
                var completeWidth = this.computeCompleteWidth();
                t.$el.find('.' + this.fieldName + ' .complete').width(completeWidth);
            },
            setValue: function(data) {
                var t = this;
                if(data) {
                    _log(data);
                    t.config.roomMount = data.roomMount || 0;
                    t.config.alreadyInputAmount = data.alreadyInputAmount || 0;
                    t.config.address = data.address;
                    t.config.createAuth = data.createAuth;
                    t.config.admin = data.admin;
                    t.config.createDate = data.createDate;
                }
                this.reRender();
            }
        });
        return LayoutView;
    });