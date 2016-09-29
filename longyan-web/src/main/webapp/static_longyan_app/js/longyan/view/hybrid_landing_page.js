/**
 * hybrid跳转中转页
 **/
define('js/longyan/view/hybrid_landing_page', [
        'js/util/memory_cache',
        'js/components/alert_ui'
    ],
    function(Cache, AlertUI) {
        var tipsAlert = tipsAlert || new AlertUI();
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                //加载数据  
                t.loadData();
            },
            loadData: function() {
                var t = this;
                if (t.config && t.config.data) {
                    //处理数据
                    // var load_object={
                    //     gotoPage:'community/1',
                    //     source:'hybrid',
                    //     ......
                    // }
                    //解码
                    var decode = decodeURIComponent(t.config.data);
                    //编码
                    var load_object = $nvwa.string.jsonStringToObject(decode);
                    if (load_object && load_object.gotoPage) {
                        window.router.changePage(load_object.gotoPage, load_object);
                    }
                }
            }
        });
        return LayoutView;
    });