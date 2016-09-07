/**
 * 关于 -> 帮助
 **/
define('js/longyan/view/help_list', [
        'text!js/longyan/template/help_list.tpl',
        'text!js/longyan/template/help_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/list-box',
        'js/api/community',
        'js/util/hybrid'
    ],
    function(HelpListTpl, HelpListItemTpl, Cache, AlertUI, HeaderView, ListBox, CommunityApi, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#help-list-view';
        var form_id = '#help-list-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据  
                t.initEvents();
                t.isIos()   //判断手机终端信息

            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(HelpListTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '帮助中心',
                    goBackUrl: function() {
                        hybrid.backToHybrid("Mine","direct");
                    }
                });
                // $('<div class="gap basic-gap"></div>').appendTo($('#help-list-box'));

                t.list_box = new ListBox({
                    el: $('#help-list-box')
                }, {
                    scroll: false, //bu支持下拉刷新
                   // marginTop: '11.4rem'
                }, {
                    loadData: function(page, handler) {
                        var page = 1;
                        var pageSize = 20;
                        // tipsAlert.openLoading({
                        //     content: '加载中...'
                        // });
                        // ReportApi.getMallList({
                        //         organizationId: organizationId
                        //     },
                        //     function(data) {
                        //         tipsAlert.close();
                        //         console.log(data);
                        //         if (data && data.result) {

                        var title = new Array(10);
                        title[0] = 'iPhone安装程序如何设置应用访问？';
                        title[1] = '录入小区和住宅的先后顺序是什么？';
                        title[2] = '哪些人可以录入小区？';
                        title[3] = '哪些人可以录入住宅？';
                        title[4] = '小区数据由谁进行管理？';
                        title[5] = '入住率怎么计算？';
                        title[6] = '装修状态怎么判断？';
                        title[7] = '同城多店小区怎么分配归属？';
                        title[8] = '小区的详细地址需要如何填写？';
                        title[9] = '员工变动后所属信息如何更改？';

                        var str = new Array(10);
                        str[0] = '<div class="help_detail_box"><div class="detail_title">iPhone安装程序如何设置应用访问？</div>' +
                            '<div class="lh40 pb10">在iPhone设置——通用——设备管理——选择信任“shanghaichinaredstar”</div>' +
                            '<img width="100%" src="' + window.resource.image + '/help/help_01.png"/>' +
                            '</div>';
                        str[1] = '<div class="help_detail_box"><div class="detail_title">录入小区和住宅的先后顺序是什么？</div>' +
                            '<div class="lh40 pb10">录入住宅之前，该住宅所属小区需要先录入系统。</div>' +
                            '<img width="100%" src="' + window.resource.image + '/help/help_02.png"/>' +
                            '</div>';
                        str[2] = '<div class="help_detail_box"><div class="detail_title">哪些人可以录入小区？</div>' +
                            '<div class="lh40 pb10">商场员工可以录入小区信息。</div>' +
                            '</div>';
                        str[3] = '<div class="help_detail_box"><div class="detail_title">哪些人可以录入住宅？</div>' +
                            '<div class="lh40 pb10">注册成功的用户都可以录入住宅信息。</div>' +
                            '</div>';
                        str[4] = '<div class="help_detail_box"><div class="detail_title">小区数据由谁进行管理？</div>' +
                            '<div class="lh40 pb10">小区录入人员负责小区信息的管理和维护，包含小区内住宅信息的维护。</div>' +
                            '</div>';
                        str[5] = '<div class="help_detail_box"><div class="detail_title">入住率怎么计算？</div>' +
                            '<div class="lh40 pb10">入住率=已装修住户/总住户数</div>' +
                            '</div>';
                        str[6] = '<div class="help_detail_box"><div class="detail_title">装修状态怎么判断？</div>' +
                            '<div class="lh40 pb10">装修状态指房屋装修的现状。</div>' +
                            '<img width="100%" src="' + window.resource.image + '/help/help_07.png"/>' +
                            '</div>';
                        str[7] = '<div class="help_detail_box"><div class="detail_title">同城多店小区怎么分配归属？</div>' +
                            '<div class="lh40 pb10">各商场由近及远进行录入，同城多店采取先入先得原则。</div>' +
                            '</div>';
                        str[8] = '<div class="help_detail_box"><div class="detail_title">小区的详细地址需要如何填写？</div>' +
                            '<div class="lh40 pb10">详细地址需要精确到号（弄）。</div>' +
                            '<img width="100%" src="' + window.resource.image + '/help/help_09.png"/>' +
                            '</div>';
                        str[9] = '<div class="help_detail_box"><div class="detail_title">员工变动后所属信息如何更改？</div>' +
                            '<div class="lh40 pb10">员工变动后可由商场管理人员在PC管理后台重新进行权限分配。</div>' +
                            '</div>';45
                        var servicestr = '<div class="help_service">' +
                            '<dl>' +
                            '<dd>服务电话  <a class="telPhone" href="javascript:void(0)">400 688 9333</a></dd>' +
                            '<dd style="font-size:1rem;color:#787878;">工作日上午9点 到下午17：30</dd>' +
                            ' </dl>' +
                            '</div>';
                        $('#scroller').append(servicestr);
                        var totalPages = 1;
                        var currentPage = 1;
                        var dataList = new Array();
                        for (var i = 0; i < str.length; i++) {
                            var currentRecords = new Array();
                            currentRecords[i] = [{      
                                text: title[i],
                                html: str[i],
                                id: i
                            }];
                            totalPages = i+1;
                            currentPage = i+1;
                            dataList[i] = currentRecords[i];
                            if (handler) {
                                handler(currentRecords[i], currentPage, totalPages,0);
                            }
                        }
                        Cache.set('dataList-info',dataList);
                        /*var currentRecords = [{
                            text: '如何快速查看小区报表',
                            html: str,
                            id: '1'
                        }];
                        t.dataList = currentRecords;*/
                        // t.list_box.setCurrentPage(currentPage);
                        // t.list_box.setTotalPage(totalPages);
                        //     }
                        // },
                        // function(code, msg) {
                        //     tipsAlert.close();
                        //     tipsAlert.openAlert({
                        //         content: msg
                        //     });
                        // });
                    },
                    appendItem: function(data) {
                        //住宅录入率
                        return tpl(HelpListItemTpl, {
                            data: data
                        });
                    }
                });

                // t.list_box.$el.find('#scroller').removeAttr('class');
                // t.list_box.$el.find('#scroller').removeAttr('id');

            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                var dataList = Cache.get('dataList-info');
                t.$el.find('.helpItem').on('click', function(e) {
                    var hId = $(e.currentTarget).attr('h-id');
                    /*if (t.dataList) {
                        for (var i = 0; i < t.dataList.length; i++) {
                            var data = t.dataList[i];
                            if (data.id == hId) {
                                Cache.set('html', data.html);
                                Cache.set('title', data.text);
                                router.navigate('help_detail', {
                                    trigger: true
                                });
                                break;
                            }
                        }
                    }*/
                    if (dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            var data = dataList[hId];
                            if (data[i].id == hId) {
                                Cache.set('html', data[i].html);
                                Cache.set('title', data[i].text);
                                router.navigate('help_detail', {
                                    trigger: true
                                });
                                break;
                            }
                        }
                    }

                });
            },
            isIos: function() {
                var t = this;
                var userAgentInfo = navigator.userAgent;
                var Agents = ["iPhone",
                    "iPad", "iPod"
                ];
                for (var v = 0; v < Agents.length; v++) {
                    if (userAgentInfo.indexOf(Agents[v]) > 0) {
                        $('.telPhone').attr('href','tel:4006889333'); 
                    }
                }
            }
        });
        return LayoutView;
    });