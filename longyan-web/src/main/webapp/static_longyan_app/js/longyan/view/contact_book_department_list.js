/**
 *
 * 首页->通讯录
 **/
define('js/longyan/view/contact_book_department_list', [
        'text!js/longyan/template/contact_book_department_list.tpl',
        'js/api/contactBook',
        'js/components/alert_ui'
    ],
    function (contactBookDepartmentListTpl,contactBookApi,AlertUI) {
        var tipsAlert = tipsAlert || new AlertUI();
        var LayoutView = Backbone.View.extend({
            events: {},
            initialize: function (options, config) {
                var t = this;
                t.$el.off('click');
                t.render();

                t.loadData('', "#contactShowbox");
                //加载数据
                t.initEvents();
            },
            render: function () {
                var t = this;
                //t.$el.html(tpl(contactBookDepartmentListTpl, {})); //模板渲染
            },
            itemClick: function (listShowBox) {

                var fun = this;
                var $item = $(listShowBox).find(".item");
                $item.off("click");
                $item.on("click", function () {
                    var $this = $(this),
                        thisDepartmentId = $this.data("departmentid"),
                        $parent = $this.parent(),
                        $siblingsUl = $this.siblings("ul");

                    if ($siblingsUl.length) {
                        if ($this.hasClass("item-open")) {
                            $this.removeClass("item-open");
                            $siblingsUl.slideUp(500);
                        } else {
                            $this.addClass("item-open");
                            $siblingsUl.slideDown(500);
                        }
                    } else {
                        $this.addClass("item-open");
                        //$(".u-full").append(thisDepartmentId+"_")
                        fun.loadData(thisDepartmentId, $parent);

                        $siblingsUl.hide();
                        $siblingsUl.slideUp(500);
                    }


                })
            },
            loadData: function (departmentId, departmentListBox) {
                var fun = this;
                tipsAlert.openLoading({
                    content: '加载中...'
                });


                contactBookApi.getContactDeptList(departmentId,
                    function (data) {
                       // $(".u-full").append(departmentId+"_")
                        tipsAlert.close();
                        var t = this,
                            $contactShowbox = t.$el;
                        var tplHtml = tpl(contactBookDepartmentListTpl, {data: data});
                        $(tplHtml).appendTo($(departmentListBox));

                        fun.itemClick(departmentListBox);
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
                        }else if(code && code == -401){
                            router.navigate('login', {
                                trigger: true
                            });
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
            }
        })
        return LayoutView;

    }
)