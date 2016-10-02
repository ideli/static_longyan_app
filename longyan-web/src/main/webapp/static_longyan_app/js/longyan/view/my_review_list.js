/**
 *my review list view
 *我的审核列表
 **/
define('js/longyan/view/my_review_list', [
        'text!js/longyan/template/my_review_list.tpl',
        'text!js/longyan/template/my_review_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/audit'
    ],
    function(ListContailerTpl, ReviewListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, AuditApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-review-list-view';
        var form_id = '#my-review-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                'click .item-box': '_clickItem',
                'click .my-owner-community-list-item': '_clickToAnother'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
                //加载数据       
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(ListContailerTpl, {config: t.config}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '我的审核'
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#my-review-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });

                        var _request_type=['NEEDACTION','OK','NG'];

                        AuditApi.myReviewList({
                            type:_request_type[t.config.status]
                        },function(data){
                            if(data&&data.result){
                                tipsAlert.close();
                                var result=data.result;
                                var currentPage = result.currentPage;
                                var totalPages = result.totalPages;
                                var currentRecords = result.currentRecords;
                                if(handler){
                                    handler(currentRecords, currentPage, totalPages);
                                }
                            }
                        },function(code, msg) {
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                    },

                    appendItem: function(data) {
                        console.log(data);
                        //住宅录入率
                        // var item = {
                        //     index: i,
                        //     name: data['xingMing'],
                        //     inputMemberAmount: data['inputMemberAmount'],
                        //     inputCommunityAmount: data['inputCommunityAmount'],
                        //     employeeCount: data['employeeCount'],
                        //     inputMemberRate: inputMemberRate,
                        //     url: '#report_employee_by_id/' + data['id']
                        // };
                        // i++;
                        //'李晓明提交了<span>万科十一区</span>的小区变更申请',
                        var arrDay = data.auditShowDate.substring(0,10);
                        var arrSec = data.auditShowDate.substring(11);
                        var name = data.updateEmployeeXingMing||"";
                        var str = name+'提交了<span>'+data.name+'</span>的小区变更申请';
                        if(t.config.status==0){
                            var showName = str;
                        }else if(t.config.status==1){
                            var showName = str+"通过";
                        }else if(t.config.status==2){
                            var showName = str+"未通过";
                        }
                        var item = {
                            id: data.id,
                            name: showName,
                            dateStr: arrDay,
                            timeStr: arrSec,
                            status: 0
                        };

                        return tpl(ReviewListItemTpl, {
                            data: item
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            },

            //点击跳转至相关页面
            _clickToAnother: function(e) {
                var t = this;
                var id = $(e.currentTarget).attr('data-id') || 0;
                AuditApi.auditDetails(id,function(){
                    tipsAlert.close();
                    $(".button-box.pass-button").attr('data-id', id);
                    if(t.config.status != 0){
                        $('.basic-gap.owner-gap').hide();
                    }
                },function(code, msg) {
                    tipsAlert.close();
                    tipsAlert.openAlert({
                        content: msg
                    });
                });
                window.location.href = "#my_review_detail/"+id;
            },

            //点击跳转小区详情页面
            _clickItem: function(e) {
                var t = this;
                var index = $(e.currentTarget).attr('index');
                if (index != t.config.status) {
                    window.location.href = '#my_review_list/' + index;
                } else {
                    console.log('no action');
                }
            },

            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });