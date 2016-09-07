/**
 *
 * 基础容器
 **/
define('js/longyan/view/container', [
        'text!js/longyan/template/container.tpl'
    ],
    function(ContainerTpl) {
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
                $('body').css('background-color', t.config.backgroundColor || '#F5F5F5');
                t.$el.html(tpl(ContainerTpl, {
                	data: t.config
                }));
            }
        });
        return LayoutView;
    });