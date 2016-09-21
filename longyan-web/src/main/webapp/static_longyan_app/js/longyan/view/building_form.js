/**
 * 社区详情 -> 社区编辑/添加社区
 **/
define('js/longyan/view/building_form', [
        'text!js/longyan/template/building_form.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/picker-box',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/api/community',
        'js/api/common'
    ],
    function(BuildingFormTpl, Cache, AlertUI, HeaderView, PickerBox, InputBox, ButtonBox, CommunityApi, CommonApi) {

        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#building-view';
        var form_id = '#building-form';
        var city;
        var LayoutView = Backbone.View.extend({
            events: {},
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
                $('body').css('background-color', '#fff');
                t.$el.html(tpl(BuildingFormTpl, {}));
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '楼栋信息'
                });

                t.building_number_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-number-input',
                    text: '楼栋号',
                    type: "text"
                });
                t.building_floor_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-floor-amount-input',
                    text: '楼层数',
                    type: "tel"
                });
                t.building_unit_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-unit-amount-input',
                    text: '单元数',
                    type: "tel",
                    placeholder: '非必填项'
                });
                t.building_room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-room-amount-input',
                    text: '总户数',
                    type: "tel"
                });

                $('<div class="gap basic-gap owner-gap button-container"></div>').appendTo($(form_id));

                t.community_commit_input = new ButtonBox({
                    el: $('.button-container')
                }, {
                    fieldName: 'community-commit-input',
                    text: '保存',
                }, {
                    Click: function(e) {
                        if (t.config && t.config.update) {
                            //update 小区
                            t.__update(t);
                        } else if (t.config && t.config.create) {
                            //create 小区
                            t.__add(t);
                        }
                    }
                });
                if (t.config && t.config.update) {

                } else if (t.config && t.config.create) {
                    t.community_name_input.setReadOnly(true);
                }
            },
            //添加小区
            __add: function(t) {
                //添加小区逻辑
                if (t.checkForm()) {
                    //获取输入的地理位置
                    //获取输入的社区信息

                }
            },
            //更新小区
            __update: function(t) {
                //更新小区逻辑
                var _tipsAlertConfirmText = '是否修改小区';
                var _tipsAlertSuccessText = '恭喜您,修改成功';
                if (t.config.update_exist) {
                    _tipsAlertConfirmText = '是否创建小区';
                    _tipsAlertSuccessText = '恭喜您,添加成功';
                }
                if (t.checkForm()) {

                }
            },
            loadData: function() {
                var t = this;

            },
            //设置表单
            setFormValue: function(community) {
                var t = this;


            },
            //检查必填字段是否为空
            checkForm: function() {
                var t = this;

                return true;
            }
        });
        return LayoutView;
    });