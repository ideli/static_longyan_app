/**
 * common alert ui
                        var tipsAlert = tipsAlert || new AlertUI();
                        tipsAlert.openAlert({
                            content: '系统异常'
                        });

                //弹出对话框
                tipsAlert.open({
                    cancelText: '取消',
                    confirmText: '确认',
                    content: 'html代码',
                    onConfirm: function(e) {
                        tipsAlert.close();
                    },
                    onCancel: function(e) {
                        tipsAlert.close();
                    }
                });

                 tipsAlert.openLoading({
                    content: '加载中...'
                });

                 tipsAlert.openToast({
                        content: message
                    });

 * @author wangxinwei101
 */
define('js/components/alert_ui', function(require) {
    var defaultOptions = {
        // whether show close button
        closeable: true,

        // whether need mask
        needMask: true,

        // whether is toast
        toastText: false,
        // toast delay time seconds
        toastDelayTime: 2,

        // loading
        loadingText: '',

        // whether show title
        titleText: '',
        // alert content
        content: '',

        // cancel button
        cancelText: '取消',
        // confirm button
        confirmText: '确定',

        // prevent page scroll event
        preventPageScroll: true,

        // alert ui open event handler
        // onOpen: function(){},
        // alert ui close event handler
        // onClose: function(){},
        // alert ui confirm button click event handler
        // onConfirm: function(){},
        // alert ui cancel button click event handler
        // onCancel: function(){}

        // alert ui container
        $el: $('.pageContainer')
    };
    // var TplAlert = require('text!js/template/alert_ui.tpl');
    var TplAlert = '<div class="ui-alert-fund591-wrapper<#if(typeof layout!=="undefined" &&layout==1){echo(" ui-alert-fund591-wrapper2")}#>">\
                            <#if(titleText){#>\
                            <div class="ui-alert-fund591-head border-gray-b g-hrz">\
                                <#if(closeable){#><a class="icon ui-alert-fund591-close-btn lufaxfont" title="关闭">&#xe649;</a><#}#>\
                                <span class="ui-alert-fund591-title u-full"><#= titleText #></span>\
                            </div>\
                            <#}#>\
                            <div class="ui-alert-fund591-body">\
                                <div class="J_content">\
                                    <#if(content){echo(content)}#>\
                                    <#if(toastText){echo(toastText)}#>\
                                </div>\
                                <#if(loadingText || typeof openLoading!=="undefined"){#>\
                                <div class="J_loading loading-container">\
                                    <div class="loading<#if(typeof skin!=="undefined" && skin==1){echo(" loading2")}#> animate u-wrap">\
                                        <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>\
                                    </div>\
                                    <div class="loading_box_text font3 weak-color-2 u-full"><span><#=loadingText#></span></div>\
                                </div>\
                                <#}#>\
                            </div>\
                            <#if(cancelText || confirmText){#>\
                            <div class="ui-alert-fund591-foot border-gray-t">\
                                <#if(cancelText){#><span class="button J_btn_cancel"><#= cancelText #></span><#}#>\
                                <#if(confirmText){#><span class="button border-gray-l J_btn_confirm"><#= confirmText #></span><#}#>\
                            </div>\
                            <#}#>\
                        </div>';
    var Alert = Backbone.View.extend({

        initialize: function(options, config) {
            this.options = $.extend({}, defaultOptions, options);
            this.$el = this.options.$el;
            this.render();
        },

        render: function(options) {
            var self = this;
            options = options || {};
            if (this.options.needMask && options.needMask !== false) {
                this.mask = this.mask || $('<div class="hidden"></div>');
                this.mask.appendTo(this.$el);

                //prevent page scroll
                if (this.options.preventPageScroll) {
                    this.mask.unbind().on('touchmove', function(e) {
                        return false;
                    });
                }
            }

            this.dialogue = this.dialogue || $('<div class="hidden"></div>');
            this.dialogue.html(tpl(TplAlert, this.getOptions(options)));
            this.dialogue.appendTo(this.$el);

            this.contentDom = this.dialogue.find('.J_content');
            this.loadingDom = this.dialogue.find('.J_loading');
            this.confirmBtn = this.dialogue.find('.J_btn_confirm');
            this.cancelBtn = this.dialogue.find('.J_btn_cancel');
            this.closeBtn = this.dialogue.find('.ui-alert-fund591-close-btn');
            this.input = this.dialogue.find('input.J_input');

            this.confirmBtn.unbind().on('click', function() {
                self.confirm(options);
            }).on('touchstart', function() {
                self.confirmTouch(options);
            });
            this.cancelBtn.unbind().on('click', function() {
                self.cancel(options);
            });
            this.closeBtn.unbind().on('click', function() {
                self.close(options);
            });

            //prevent page scroll
            if (this.options.preventPageScroll) {
                this.dialogue.unbind().on('touchmove', function(e) {
                    return false;
                });
            }

            return this;
        },

        events: {},

        close: function(options) {
            this.input.val('');
            this.options.needMask && this.mask.addClass("hidden");
            this.dialogue.addClass("hidden");

            // add close event listener
            if (options && options.onClose) {
                options.onClose.apply(this);
            } else if (this.options.onClose) {
                this.options.onClose.apply(this);
            }

            this.destroy();
        },

        confirm: function(options) {
            // if confirm button disabled
            if (this.confirmBtn.hasClass('disabled')) {
                return;
            }

            // add confirm event listener
            if (options && options.onConfirm) {
                options.onConfirm.apply(this);
            } else if (this.options.onConfirm) {
                this.options.onConfirm.apply(this);
            } else {
                this.close();
            }
        },

        confirmTouch: function(options) {
            // if confirm button disabled
            if (this.confirmBtn.hasClass('disabled')) {
                return;
            }

            // add confirm event listener
            if (options && options.onConfirmTouch) {
                options.onConfirmTouch.apply(this);
            } else if (this.options.onConfirmTouch) {
                this.options.onConfirmTouch.apply(this);
            }
        },

        cancel: function(options) {
            // if cancel button disabled
            if (this.cancelBtn.hasClass('disabled')) {
                return;
            }

            // add cancel event listener
            if (options && options.onCancel) {
                options.onCancel.apply(this);
            } else if (this.options.onCancel) {
                this.options.onCancel.apply(this);
            } else {
                this.close();
            }
        },

        open: function(options) {
            options = this.getOptions(options);
            // not toast and not loading, then render normal dialogue
            if (!options.toastText && !options.loadingText) {
                this.render(options);
            }

            this.dialogue.attr('class', 'ui-alert-fund591').attr('style', '');
            this.mask && this.mask.attr('class', 'ui-alert-fund591-mask');

            //vertical middle dialogue
            var self = this,
                win = $(window),
                top = win.height() - this.dialogue.height();

            this.dialogue.css({
                top: top / 2 + win.scrollTop()
            });

            this.mask.css({
                // height: Math.max(win.height(), document.body.scrollHeight)
                height: Math.max(window.screen.height, document.body.scrollHeight)
            });

            // add open event listener
            if (options.onOpen) {
                options.onOpen.apply(this);
            }

            if (options.toastText) {
                openToast();
            }

            if (options.loadingText) {
                openLoading();
            }
        },

        getOptions: function(options) {
            var temp = $.extend({}, this.options);
            return $.extend(temp, options || {});
        },

        openAlert: function(options) {
            this.open($.extend({
                cancelText: '',
            }, options || {}, {
                content: '<p class="plain-text">' + (options.content || this.options.content) + '</p>'
            }));
        },

        /**
         * [openLoading description]
         * @param  {Object} options {cotent:, onClose:, skin:0/1, layout:0/1, width:, height:}
         * @return {[type]}         [description]
         */
        openLoading: function(options) {
            options.loadingText = options.content;
            options.openLoading = true;
            options.content = '';
            // this.render(this.getOptions({loadingText: options.content, onClose: options.onClose, }));

            this.render(this.getOptions(options));
            this.loadingWrapper = this.dialogue.find('.ui-alert-fund591-wrapper');
            this.dialogue.attr('class', 'ui-alert-fund591 ui-alert-fund591-loading');
            this.mask && this.mask.attr('class', 'ui-alert-fund591-mask ui-alert-fund591-mask-loading');

            var addStyle = {};
            if (options.width) {
                addStyle.width = options.width;
            }
            if (options.height) {
                addStyle.height = options.height;
            }
            this.loadingWrapper.css(addStyle);

            var self = this,
                win = $(window),
                left = win.width() - this.loadingWrapper.width(),
                top = win.height() - this.loadingWrapper.height();

            this.dialogue.css({
                left: left / 2,
                top: top / 2
            });

            this.loadingWrapper.addClass('in');
        },

        openToast: function(options) {
            clearTimeout(this.toastTimer);
            clearTimeout(this.toastCloseTimer);

            options.toastText = options.content;
            options.content = '';

            this.render(this.getOptions(options));
            this.toastWrapper = this.dialogue.find('.ui-alert-fund591-wrapper');
            this.dialogue.attr('class', 'ui-alert-fund591 ui-alert-fund591-toast');

            if (this.mask && options.needMask !== false) {
                this.mask && this.mask.attr('class', 'ui-alert-fund591-mask ui-alert-fund591-mask-toast');
            }

            var self = this,
                win = $(window),
                left = win.width() - this.toastWrapper.width(),
                top = win.height() - this.toastWrapper.height(),
                switchClass = options.noAnimation ? 'show' : 'in',
                toastDelayTime = options.toastDelayTime || this.options.toastDelayTime;

            this.dialogue.css({
                left: left / 2,
                top: top / 2
            });

            // update toast delay time
            if (options.toastDelayTime) {
                toastDelayTime = options.toastDelayTime;
            }
            this.toastWrapper.addClass(switchClass);

            this.toastTimer = setTimeout(function() {
                self.toastWrapper.removeClass(switchClass);
                options.closeCallback && options.closeCallback.apply(this);
            }, toastDelayTime * 1000);

            this.toastCloseTimer = setTimeout(function() {
                self.close();
            }, toastDelayTime * 1000 + 500);

            options.openCallback && options.openCallback.apply(this);
        },

        destroy: function() {
            this.options.needMask && this.mask.remove();
            this.dialogue.remove();
        }
    });
    return Alert;
});