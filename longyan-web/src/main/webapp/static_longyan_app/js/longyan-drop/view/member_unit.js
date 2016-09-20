/**
 *house list view
 *房屋信息默认页
 **/
define('js/longyan/view/member_unit', [
        'text!js/longyan/template/member_unit.tpl',
        'text!js/longyan/template/no_data.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/element/view/title-box',
        'js/element/view/unit-box',
        'js/element/view/group-button-box',
        'js/api/member'
    ],
    function(MemberUnitTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI,
        HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, TitleBox, UnitBox, GroupButtonBox, MemberApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#member-unit-view';
        var form_id = '#member-unit-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                // 加载数据
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#f5f5f5');

                t.right_button_text = '编辑';
                t.memberUnits = []; //记录单元item
                t.memberUnitTitles = []; //记录单元title item
                t.itemNum = 0; //第一个item  记录第几个item，方便添加 保证fieldName 唯一
                t.bottom_delete_button_box = ''; //底部删除按钮
                var right_button_action = function(e) {
                    e.preventDefault();
                    //点击编辑 进入删除界面，勾选删除
                    if (t.right_button_text === '编辑') {
                        t.right_button_text = '取消'
                    } else {
                        t.right_button_text = '编辑'
                    }
                    t.$el.find('#header-container').find('.right-box').html(t.right_button_text);

                    if (t.memberUnits && t.memberUnits.length > 0) {
                        for (var i = 0; i < t.memberUnits.length; i++) {
                            var unit = t.memberUnits[i];
                            //显示编辑界面
                            unit.toEditView(function() {
                                t.changeBottomBtn();
                            });
                        }
                    }
                };
                t.$el.html(tpl(MemberUnitTpl, {}));

                // var community = Cache.get('community-manager-object');
                var header_view_text = "楼栋号";

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_view_text,
                    goBackUrl: function() {
                        Backbone.history.history.back();
                    }
                });

                if (t.right_button_text && t.right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(t.right_button_text);
                    t.$el.find('#header-container').find('.right-box').on('click', right_button_action);
                }

                t.member_title_box = new TitleBox({
                    el: $(form_id)
                }, {
                    fieldName: 'member_title_box',
                    text: '1号楼',
                    readonly: 'readonly'
                }, {
                    Click: function(e) {
                        t.member_title_box.removeReadonly();
                    },
                    Blur: function(e) {
                        t.member_title_box.setReadonly();
                    }
                });

                //底部按钮组
                t.bottom_group_button_box = new GroupButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'bottom_group_button_box',
                    clazz: 'fixed-bottom g-hrz',
                    btns: [{
                        text: '添加单元',
                        clazz: 'green u-full',
                        disable: false,
                        Click: function() {
                            t.addUnitAction();
                        }
                    }, {
                        text: '保存',
                        clazz: 'blue u-full',
                        disable: false,
                        Click: function() {

                        }
                    }]
                });
                //删除按钮
                t.bottom_delete_button_box = new GroupButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'bottom_delete_button_box',
                    clazz: 'fixed-bottom g-hrz hidden',
                    btns: [{
                        text: '<i class="iconfont icon-longyanjiahao"></i>删除',
                        clazz: 'grey u-full ',
                        disable: false,
                        Click: function() {
                            //删除事件

                        }
                    }]
                });

                // t.add_btn_box = new ButtonBox({
                //     el: $(form_id)
                // }, {
                //     text: '<i class="iconfont icon-longyanjiahao"></i> 添加'
                // });

                var datas = [{
                    value: '123'
                }, {
                    value: '123'
                }, {
                    value: '123'
                }];
                t.addUnitAction(datas);

            },
            //改变底部按钮，是删除  还是 添加单元、保存按钮
            changeBottomBtn: function() {
                var t = this;
                //delete按钮是否是影藏的
                if (t.bottom_delete_button_box && t.bottom_delete_button_box.isHidden()) {
                    //如果是影藏的，就显示删除按钮，影藏添加按钮
                    t.bottom_delete_button_box.setHidden(false);
                    t.bottom_group_button_box.setHidden(true);
                } else {
                    t.bottom_delete_button_box.setHidden(true);
                    t.bottom_group_button_box.setHidden(false);
                }
            },
            //添加单元
            addUnitAction: function(datas) {
                var t = this;
                //item start
                var unit_title_box = new TitleBox({
                    el: $(form_id)
                }, {
                    fieldName: 'unit_title_box' + t.itemNum,
                    text: '单元名称',
                    readonly: 'readonly',
                    clazz: ' border-bottom'
                }, {
                    Click: function() {
                        unit_title_box.removeReadonly();
                    },
                    Blur: function() {
                        unit_title_box.setReadonly();
                    }
                });
                unit_title_box.removeReadonly();
                t.memberUnitTitles.push(unit_title_box);

                var unit_item_box = new UnitBox({
                    el: $(form_id)
                }, {
                    fieldName: 'unit_item_box' + t.itemNum,
                    text: '房号',
                    datas: datas
                }, {
                    Click: function(e) {

                    }
                });
                t.memberUnits.push(unit_item_box);
                t.itemNum++;
                //item end
            },
            //初始化监听器
            initEvents: function() {
                // var t = this;
                // t.$el.find('#fix-button').on('click', function(e) {
                //     e.preventDefault();
                //     router.navigate('member_create', {
                //         trigger: true
                //     });
                // });
            },
            destroy: function() {

            }

        });
        return LayoutView;
    });