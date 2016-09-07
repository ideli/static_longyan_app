/**
 * 首页 -> 关于 -> 反馈
 **/
define('js/longyan/view/feedback', [
        'text!js/longyan/template/feedback.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/textarea-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/feedback',
        'js/util/hybrid'
    ],
    function(feedbackTpl, Cache, AlertUI, HeaderView, InputBox, textareaBox, buttonBox, LinkBox, TipsBar, FeedbackApi, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#feedback-form';
        var form_id = '#textarea-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据  

            },
            render: function() {
                var t = this;
                t.$el.html(tpl(feedbackTpl, {}));
                var __isVerify = function() {
                    if (t.desc_info_input.isVerify()) {
                        if(t.desc_info_input.getValue().length>=5){
                            return true;
                        }
                    }
                    return false;
                }
                var right_button_text = '';
                var header_view_text = '';
                //头部

                /* var str = '<div class="aboutus_center_box"><ul>' +
                 tpl('<li><img src="<#=window.resource.image#>/aboutuscenter1.png" /></li>',{}) +
                 tpl('<li><img src="<#=window.resource.image#>/aboutuscenter2.png" /></li>',{}) +
                 tpl('<li><img src="<#=window.resource.image#>/aboutuscenter3.png" /></li>',{}) +
                 tpl('<li><a href="email:longyan@chinaredstar.com"><img src="<#=window.resource.image#>/aboutuscenter4.png" /></a></li>',{})+
                 tpl('<li><a href="tel:4006889333"><img src="<#=window.resource.image#>/aboutuscenter5.png" /></a></a></li>',{}) +
                 tpl('<li><img src="<#=window.resource.image#>/aboutuscenter6.png" /></li>',{})+
                 '</ul></div>';
                 t.$el.html(tpl(feedback, {
                     content: str
                 }));*/
                //头部
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '意见反馈',
                    goBackUrl: function() { //未保存直接返回提示
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '您是否放弃保存',
                            onConfirm: function(e) {
                                tipsAlert.close();
                                var arealen = t.desc_info_input.getValue().length;
                                if (arealen >= 1) {
                                    Cache.set('desc-Info', '');
                                }
                                hybrid.backToHybrid("Mine");
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });
                //title
                t.tips_bar = new TipsBar({
                    el: $(view_id)
                }, {
                    fieldName: 'title-tips',
                    text: '反馈问题类型'
                });

                //反馈类型遍历
                FeedbackApi.getFeedbackList(function(data) {
                    tipsAlert.close();
                    //success                        
                    //填充数据
                    console.log(data.result)
                    if (data && data.result) {
                        for (var i = 0; i < data.result.length; i++) {
                            var aliasStr = data.result[i].alias;
                            var nameStr = data.result[i].name;
                            var idNumber = data.result[i].id;
                            //反馈意见列表 
                            t.feedback_button = new buttonBox({
                                el: $(view_id)
                            }, {
                                fieldName: aliasStr + '-button',
                                text: nameStr
                            });
                        }
                        $('.button-box').on('click', function() {
                            $('.button-box').removeClass('active');
                            $(this).addClass('active');
                            var feedbackInd = $(this).index();
                            Cache.set('feedback-index', {
                                feedbackInd: feedbackInd
                            });
                        });

                        Cache.set('result-info', data);
                    }
                }, function(code, msg) {
                    tipsAlert.close();
                    //error
                    tipsAlert.openAlert({
                        content: msg
                    });
                });

                //描述问题和意见入框
                t.desc_info_input = new textareaBox({
                    el: $(form_id)
                }, {
                    fieldName: 'Desc-info-input',
                    text: '',
                    maxlength: '100',
                    placeholder: '请简要描述您的问题和意见'
                }, {
                    Keyup: function() {
                        t.changeLength();
                    }
                });


                //联系电话入框
                t.phone_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'phone-input',
                    text: '联系电话',
                    type: 'number',
                    placeholder: '选填，便于我们与您联系'
                }, {
                    Keyup: function() {
                        /*__keyup();
                        t.verifylen();*/
                    }
                });

                //文本域输入时，字符提示
                $("textarea").after("<div class='textarea-length'>100/<span id='textarea-count'>100</span></div>");

                //保存按钮
                t.submit_button = new LinkBox({
                    el: $('#header-container')
                }, {
                    fieldName: 'submit-button',
                    text: '保存'
                });
                //文本域输入时，浮层显示
                /*$('.Desc-info-input textarea').on('click', function() {
                         $('#textarea-box-pop-view').show();
                         $('#header-container-Desc-info-input').removeClass('hidden');
                });*/
                //浮层文本域输入事件
                /*$('#textarea-box-pop-view textarea').keyup(function() {
                     count = 100,
                     len = $('#textarea-box-pop-view textarea').val().length;
                     $("#textarea-box-pop-view #textarea-count").text(count-len);
                     if(len>=count){
                         $("#textarea-count").text(0);
                          //限制文字长度的提示
                         tipsAlert.openAlert({
                                     content: '填写内容不能超过100字'
                          });
                     }
                });*/

                //浮层返回按钮事件
                /* $('#textarea-box-pop-view .goback-button').on('click', function() {
                          $('#textarea-box-pop-view').hide();
                          $('#header-container-Desc-info-input').addClass('hidden');
                 });*/
                //确定按钮
                /*$('#textarea-box-pop-view .submit-button-view').on('click', function() {
                         $('#textarea-box-pop-view').hide();
                         $('#header-container-Desc-info-input').addClass('hidden');
                         var textVal = $('#textarea-box-pop-view textarea').val(); 
                         t.desc_info_input.setValue(textVal);
                         count = 100,
                         len = $('#textarea-box-pop-view textarea').val().length;
                         $(".Desc-info-input #textarea-count").text(count-len);
                });*/
                //跳转至文本域输入页面
                /* t.$el.find('.Desc-info-input').on('click', function() {
                          var descInfo = t.desc_info_input.getValue();
                          Cache.set('desc-Info', {descInfo: descInfo});
                          router.navigate('feedback_textarea', {
                              trigger: true
                          });
                 });*/
                //保存按钮
                t.$el.find('.submit-button').on('click', function() {
                    var resultInfo = Cache.get('result-info');
                    var userInfo = Cache.get('user-info');
                    var feedbackLen = $('#feedback-form .active').length;
                    var feedbackIndex = Cache.get('feedback-index');
                    var feedbackInd = parseInt(feedbackIndex.feedbackInd) - 1;
                    if (feedbackLen == 1) {
                        if (__isVerify()) {
                            //保存意见反馈
                            var descInfoContent = t.desc_info_input.getValue();
                            var descInfoAlias = resultInfo.result[feedbackInd].alias;
                            var descInfoName = userInfo.xingMing;
                            var descInfoPhone = t.phone_input.getValue();
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });

                            FeedbackApi.feedbackCreate(descInfoContent, descInfoAlias, descInfoName, descInfoPhone, function() {
                                    //success
                                    setTimeout("router.navigate('feedback_success', {trigger: true});", "2000");
                                    //关闭loading
                                    // tipsAlert.close();
                                },
                                function(code, msg) {
                                    //关闭loading
                                    tipsAlert.close();
                                    //error
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                        } else {
                            tipsAlert.openAlert({
                                content: '填写内容不能少于5个字'
                            });
                        }
                    } else {
                        tipsAlert.openAlert({
                            content: '请选择反馈类型'
                        });
                        return false;
                    }

                });
            }, //文本域输入时，字符提示
            changeLength: function() {
                var t = this,
                    count = 100,
                    len = t.desc_info_input.getValue().length;
                $("#textarea-count").text(count - len);
                if (len >= count) {
                    $("#textarea-count").text(0);
                    //限制文字长度的提示
                    tipsAlert.openAlert({
                        content: '填写内容不能超过100字'
                    });
                }
            }

        });
        return LayoutView;
    });