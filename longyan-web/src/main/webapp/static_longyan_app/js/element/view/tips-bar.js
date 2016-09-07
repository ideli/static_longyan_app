/**
 * 头
 **/
define('js/element/view/tips-bar', [
        'text!js/element/template/tips-bar.tpl'
    ],
    function(TipsTpl, Cache, AlertUI) {
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
                t.$el.append(tpl(TipsTpl, {
                    data: t.config
                }));
            }
        });
        return LayoutView;
    });