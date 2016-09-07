define('js/components/picker', function() {

    var LayoutView = Backbone.View.extend({
        events: {},
        initialize: function(options, config) {
            var t = this;

            $.device = (function($) {
                var device = {};
                var ua = navigator.userAgent;
                var android = ua.match(/(Android);?[\s\/]+([\d.]+)?/);
                var ipad = ua.match(/(iPad).*OS\s([\d_]+)/);
                var ipod = ua.match(/(iPod)(.*OS\s([\d_]+))?/);
                var iphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/);

                device.ios = device.android = device.iphone = device.ipad = device.androidChrome = false;
                // Android
                if (android) {
                    device.os = 'android';
                    device.osVersion = android[2];
                    device.android = true;
                    device.androidChrome = ua.toLowerCase().indexOf('chrome') >= 0;
                }
                if (ipad || iphone || ipod) {
                    device.os = 'ios';
                    device.ios = true;
                }
                // iOS
                if (iphone && !ipod) {
                    device.osVersion = iphone[2].replace(/_/g, '.');
                    device.iphone = true;
                }
                if (ipad) {
                    device.osVersion = ipad[2].replace(/_/g, '.');
                    device.ipad = true;
                }
                if (ipod) {
                    device.osVersion = ipod[3] ? ipod[3].replace(/_/g, '.') : null;
                    device.iphone = true;
                }


                return device;
            })($);

            if ($(".picker-container").length == 0) {
                $('<div class="picker-container"></div>').appendTo($("body"));
            }
            var defaults = {
                modalButtonOk: '确定',
                modalButtonCancel: '取消',
                modalPreloaderTitle: '加载中',
                modalContainer: $(".picker-container")[0] || document.body
            };

            $.support = (function() {
                var support = {
                    touch: !!(('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch)
                };
                return support;
            })();

            $.touchEvents = {
                start: $.support.touch ? 'touchstart' : 'mousedown',
                move: $.support.touch ? 'touchmove' : 'mousemove',
                end: $.support.touch ? 'touchend' : 'mouseup'
            };
            $.getTranslate = function(el, axis) {
                var matrix, curTransform, curStyle, transformMatrix;

                // automatic axis detection
                if (typeof axis === 'undefined') {
                    axis = 'x';
                }

                curStyle = window.getComputedStyle(el, null);
                if (window.WebKitCSSMatrix) {
                    // Some old versions of Webkit choke when 'none' is passed; pass
                    // empty string instead in this case
                    transformMatrix = new WebKitCSSMatrix(curStyle.webkitTransform === 'none' ? '' : curStyle.webkitTransform);
                } else {
                    transformMatrix = curStyle.MozTransform || curStyle.OTransform || curStyle.MsTransform || curStyle.msTransform || curStyle.transform || curStyle.getPropertyValue('transform').replace('translate(', 'matrix(1, 0, 0, 1,');
                    matrix = transformMatrix.toString().split(',');
                }

                if (axis === 'x') {
                    //Latest Chrome and webkits Fix
                    if (window.WebKitCSSMatrix)
                        curTransform = transformMatrix.m41;
                    //Crazy IE10 Matrix
                    else if (matrix.length === 16)
                        curTransform = parseFloat(matrix[12]);
                    //Normal Browsers
                    else
                        curTransform = parseFloat(matrix[4]);
                }
                if (axis === 'y') {
                    //Latest Chrome and webkits Fix
                    if (window.WebKitCSSMatrix)
                        curTransform = transformMatrix.m42;
                    //Crazy IE10 Matrix
                    else if (matrix.length === 16)
                        curTransform = parseFloat(matrix[13]);
                    //Normal Browsers
                    else
                        curTransform = parseFloat(matrix[5]);
                }

                return curTransform || 0;
            };
            $.requestAnimationFrame = function(callback) {
                if (window.requestAnimationFrame) return window.requestAnimationFrame(callback);
                else if (window.webkitRequestAnimationFrame) return window.webkitRequestAnimationFrame(callback);
                else if (window.mozRequestAnimationFrame) return window.mozRequestAnimationFrame(callback);
                else {
                    return window.setTimeout(callback, 1000 / 60);
                }
            };

            $.cancelAnimationFrame = function(id) {
                if (window.cancelAnimationFrame) return window.cancelAnimationFrame(id);
                else if (window.webkitCancelAnimationFrame) return window.webkitCancelAnimationFrame(id);
                else if (window.mozCancelAnimationFrame) return window.mozCancelAnimationFrame(id);
                else {
                    return window.clearTimeout(id);
                }
            };


            $.fn.transform = function(transform) {
                for (var i = 0; i < this.length; i++) {
                    var elStyle = this[i].style;
                    elStyle.webkitTransform = elStyle.MsTransform = elStyle.msTransform = elStyle.MozTransform = elStyle.OTransform = elStyle.transform = transform;
                }
                return this;
            };

            $.fn.transition = function(duration) {
                if (typeof duration !== 'string') {
                    duration = duration + 'ms';
                }
                for (var i = 0; i < this.length; i++) {
                    var elStyle = this[i].style;
                    elStyle.webkitTransitionDuration = elStyle.MsTransitionDuration = elStyle.msTransitionDuration = elStyle.MozTransitionDuration = elStyle.OTransitionDuration = elStyle.transitionDuration = duration;
                }
                return this;
            };


            $.fn.transitionEnd = function(callback) {
                var events = ['webkitTransitionEnd', 'transitionend', 'oTransitionEnd', 'MSTransitionEnd', 'msTransitionEnd'],
                    i, dom = this;

                function fireCallBack(e) {
                    /*jshint validthis:true */
                    if (e.target !== this) return;
                    callback.call(this, e);
                    for (i = 0; i < events.length; i++) {
                        dom.off(events[i], fireCallBack);
                    }
                }
                if (callback) {
                    for (i = 0; i < events.length; i++) {
                        dom.on(events[i], fireCallBack);
                    }
                }
                return this;
            };

            $.pickerModal = function(pickerModal, removeOnClose) {
                if (typeof removeOnClose === 'undefined') removeOnClose = true;
                if (typeof pickerModal === 'string' && pickerModal.indexOf('<') >= 0) {
                    pickerModal = $(pickerModal);
                    if (pickerModal.length > 0) {
                        if (removeOnClose) pickerModal.addClass('remove-on-close');
                        $(defaults.modalContainer).append(pickerModal[0]);
                    } else return false; //nothing found
                }
                pickerModal = $(pickerModal);
                if (pickerModal.length === 0) return false;
                pickerModal.show();
                $.openModal(pickerModal);
                return pickerModal[0];
            };



            $.openModal = function(modal) {
                modal = $(modal);
                var isModal = modal.hasClass('modal');
                if ($('.modal.modal-in:not(.modal-out)').length && defaults.modalStack && isModal) {
                    $.modalStack.push(function() {
                        $.openModal(modal);
                    });
                    return;
                }
                var isPopover = modal.hasClass('popover');
                var isPopup = modal.hasClass('popup');
                var isLoginScreen = modal.hasClass('login-screen');
                var isPickerModal = modal.hasClass('picker-modal');
                var isToast = modal.hasClass('toast');
                if (isModal) {
                    modal.show();
                    modal.css({
                        marginTop: -Math.round(modal.outerHeight() / 2) + 'px'
                    });
                }
                if (isToast) {
                    modal.css({
                        marginLeft: -Math.round(modal.outerWidth() / 2 / 1.185) + 'px' //1.185 是初始化时候的放大效果
                    });
                }

                var overlay;
                if (!isLoginScreen && !isToast) {
                    if ($('.modal-overlay').length === 0 && !isPopup) {
                        $(defaults.modalContainer).append('<div class="modal-overlay"></div>');
                    }
                    if ($('.popup-overlay').length === 0 && isPopup) {
                        $(defaults.modalContainer).append('<div class="popup-overlay"></div>');
                    }
                    overlay = isPopup ? $('.popup-overlay') : $('.modal-overlay');
                }

                //Make sure that styles are applied, trigger relayout;
                var clientLeft = modal[0].clientLeft;

                // Trugger open event
                modal.trigger('open');

                // Picker modal body class
                if (isPickerModal) {
                    $(defaults.modalContainer).addClass('with-picker-modal');
                }

                // Classes for transition in
                if (!isLoginScreen && !isToast) overlay.addClass('modal-overlay-visible');
                modal.removeClass('modal-out').addClass('modal-in').transitionEnd(function(e) {
                    if (modal.hasClass('modal-out')) modal.trigger('closed');
                    else modal.trigger('opened');
                });
                return true;
            };



            $.closeModal = function(modal) {
                modal = $(modal || '.modal-in');
                if (typeof modal !== 'undefined' && modal.length === 0) {
                    return;
                }
                var isModal = modal.hasClass('modal');
                var isPopover = modal.hasClass('popover');
                var isPopup = modal.hasClass('popup');
                var isLoginScreen = modal.hasClass('login-screen');
                var isPickerModal = modal.hasClass('picker-modal');

                var removeOnClose = modal.hasClass('remove-on-close');

                var overlay = isPopup ? $('.popup-overlay') : $('.modal-overlay');
                if (isPopup) {
                    if (modal.length === $('.popup.modal-in').length) {
                        overlay.removeClass('modal-overlay-visible');
                    }
                } else if (isPickerModal) {
                    overlay.removeClass('modal-overlay-visible');
                }

                modal.trigger('close');

                // Picker modal body class
                if (isPickerModal) {
                    $(defaults.modalContainer).removeClass('with-picker-modal');
                    $(defaults.modalContainer).addClass('picker-modal-closing');
                }

                if (!isPopover) {
                    modal.removeClass('modal-in').addClass('modal-out').transitionEnd(function(e) {
                        if (modal.hasClass('modal-out')) modal.trigger('closed');
                        else modal.trigger('opened');

                        if (isPickerModal) {
                            $(defaults.modalContainer).removeClass('picker-modal-closing');
                        }
                        if (isPopup || isLoginScreen || isPickerModal) {
                            modal.removeClass('modal-out').hide();
                            if (removeOnClose && modal.length > 0) {
                                modal.remove();
                            }
                        } else {
                            modal.remove();
                        }
                    });
                    if (isModal && defaults.modalStack) {
                        $.modalStackClearQueue();
                    }
                } else {
                    modal.removeClass('modal-in modal-out').trigger('closed').hide();
                    if (removeOnClose) {
                        modal.remove();
                    }
                }
                return true;
            };
            var Picker = function(params) {
                var p = this;
                var defaults = {
                    updateValuesOnMomentum: false,
                    updateValuesOnTouchmove: true,
                    rotateEffect: false,
                    momentumRatio: 7,
                    freeMode: false,
                    // Common settings
                    scrollToInput: true,
                    inputReadOnly: true,
                    convertToPopover: true,
                    onlyInPopover: false,
                    toolbar: true,
                    needMask: false, //是否需要点击遮罩不关闭  false 为点击关闭
                    toolbarCloseText: '确定',
                    toolbarTemplate: '<header class="bar bar-nav">\
          <button class="button button-link pull-right close-picker">确定</button>\
          <h1 class="title"></h1>\
          </header>',
                };
                params = params || {};
                for (var def in defaults) {
                    if (typeof params[def] === 'undefined') {
                        params[def] = defaults[def];
                    }
                }
                p.params = params;
                p.cols = [];
                p.initialized = false;

                // Inline flag
                p.inline = p.params.container ? true : false;

                // 3D Transforms origin bug, only on safari
                var originBug = $.device.ios || (navigator.userAgent.toLowerCase().indexOf('safari') >= 0 && navigator.userAgent.toLowerCase().indexOf('chrome') < 0) && !$.device.android;

                // Should be converted to popover
                function isPopover() {
                    var toPopover = false;
                    if (!p.params.convertToPopover && !p.params.onlyInPopover) return toPopover;
                    if (!p.inline && p.params.input) {
                        if (p.params.onlyInPopover) toPopover = true;
                        else {
                            if ($.device.ios) {
                                toPopover = $.device.ipad ? true : false;
                            } else {
                                if ($(window).width() >= 768) toPopover = true;
                            }
                        }
                    }
                    return toPopover;
                }

                function inPopover() {
                    if (p.opened && p.container && p.container.length > 0 && p.container.parents('.popover').length > 0) return true;
                    else return false;
                }

                // Value
                p.setValue = function(arrValues, transition) {
                    var valueIndex = 0;
                    for (var i = 0; i < p.cols.length; i++) {
                        if (p.cols[i] && !p.cols[i].divider) {
                            p.cols[i].setValue(arrValues[valueIndex], transition);
                            valueIndex++;
                        }
                    }
                };


                p.updateValue = function() {
                    var newValue = [];
                    var newDisplayValue = [];
                    for (var i = 0; i < p.cols.length; i++) {
                        if (!p.cols[i].divider) {
                            newValue.push(p.cols[i].value);
                            newDisplayValue.push(p.cols[i].displayValue);
                        }
                    }
                    if (newValue.indexOf(undefined) >= 0) {
                        return;
                    }
                    p.value = newValue;
                    p.displayValue = newDisplayValue;
                    if (p.params.onChange) {
                        p.params.onChange(p, p.value, p.displayValue);
                    }
                    if (p.input && p.input.length > 0) {
                        $(p.input).val(p.params.formatValue ? p.params.formatValue(p, p.value, p.displayValue) : p.value.join(' '));

                        if (p.input[0].tagName.toUpperCase() != "INPUT") {
                            p.input[0].dataset.selected = p.value.join(' ');
                            p.input[0].dataset.selectedName = p.displayValue.join(' ');
                        }

                        $(p.input).trigger('change');
                    }
                };

                // Columns Handlers
                p.initPickerCol = function(colElement, updateItems) {
                    var colContainer = $(colElement);
                    var colIndex = colContainer.index();
                    var col = p.cols[colIndex];
                    if (col.divider) return;
                    col.container = colContainer;
                    col.wrapper = col.container.find('.picker-items-col-wrapper');
                    col.items = col.wrapper.find('.picker-item');

                    var i, j;
                    var wrapperHeight, itemHeight, itemsHeight, minTranslate, maxTranslate;
                    col.replaceValues = function(values, displayValues) {
                        col.destroyEvents();
                        col.values = values;
                        col.displayValues = displayValues;
                        var newItemsHTML = p.columnHTML(col, true);
                        col.wrapper.html(newItemsHTML);
                        col.items = col.wrapper.find('.picker-item');
                        col.calcSize();
                        col.setValue(col.values[0], 0, true);
                        col.initEvents();
                    };
                    col.calcSize = function() {
                        if (p.params.rotateEffect) {
                            col.container.removeClass('picker-items-col-absolute');
                            if (!col.width) col.container.css({
                                width: ''
                            });
                        }
                        var colWidth, colHeight;
                        colWidth = 0;
                        colHeight = col.container[0].offsetHeight;
                        wrapperHeight = col.wrapper[0].offsetHeight;
                        itemHeight = col.items[0] ? col.items[0].offsetHeight : 0;
                        itemsHeight = itemHeight * col.items.length;
                        minTranslate = colHeight / 2 - itemsHeight + itemHeight / 2;
                        maxTranslate = colHeight / 2 - itemHeight / 2;
                        if (col.width) {
                            colWidth = col.width;
                            if (parseInt(colWidth, 10) === colWidth) colWidth = colWidth + 'px';
                            col.container.css({
                                width: colWidth
                            });
                        }
                        if (p.params.rotateEffect) {
                            if (!col.width) {
                                col.items.each(function() {
                                    var item = $(this);
                                    item.css({
                                        width: 'auto'
                                    });
                                    colWidth = Math.max(colWidth, item[0].offsetWidth);
                                    item.css({
                                        width: ''
                                    });
                                });
                                col.container.css({
                                    width: (colWidth + 2) + 'px'
                                });
                            }
                            col.container.addClass('picker-items-col-absolute');
                        }
                    };
                    col.calcSize();

                    col.wrapper.transform('translate3d(0,' + maxTranslate + 'px,0)').transition(0);


                    var activeIndex = 0;
                    var animationFrameId;

                    // Set Value Function
                    col.setValue = function(newValue, transition, valueCallbacks) {
                        if (typeof transition === 'undefined') transition = '';
                        var newActiveIndex = col.wrapper.find('.picker-item[data-picker-value="' + newValue + '"]').index();
                        if (typeof newActiveIndex === 'undefined' || newActiveIndex === -1) {
                            return;
                        }
                        var newTranslate = -newActiveIndex * itemHeight + maxTranslate;
                        // Update wrapper
                        col.wrapper.transition(transition);
                        col.wrapper.transform('translate3d(0,' + (newTranslate) + 'px,0)');

                        // Watch items
                        if (p.params.updateValuesOnMomentum && col.activeIndex && col.activeIndex !== newActiveIndex) {
                            $.cancelAnimationFrame(animationFrameId);
                            col.wrapper.transitionEnd(function() {
                                $.cancelAnimationFrame(animationFrameId);
                            });
                            updateDuringScroll();
                        }

                        // Update items
                        col.updateItems(newActiveIndex, newTranslate, transition, valueCallbacks);
                    };

                    col.updateItems = function(activeIndex, translate, transition, valueCallbacks) {
                        if (typeof translate === 'undefined') {
                            translate = $.getTranslate(col.wrapper[0], 'y');
                        }
                        if (typeof activeIndex === 'undefined') activeIndex = -Math.round((translate - maxTranslate) / itemHeight);



                        if (activeIndex < 0) activeIndex = 0;
                        if (activeIndex >= col.items.length) activeIndex = col.items.length - 1;
                        var previousActiveIndex = col.activeIndex;
                        col.previousActiveIndex = previousActiveIndex;
                        col.activeIndex = activeIndex;
                        //去掉 .picker-after-selected, .picker-before-selected 以提高性能
                        col.wrapper.find('.picker-selected').removeClass('picker-selected');
                        if (p.params.rotateEffect) {
                            col.items.transition(transition);
                        }
                        var selectedItem = col.items.eq(activeIndex).addClass('picker-selected').transform('');

                        if (valueCallbacks || typeof valueCallbacks === 'undefined') {
                            // Update values
                            col.value = selectedItem.attr('data-picker-value');
                            col.displayValue = col.displayValues ? col.displayValues[activeIndex] : col.value;
                            // On change callback
                            if (previousActiveIndex !== activeIndex) {
                                if (col.onChange) {
                                    col.onChange(p, col.value, col.displayValue);
                                }
                                p.updateValue();
                            }
                        }

                        // Set 3D rotate effect
                        if (!p.params.rotateEffect) {
                            return;
                        }
                        var percentage = (translate - (Math.floor((translate - maxTranslate) / itemHeight) * itemHeight + maxTranslate)) / itemHeight;

                        col.items.each(function() {
                            var item = $(this);
                            var itemOffsetTop = item.index() * itemHeight;
                            var translateOffset = maxTranslate - translate;
                            var itemOffset = itemOffsetTop - translateOffset;
                            var percentage = itemOffset / itemHeight;

                            var itemsFit = Math.ceil(col.height / itemHeight / 2) + 1;

                            var angle = (-18 * percentage);
                            if (angle > 180) angle = 180;
                            if (angle < -180) angle = -180;
                            // Far class
                            if (Math.abs(percentage) > itemsFit) item.addClass('picker-item-far');
                            else item.removeClass('picker-item-far');
                            // Set transform
                            item.transform('translate3d(0, ' + (-translate + maxTranslate) + 'px, ' + (originBug ? -110 : 0) + 'px) rotateX(' + angle + 'deg)');
                        });
                    };

                    function updateDuringScroll() {
                        animationFrameId = $.requestAnimationFrame(function() {
                            col.updateItems(undefined, undefined, 0);
                            updateDuringScroll();
                        });
                    }

                    // Update items on init
                    if (updateItems) col.updateItems(0, maxTranslate, 0);

                    var allowItemClick = true;
                    var isTouched, isMoved, touchStartY, touchCurrentY, touchStartTime, touchEndTime, startTranslate, returnTo, currentTranslate, prevTranslate, velocityTranslate, velocityTime;

                    function handleTouchStart(e) {
                        if (isMoved || isTouched) return;
                        e.targetTouches = e.originalEvent.touches;
                        e.preventDefault();
                        isTouched = true;
                        touchStartY = touchCurrentY = e.type === 'touchstart' ? e.targetTouches[0].pageY : e.pageY;
                        touchStartTime = (new Date()).getTime();

                        allowItemClick = true;
                        startTranslate = currentTranslate = $.getTranslate(col.wrapper[0], 'y');
                    }

                    function handleTouchMove(e) {
                        if (!isTouched) return;
                        e.preventDefault();
                        e.targetTouches = e.originalEvent.touches;
                        allowItemClick = false;
                        touchCurrentY = e.type === 'touchmove' ? e.targetTouches[0].pageY : e.pageY;
                        if (!isMoved) {
                            // First move
                            $.cancelAnimationFrame(animationFrameId);
                            isMoved = true;
                            startTranslate = currentTranslate = $.getTranslate(col.wrapper[0], 'y');
                            col.wrapper.transition(0);
                        }
                        e.preventDefault();

                        var diff = touchCurrentY - touchStartY;
                        currentTranslate = startTranslate + diff;
                        returnTo = undefined;

                        // Normalize translate
                        if (currentTranslate < minTranslate) {
                            currentTranslate = minTranslate - Math.pow(minTranslate - currentTranslate, 0.8);
                            returnTo = 'min';
                        }
                        if (currentTranslate > maxTranslate) {
                            currentTranslate = maxTranslate + Math.pow(currentTranslate - maxTranslate, 0.8);
                            returnTo = 'max';
                        }
                        // Transform wrapper
                        col.wrapper.transform('translate3d(0,' + currentTranslate + 'px,0)');

                        // Update items
                        col.updateItems(undefined, currentTranslate, 0, p.params.updateValuesOnTouchmove);

                        // Calc velocity
                        velocityTranslate = currentTranslate - prevTranslate || currentTranslate;
                        velocityTime = (new Date()).getTime();
                        prevTranslate = currentTranslate;
                    }

                    function handleTouchEnd(e) {
                        if (!isTouched || !isMoved) {
                            isTouched = isMoved = false;
                            return;
                        }
                        isTouched = isMoved = false;
                        col.wrapper.transition('');
                        if (returnTo) {
                            if (returnTo === 'min') {
                                col.wrapper.transform('translate3d(0,' + minTranslate + 'px,0)');
                            } else col.wrapper.transform('translate3d(0,' + maxTranslate + 'px,0)');
                        }
                        touchEndTime = new Date().getTime();

                        var velocity, newTranslate, slow = false;
                        if (touchEndTime - touchStartTime > 300) {
                            newTranslate = currentTranslate;
                            slow = true;
                        } else {
                            velocity = Math.abs(velocityTranslate / (touchEndTime - velocityTime));
                            newTranslate = currentTranslate + velocityTranslate * p.params.momentumRatio;
                        }

                        newTranslate = Math.max(Math.min(newTranslate, maxTranslate), minTranslate);

                        // Active Index
                        var activeIndex = -Math.floor((newTranslate - maxTranslate) / itemHeight);


                        activeIndex = activeIndex > col.activeIndex && slow ? col.activeIndex : activeIndex;

                        // Normalize translate
                        if (!p.params.freeMode) newTranslate = -activeIndex * itemHeight + maxTranslate;

                        // Transform wrapper
                        col.wrapper.transform('translate3d(0,' + (parseInt(newTranslate, 10)) + 'px,0)');

                        // Update items
                        col.updateItems(activeIndex, newTranslate, '', true);

                        // 百万任我行扩展功能
                        if (p.params.onTouchEnd) p.params.onTouchEnd(p, activeIndex);

                        // Watch items
                        if (p.params.updateValuesOnMomentum) {
                            updateDuringScroll();
                            col.wrapper.transitionEnd(function() {
                                $.cancelAnimationFrame(animationFrameId);
                            });
                        }

                        // Allow click
                        setTimeout(function() {
                            allowItemClick = true;
                        }, 100);
                    }

                    function handleClick(e) {
                        if (!allowItemClick) return;
                        $.cancelAnimationFrame(animationFrameId);
                        /*jshint validthis:true */
                        var value = $(this).attr('data-picker-value');
                        col.setValue(value);
                    }

                    col.initEvents = function(detach) {
                        var method = detach ? 'off' : 'on';
                        col.container[method]($.touchEvents.start, handleTouchStart);
                        col.container[method]($.touchEvents.move, handleTouchMove);
                        col.container[method]($.touchEvents.end, handleTouchEnd);
                        col.items[method]('click', handleClick);
                    };
                    col.destroyEvents = function() {
                        col.initEvents(true);
                    };

                    col.container[0].f7DestroyPickerCol = function() {
                        col.destroyEvents();
                    };

                    col.initEvents();

                };
                p.destroyPickerCol = function(colContainer) {
                    colContainer = $(colContainer);
                    if ('f7DestroyPickerCol' in colContainer[0]) colContainer[0].f7DestroyPickerCol();
                };
                // Resize cols
                function resizeCols() {
                    if (!p.opened) return;
                    for (var i = 0; i < p.cols.length; i++) {
                        if (!p.cols[i].divider) {
                            p.cols[i].calcSize();
                            p.cols[i].setValue(p.cols[i].value, 0, false);
                        }
                    }
                }
                $(window).on('resize', resizeCols);

                // HTML Layout
                p.columnHTML = function(col, onlyItems) {
                    var columnItemsHTML = '';
                    var columnHTML = '';
                    if (col.divider) {
                        columnHTML += '<div class="picker-items-col picker-items-col-divider ' + (col.textAlign ? 'picker-items-col-' + col.textAlign : '') + ' ' + (col.cssClass || '') + '">' + col.content + '</div>';
                    } else {
                        for (var j = 0; j < col.values.length; j++) {
                            columnItemsHTML += '<div class="picker-item" data-picker-value="' + col.values[j] + '">' + (col.displayValues ? col.displayValues[j] : col.values[j]) + '</div>';
                        }
                        columnHTML += '<div class="picker-items-col ' + (col.textAlign ? 'picker-items-col-' + col.textAlign : '') + ' ' + (col.cssClass || '') + '"><div class="picker-items-col-wrapper">' + columnItemsHTML + '</div></div>';
                    }
                    return onlyItems ? columnItemsHTML : columnHTML;
                };
                p.layout = function() {
                    var pickerHTML = '';
                    var pickerClass = '';
                    var i;
                    p.cols = [];
                    var colsHTML = '';
                    for (i = 0; i < p.params.cols.length; i++) {
                        var col = p.params.cols[i];
                        colsHTML += p.columnHTML(p.params.cols[i]);
                        p.cols.push(col);
                    }
                    pickerClass = 'picker-modal picker-columns ' + (p.params.cssClass || '') + (p.params.rotateEffect ? ' picker-3d' : '');
                    pickerHTML =
                        '<div class="' + (pickerClass) + '">' +
                        (p.params.toolbar ? p.params.toolbarTemplate.replace(/\{\{closeText\}\}/g, p.params.toolbarCloseText) : '') +
                        '<div class="picker-modal-inner picker-items">' +
                        colsHTML +
                        '<div class="picker-center-highlight"></div>' +
                        '</div>' +
                        '</div>';

                    p.pickerHTML = pickerHTML;
                };

                // Input Events
                function openOnInput(e) {
                    e.preventDefault();
                    if (p.opened) return;
                    p.open();
                    if (p.params.scrollToInput && !isPopover()) {
                        var pageContent = p.input.parents('.page-content');
                        if (pageContent.length === 0) return;

                        var paddingTop = parseInt(pageContent.css('padding-top'), 10),
                            paddingBottom = parseInt(pageContent.css('padding-bottom'), 10),
                            pageHeight = pageContent[0].offsetHeight - paddingTop - p.container.height(),
                            pageScrollHeight = pageContent[0].scrollHeight - paddingTop - p.container.height(),
                            newPaddingBottom;
                        var inputTop = p.input.offset().top - paddingTop + p.input[0].offsetHeight;
                        if (inputTop > pageHeight) {
                            var scrollTop = pageContent.scrollTop() + inputTop - pageHeight;
                            if (scrollTop + pageHeight > pageScrollHeight) {
                                newPaddingBottom = scrollTop + pageHeight - pageScrollHeight + paddingBottom;
                                if (pageHeight === pageScrollHeight) {
                                    newPaddingBottom = p.container.height();
                                }
                                pageContent.css({
                                    'padding-bottom': (newPaddingBottom) + 'px'
                                });
                            }
                            pageContent.scrollTop(scrollTop, 300);
                        }
                    }
                }

                function closeOnHTMLClick(e) {
                    if (inPopover()) return;
                    var isNotInPop = $(e.target).parents('.picker-container').length === 0;
                    if (p.input && p.input.length > 0) {
                        if (isNotInPop) {
                            return;
                        } else {
                            if (e.target !== p.input[0] && $(e.target).parents('.picker-modal').length === 0 && !p.params.needMask) p.close();
                        }
                    } else {
                        if ($(e.target).parents('.picker-modal').length === 0) p.close();
                    }
                }

                if (p.params.input) {
                    p.input = $(p.params.input);
                    if (p.input.length > 0) {
                        if (p.params.inputReadOnly) p.input.prop('readOnly', true);
                        if (!p.inline) {
                            p.input.on('click', openOnInput);
                        }
                        if (p.params.inputReadOnly) {
                            p.input.on('focus mousedown', function(e) {
                                e.preventDefault();
                            });
                        }
                    }

                }

                if (!p.inline) $('html').on('click', closeOnHTMLClick);

                // Open
                function onPickerClose() {
                    p.opened = false;
                    if (p.input && p.input.length > 0) p.input.parents('.page-content').css({
                        'padding-bottom': ''
                    });
                    if (p.params.onClose) p.params.onClose(p);

                    // Destroy events
                    p.container.find('.picker-items-col').each(function() {
                        p.destroyPickerCol(this);
                    });
                }

                p.opened = false;
                p.open = function() {
                    var toPopover = isPopover();

                    if (!p.opened) {

                        // Layout
                        p.layout();

                        // Append
                        if (toPopover) {
                            p.pickerHTML = '<div class="popover popover-picker-columns"><div class="popover-inner">' + p.pickerHTML + '</div></div>';
                            p.popover = $.popover(p.pickerHTML, p.params.input, true);
                            p.container = $(p.popover).find('.picker-modal');
                            $(p.popover).on('close', function() {
                                onPickerClose();
                            });
                        } else if (p.inline) {
                            p.container = $(p.pickerHTML);
                            p.container.addClass('picker-modal-inline');
                            $(p.params.container).append(p.container);
                        } else {
                            p.container = $($.pickerModal(p.pickerHTML));
                            $(p.container)
                                .on('close', function() {
                                    onPickerClose();
                                });
                        }

                        // Store picker instance
                        p.container[0].f7Picker = p;

                        // Init Events
                        p.container.find('.picker-items-col').each(function() {
                            var updateItems = true;
                            if ((!p.initialized && p.params.value) || (p.initialized && p.value)) updateItems = false;
                            p.initPickerCol(this, updateItems);
                        });

                        // Set value
                        if (!p.initialized) {
                            if (p.params.value) {
                                p.setValue(p.params.value, 0);
                            }
                        } else {
                            if (p.value) p.setValue(p.value, 0);
                        }
                    }

                    // Set flag
                    p.opened = true;
                    p.initialized = true;

                    if (p.params.onOpen) p.params.onOpen(p);
                };

                // Close
                p.close = function() {
                    if (!p.opened || p.inline) return;
                    if (inPopover()) {
                        $.closeModal(p.popover);
                        return;
                    } else {
                        $.closeModal(p.container);
                        return;
                    }
                };

                // Destroy
                p.destroy = function() {
                    p.close();
                    if (p.params.input && p.input.length > 0) {
                        p.input.off('click focus', openOnInput);
                    }
                };

                if (p.inline) {
                    p.open();
                }

                return p;
            };
            $.fn.picker = function(params) {
                var args = arguments;
                return this.each(function() {
                    if (!this) return;
                    var $this = $(this);

                    var picker = $this.data("picker");
                    if (!picker) {
                        var p = $.extend({
                            input: this
                        }, params);
                        picker = new Picker(p);
                        $this.data("picker", picker);
                    }
                    if (typeof params === typeof "a") {
                        picker[params].apply(picker, Array.prototype.slice.call(args, 1));
                    }
                });
            };
        }
    });
    return LayoutView;

})