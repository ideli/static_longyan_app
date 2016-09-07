/**
 *house list view
 *楼栋号列表页,添加楼栋,编辑楼栋,删除楼栋
 **/
define('js/longyan/view/member_list', [
        'text!js/longyan/template/member_list.tpl',
        'text!js/longyan/template/member_list_item.tpl',
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
        'js/api/community',
        'js/util/hybrid'
    ],
    function( MemberListTpl, MemberListItemTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox,CommunityApi,hybrid ) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#member-list-view';
        var form_id = '#member-list-form';
        var viewAdd_id = '#member_build_add-view';
        var formAdd_id = '#member-build-add-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.config = config || {};
                t.render();
                // 加载数据
                t.initEvents();
            },
            render: function() {
                var t = this,
                distance;
                $('body').css('background-color', '#f5f5f5');
                t.$el.html(tpl(MemberListTpl, {}));

                var community = Cache.get('community-manager-object');
                header_view_text = community.name;
                communityId = community.id;
                roomMount = community.roomMount;
                buildingAmount = community.buildingAmount;
                latitude = community.latitude;//小区纬度
                longitude = community.longitude;//小区经度
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_view_text,
                    goBackUrl: function() {
                        Backbone.history.history.back();
                    }
                });

                function setLocationDistance(){
                        hybrid.locationDistance({"latitude": latitude,"longitude": longitude},function(resp){
                            /*tipsAlert.openAlert({
                                content: resp.latitude+"_"+resp.longitude+"_"+resp.location+"_"+resp.distance
                            });*/
                            distance = parseInt(resp.distance);
                            if(distance > 500){
                                $("#member-list-location").show();
                                $("#locationDistanceBox").html(distance);
                            }else{
                                $("#member-list-location").hide();
                            }
                        });

                    return distance;
                }
                setLocationDistance();

                var right_button_text = '添加楼栋';
                var right_button_action = function(e) {
                    e.preventDefault();
                    if(setLocationDistance() > 500){
                        tipsAlert.openToast({
                            content: '距离小区500米内才能添加房栋号'
                        });
                        return false;
                    } else {
                        $('.community-build-add').removeClass('hidden').find('.delete-button').addClass('hidden');
                        t.type = "add";
                        t.building_name_input.setValue('');
                        t.unit_amount_input.setValue('');
                        t.floor_amount_input.setValue('');
                        t.room_amount_input.setValue('');
                    }
                };
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('#header-container').find('.right-box').on('click', right_button_action);
                }

                t.list_box = new ListBox({
                    el: $(form_id)
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        var page = page || 1;
                        var pageSize = 10;
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        CommunityApi.getCommunityBuildingList(page, pageSize, communityId,
                            function(data) {
                               tipsAlert.close();
                                if (data && data.redstarCommunityBuildings) {
                                    var totalRecords = data.redstarCommunityBuildings.length;
                                    if (!totalRecords) {
                                        $('#scroller').hide();
                                        //$('#scroller').after(tpl(NoDataTpl, {}));

                                        t.$el.find('.error-no-data').find('.button').on('click', function() {
                                            //添加楼栋号
                                            router.navigate('', {
                                                trigger: true
                                            });
                                        });
                                    } else {
                                        $('.error-view').remove();
                                        $('#scroller').show();
                                        //var member_listSum = $('.member-item-box').size();
                                        $('#member-list-recorded').find('.buildNumber').text(totalRecords);//共多少楼栋
                                        var roomAmountSum = 0;
                                        for(i = 0; i<totalRecords; i++){
                                             var householdNumber = data.redstarCommunityBuildings[i].roomAmount;
                                             roomAmountSum += householdNumber;
                                             //return roomAmountSum;
                                        }
                                        $('#member-list-recorded').find('.householdNumber').text(roomAmountSum);//楼栋号共多少户
                                    }
                                    //点击刷新地图
                                    $(".updateLocation").on('click',function(){
                                        setLocationDistance();
                                    });
                                    //var totalPages = data.result.totalPages;
                                    //var currentPage = data.result.currentPage;
                                    //var currentRecords = data.result.currentRecords;
                                    //t.list_box.setCurrentPage(currentPage);
                                    //t.list_box.setTotalPage(totalPages);
                                    if (handler) {
                                        handler(data.redstarCommunityBuildings, 1, 1, 0);
                                    }
                                }
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                if (code && code == 408) {
                                    //请求超时
                                    $('#scroller').hide();
                                    var tmp = $('.error-view');
                                    if (tmp && tmp.length > 0) {

                                    } else {
                                        $('#scroller').after(tpl(NoNetworkTpl, {}));
                                        t.$el.find('.error-no-network').off('click');
                                        t.$el.find('.error-no-network').on('click', function() {
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
                    appendItem: function(data) {
                        return tpl(MemberListItemTpl, {
                            data: data
                        });
                    },
                    /*slideEdit: function (elem) {
                        var id = $(elem).parents('.member-item-box').data('key');
                        console.log(id);
                        router.navigate('member_update/' + id, {
                            trigger: true
                        });
                    },*/
                    clickEdit: function (elem) {
                        var elemIndex = $(elem).index();
                        var building = [];
                        var  buildingName = $('.member-item-box').eq(elemIndex).find('.buildingName').text();
                        var  floorAmount = $('.member-item-box').eq(elemIndex).find('.floorNumber').text();
                        var  unitAmount = $('.member-item-box').eq(elemIndex).find('.unitNumber').text();
                        var  roomAmount = $('.member-item-box').eq(elemIndex).find('.householdNumber').text();
                        building.push(buildingName,unitAmount,floorAmount,roomAmount);
                        Cache.set('building-id',  $(elem).data('key'));
                        t.setFormValue(building);
                        t.type = 'update';
                        $('.community-build-add').removeClass('hidden').find('.delete-button').removeClass('hidden');
                        //表单类型
                    }
                });
                t.building_name_input = new InputBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'building_name_input',
                    text: '楼栋号',
                    type: 'text',
                    placeholder: '例：“A、1"'
                });

                t.unit_amount_input= new InputBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'unit_amount_input',
                    type: 'text',
                    text: '单元数',
                    placeholder: '例：“1、甲”  没有则无需填写'
                });

                t.floor_amount_input = new InputBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'floor_amount_input',
                    type: 'text',
                    text: '楼层数',
                    placeholder: '请填写楼栋层数'
                });

                t.room_amount_input = new InputBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'room_amount_input',
                    type: 'text',
                    text: '总户数',
                    placeholder: '请填写楼栋总户数'
                });


                t.save_button = new ButtonBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'save-button',
                    id:'save-button',
                    text: '提交'
                }, {
                    Click: function(e) {
                        if (t.type && t.type == 'add') {
                            //create 楼栋号
                            t.__add(t);
                        } else if (t.type && t.type == 'update') {
                            //update 楼栋号
                           t.__update(t);
                        }
                    }
                });
                t.delete_button = new ButtonBox({
                    el: $(formAdd_id)
                }, {
                    fieldName: 'delete-button hidden',
                    id:'delete-button',
                    text: '删除'
                }, {
                    Click: function(e) {
                    }
                });
                $('#member-list-recorded').find('.totalBuild').text(buildingAmount);//总多少数
                $('#member-list-recorded').find('.totalHousehold').text(roomMount);//总户数
            },
             //添加楼栋号
            __add: function(t) {
                //添加楼栋号逻辑
               if (t.checkForm()) {
                    //获取输入的楼栋号信息
                    var buildingName = t.building_name_input.getValue();
                    var unitAmount = t.unit_amount_input.getValue();
                    var floorAmount = t.floor_amount_input.getValue();
                    var roomAmount = t.room_amount_input.getValue();
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    CommunityApi.addCommunityBuilding(
                        communityId,
                        buildingName,
                        roomAmount,
                        unitAmount,
                        floorAmount,
                        function(data) {
                            tipsAlert.close();
                            if (data && data.buildingId) {
                                //执行成功
                                tipsAlert.openToast({
                                    content: '恭喜您,添加成功',
                                    closeCallback: function() {
                                        tipsAlert.close();
                                        $('.community-build-add').addClass('hidden');

                                    }
                                });
                                $('.community-build-add').addClass('hidden');
                                //重新刷新 reload
                                t.list_box.loadData();
                            } else {
                                tipsAlert.openAlert({
                                    content: '系统异常'
                                });
                            }
                        },
                        function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                }
            },
            //更新楼栋号
            __update: function(t) {
                //更新楼栋号逻辑
                if (t.checkForm()) {
                    //获取输入的楼栋号信息
                    var id = Cache.get('building-id');
                    var buildingName = t.building_name_input.getValue();
                    var unitAmount = t.unit_amount_input.getValue();
                    var floorAmount = t.floor_amount_input.getValue();
                    var roomAmount = t.room_amount_input.getValue();
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    CommunityApi.updateCommunityBuilding(
                        id,
                        communityId,
                        buildingName,
                        roomAmount,
                        unitAmount,
                        floorAmount,
                        function(data) {
                            tipsAlert.close();
                            tipsAlert.openToast({
                                content: '恭喜您,修改成功'
                            });
                            $('.community-build-add').addClass('hidden');
                            //重新刷新 reload
                            t.list_box.loadData();
                        },
                        function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                }
            },
            //删除楼栋号
            __del: function(){
                tipsAlert.open({
                    cancelText: '否',
                    confirmText: '是',
                    content: '你确定要删除此楼栋号吗',
                    onConfirm: function(e) {
                        var ids = Cache.get('building-id');
                        tipsAlert.close();
                        CommunityApi.deleteCommunityBuilding(ids, communityId,
                            function(data) {
                                tipsAlert.close();
                                tipsAlert.openToast({
                                    content: '恭喜您,删除成功',
                                });
                                $('.community-build-add').addClass('hidden');
                                //重新刷新 reload
                                t.list_box.loadData();
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                //显示异常信息
                                tipsAlert.openAlert({
                                    // content: msg
                                });
                            });
                    },
                    onCancel: function(e) {
                        tipsAlert.close();
                    }
                });
            },
            //设置表单
            setFormValue: function(building) {
                var t = this;
                t.building_name_input.setValue(building[0]);
                t.unit_amount_input.setValue(building[1]);
                t.floor_amount_input.setValue(building[2]);
                t.room_amount_input.setValue(building[3]);
            },
            //检查必填字段是否为空
            checkForm: function() {
                var t = this;
                if (!t.building_name_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼栋号'
                    });
                    t.building_name_input.focus();
                    return false;
                }
                if (!t.unit_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入单元数'
                    });
                    t.unit_amount_input.focus();
                    return false;
                }
                if (isNaN(t.unit_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '单元数请输入数字'
                    });
                    t.unit_amount_input.focus();
                    return false;
                }

                if (!t.floor_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼层数'
                    });
                    t.floor_amount_input.focus();
                    return false;
                }
                if (isNaN(t.floor_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '楼层数请输入数字'
                    });
                    t.floor_amount_input.focus();
                    return false;
                }

                if (!t.room_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入总户数'
                    });
                    t.room_amount_input.focus();
                    return false;
                }
                if (isNaN(t.room_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '总户数请输入数字'
                    });
                    t.room_amount_input.focus();
                    return false;
                }

                return true;
            },           //初始化监听器
            initEvents: function() {
                var t = this;
                // t.$el.find('#fix-button').on('click', function(e) {
                //     e.preventDefault();
                //     router.navigate('member_create', {
                //         trigger: true
                //     });
                // });
                                //楼栋号列表每条数据点击事件
                $('#close-btn .icon-lydel').on('click',function(){
                    $('.community-build-add').addClass('hidden');
                });
                                //楼栋号列表每条数据点击事件
                $('.delete-button').on('click',function(){
                    tipsAlert.open({
                        cancelText: '否',
                        confirmText: '是',
                        content: '你确定要删除此楼栋号吗',
                        onConfirm: function(e) {
                            var ids = Cache.get('building-id');
                            tipsAlert.close();
                            CommunityApi.deleteCommunityBuilding(ids, communityId,
                                function(data) {
                                    tipsAlert.close();
                                    tipsAlert.openToast({
                                        content: '恭喜您,删除成功',
                                    });
                                    $('.community-build-add').addClass('hidden');
                                    //重新刷新 reload
                                    t.list_box.loadData();
                                },
                                function(code, msg) {
                                    tipsAlert.close();
                                    //显示异常信息
                                    tipsAlert.openAlert({
                                       // content: msg
                                    });
                                });
                        },
                        onCancel: function(e) {
                            tipsAlert.close();
                        }
                    });
                });

            },
            destroy: function() {
                $(window).off('scroll');
                this.list_box.removeEvent();

            }

        });
        return LayoutView;
    });