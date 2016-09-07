/**
 * think search view
 * 联想查找界面
 **/
define('js/longyan/view/think_search', [
        'text!js/longyan/template/think_search.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/input-percentage-box',
        'js/element/view/location-box',
        'js/element/view/button-box',
        'js/api/community',
        'js/api/common'
    ],
    function(CommunityListTpl, Cache, AlertUI, HeaderView, InputBox, ThinkInputBox, InputPercentageBox, LocationBox, ButtonBox, CommunityApi, CommonApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#think-search-view';
        var form_id = '#think-search-form';
        var search_alias = {
            community: 'community'
        }
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
                t.loadData();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(CommunityListTpl, {}));



                var right_button_text = '保存';
                var header_view_text = '确定';
                var right_button_action = function(e) {};
                if (t.config && t.config.alias && t.config.alias == search_alias.community) {
                    //创建小区      
                    header_view_text = '小区名称';
                } else if (t.config && t.config.alias && t.config.alias == search_alias.community) {
                    //修改小区
                    header_view_text = '开发商';
                }
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_view_text
                });
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', {
                        t: t
                    }, right_button_action);
                }

                // t.community_name_input = new InputBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'keyword-input',
                //     placeholder: '名称',
                //     no_label: true
                // });

                t.community_name_input = new ThinkInputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'keyword-input',
                    text: '',
                    placeholder: '名称'
                }, {
                    Keyup: function() {
                        //获取联想社区列表
                        // var area = {
                        //     provinceCode: _provinceCode,
                        //     cityCode: _cityCode,
                        //     areaCode: t.location_input.getValue()['col1Key']
                        // }

                        //获取省市区选择器的信息
                        var area = t.getLocation();

                        if (area && area.cityCode && area.areaCode) {
                            var provinceCode = area.provinceCode;
                            var cityCode = area.cityCode;
                            var areaCode = area.areaCode;
                            var text = t.community_name_input.getText();
                            console.log(text);
                            if (text && text.length > 0) {
                                CommunityApi.searchName(text, provinceCode, cityCode, areaCode, function(data) {
                                    if (data && data.result && data.result.length > 0) {
                                        t.community_name_input.setSearchBox(data.result, text, 'name', 'id');
                                    } else {
                                        //清空搜索框
                                        t.community_name_input.clearSearchBox();
                                    }
                                }, function(code, msg) {
                                    //显示异常信息
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                            } else {
                                //清空搜索框
                                t.community_name_input.clearSearchBox();
                            }

                        }
                    },
                    SelectValue: function(value, text) {
                        if (value) {
                            //获取社区对象                            
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });
                            CommunityApi.getCommunityById(value, function(data) {
                                tipsAlert.close();
                                //返回数据
                                if (data && data.community) {
                                    //放入缓存
                                    console.log(data.community);
                                    Cache.set('community-manager-object', data.community);
                                    //跳转到更新界面
                                    router.navigate('community_update_exist/' + value, {
                                        trigger: true
                                    });
                                } else {
                                    tipsAlert.openAlert({
                                        content: '系统异常'
                                    });
                                }
                            }, function(code, msg) {
                                tipsAlert.close();
                                //显示异常信息
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                        }
                    }
                });

            },
            loadData: function() {
                var t = this;

            }
        });
        return LayoutView;
    });