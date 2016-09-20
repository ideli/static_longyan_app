/**
 *
 * 首页->通讯录
 **/
define('js/longyan/view/contact_detail', [
        'text!js/longyan/template/contact_detail.tpl',
        'js/api/contactBook',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header'
],
    function (contactDetailTpl,contactBookApi ,Cache, AlertUI, HeaderView) {

        var tipsAlert = tipsAlert || new AlertUI();
        var LayoutView = Backbone.View.extend({
            events: {

            },
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据
                t.initEvents();
                t.config = config || {};
                t.loadData(t.config.id);

            },
            render: function() {
                $('body').css('background-color', '#ffffff');
                var t = this;
                t.$el.off('click');
                t.initEvents();
                //t.$el.html(tpl(contactDetailTpl, {})); //模板渲染

            },
            loadData:function(employeeId){
                var t = this;
                tipsAlert.openLoading({
                    content: '加载中...'
                });
                contactBookApi.getContactEmployeeInfo(employeeId,
                    function (data) {
                        if(data.redstarEmployee){
                            tipsAlert.close();
                            var $contactShowbox = t.$el;
                            var tplHtml = tpl(contactDetailTpl, {data:data.redstarEmployee});
                            t.$el.html(tplHtml);
                            var $detailGoBack = $("#detailGoBack");
                            $detailGoBack.on("click",function(){

                                router.navigate('contact_book', {
                                    trigger: true
                                });
                            })
                        }else{
                            tipsAlert.openAlert({
                                content: "无该用户信息"
                            });
                            setTimeout(function(){
                                router.navigate('contact_book', {
                                    trigger: true
                                });
                            }, 2000)
                        }

                    },
                    function (code, msg) {
                        tipsAlert.close();
                        if (code && code == 408) {
                            //请求超时
                            $('#scroller').hide();
                            var tmp = $('.error-view');
                            if (tmp && tmp.length > 0) {
                            } else {
                                $('#scroller').after(tpl(NoNetworkTpl, {}));
                                t.$el.find('.error-no-network').off('click');
                                t.$el.find('.error-no-network').on('click', function () {
                                    //重新刷新 reload
                                    t.list_box.loadData();
                                });
                            }
                            return;
                        }
                        tipsAlert.openAlert({
                            content: msg
                        });
                    });


            },
            initEvents: function() {
                var t = this;
                t.$el.find('#fix-button').on('click', function(e) {
                    e.preventDefault();
                    router.navigate('community_create', {
                        trigger: true
                    });
                });
                t.$el.on('click', '.community-item-box', function(e) {
                    e.preventDefault();
                });
            },
            test: function() {

            }
        })
        return LayoutView;

    }
)