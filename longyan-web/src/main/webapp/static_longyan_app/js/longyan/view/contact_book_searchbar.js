/**
 *
 * 首页->通讯录
 **/
define('js/longyan/view/contact_book_searchbar', [
        'text!js/longyan/template/contact_book_searchbar.tpl',
        'text!js/longyan/template/contact_book_employee_list.tpl',
        'js/api/contactBook',
        'js/components/alert_ui',
        'js/element/view/list-box'
],
    function (contactBookSearchbarTpl, contactBookEmployeeTpl, contactBookApi, AlertUI, ListBox) {
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
            },
            render: function() {
                var t = this;
                t.$el.html(tpl(contactBookSearchbarTpl, {})); //模板渲染
                /*t.contact_book_employee_list = new contactBookEmployeeListView({
                    el: $('#employeeList')

                })*/

                var $searchInput = t.$el.find(".search_input"),
                    $searchbox = $searchInput.closest(".searchinput-box"),
                    $contactBox = $("#contactBook"),
                    $contactShowbox = $("#contactShowbox"),
                    $employeeList = $("#employeeList"),
                    $searchClose = $searchInput.siblings(".close"),
                    $channel = $searchInput.siblings(".channel");

                $searchInput.on("focus",function(){
                   var $this = $(this);
                    $searchbox.addClass("choose");
                    //console.log($searchbox)
                    $contactBox.addClass("toTop");
                    $contactShowbox.hide();
                    $employeeList.show();
                }).on("keydown",function(){
                    var $this = $(this);
                    searchInputEmpty();

                })
               /* $searchInput.change(function(){
                    var $this = $(this);
                    t.getKeyWord($this.val());
                })*/
                $searchInput.bind('input propertychange', function() {
                    var $this = $(this);
                    t.getKeyWord($this.val());
                });
                $channel.on("click",function(){
                    $searchbox.removeClass("choose");
                    $contactBox.removeClass("toTop");
                    $searchbox.addClass("isEmpty");
                    $searchInput.val("");
                    $searchClose.hide();
                    $contactShowbox.show();
                    $employeeList.hide();
                });

                $searchClose.on("click",function(){
                    $searchInput.val("");
                    $searchInput.focus();
                    $searchClose.hide();
                });

                var searchInputEmpty = function(){
                    var $searchIco = $searchInput.siblings("i");
                    if($searchInput.val()){
                        $searchbox.removeClass("isEmpty");
                        $searchClose.show();
                    }else{
                        $searchbox.addClass("isEmpty");
                        $searchClose.hide();
                    }
                };


            },

            getKeyWord:function(keyword){
                //var keyword = "王";
                contactBookApi.getContacttEmployeeList(keyword,function (data){
                    var tplHtml = tpl(contactBookEmployeeTpl, {data: data.employees});
                    $("#employeeList").html(tplHtml);
                })
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
                console.log(123);
            }
        })
        return LayoutView;

    }
)