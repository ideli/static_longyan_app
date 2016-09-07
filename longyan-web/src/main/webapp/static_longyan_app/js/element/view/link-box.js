/**
 * 头
 **/
define('js/element/view/link-box', [
        'text!js/element/template/link-box.tpl'
    ],
    function(LinkTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.render();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(LinkTpl, {
                    data: t.config
                }));
            }
        });
        return LayoutView;
    });