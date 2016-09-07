/**
 * 列表项目
 **/
define('js/element/view/linked-list-item', [
        'text!js/element/template/linked-list-item.tpl'
    ],
    function(LinkedListItemTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.events = events;
                t.render();
                t.initEvents();
            },
            initEvents: function() {
                var t =  this;
                if(t.events) {
                }
            },
            clearEvents: function() {
            },
            render: function() {
                var t = this;
                this.$current = t.$el.append(tpl(LinkedListItemTpl, {
                    data: t.config
                })).find('.' + this.config.fieldName);
            },
            setValue: function(value, link) {
                this.config.text = value;
                this.config.link = '#' + link;
            },
            reRender: function() {
                this.clearEvents();
            	this.$current = this.$current.replaceWith(tpl(LinkedListItemTpl, {
                    data: this.config
                }));
                this.initEvents();
            }
        });
        return LayoutView;
    });