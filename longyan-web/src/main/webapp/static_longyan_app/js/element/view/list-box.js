/**
 * 列表
 **/
define('js/element/view/list-box', [
        'text!js/element/template/list-box.tpl',
        'text!js/element/template/slide-bar-left.tpl'
    ],
    function (ListTpl, SlideBarLeftTpl) {
        var loadingEnable = true; //分页加载锁,默认可加载
        var offset;

        var slideBarLeft = function ($elem, events) {
            var initX;
            var moveX;
            var X = 0;
            var objX = 0;
            //var offset = 280;
            $elem.each(function (idx, item) {

                var $curItem = $(item).addClass('slide-left-item').css('transform', 'translateX(0px)').append(tpl(SlideBarLeftTpl));
                if (events) {
                    if (events.slideEdit) {
                        $curItem.find('.edit').on('click', function (e) {
                            e.stopPropagation();
                            events.slideEdit(this);
                        });
                    }
                    if (events.slideDelete) {
                        $curItem.find('.delete').on('click', function (e) {
                            e.stopPropagation();
                            events.slideDelete(this);
                        });
                    }
                    if (events.clickEdit) {
                        $(item).on('click', function (e) {
                            e.stopPropagation();
                            events.clickEdit(this);
                        });
                    }
                }
            });
            var onTouchstart = function (event) {
                //event.preventDefault();

                var obj = $(event.target).parents('.slide-left-item')[0];
                if (obj && obj.className && obj.className.indexOf("slide-left-item") != -1) {
                    initX = event.targetTouches[0].pageX;
                    objX = (obj.style.WebkitTransform.replace(/translateX\(/g, "").replace(/px\)/g, "")) * 1;
                }
            };
            var onTouchmove = function (event) {
                var obj = $(event.target).parents('.slide-left-item')[0];
                moveX = event.targetTouches[0].pageX;
                X = moveX - initX;
                if (obj && objX == 0) {
                    if (obj.className && obj.className.indexOf("slide-left-item") != -1) {
                        if (X > 0) {
                            obj.style.WebkitTransform = "translateX(" + 0 + "px)";
                        }
                        else if (X < 0) {
                            var l = Math.abs(X);
                            obj.style.WebkitTransform = "translateX(" + -l + "px)";
                            if (l > offset) {
                                l = offset;
                                obj.style.WebkitTransform = "translateX(" + -l + "px)";
                            }
                        }
                    }
                } else {
                    if (obj && obj.className && obj.className.indexOf("slide-left-item") != -1) {
                        if (X > 0) {
                            var r = -offset + Math.abs(X);
                            obj.style.WebkitTransform = "translateX(" + r + "px)";
                            if (r > 0) {
                                r = 0;
                                obj.style.WebkitTransform = "translateX(" + r + "px)";
                            }
                        } else {     //向左滑动
                            obj.style.WebkitTransform = "translateX(" + -offset + "px)";
                        }
                    }
                }
            }
            var onTouchend = function (event) {
                //event.preventDefault();
                var obj = $(event.target).parents('.slide-left-item')[0];
                if (obj && obj.className && obj.className.indexOf("slide-left-item") != -1) {
                    objX = (obj.style.WebkitTransform.replace(/translateX\(/g, "").replace(/px\)/g, "")) * 1;
                    if (objX > -40) {
                        obj.style.WebkitTransform = "translateX(" + 0 + "px)";
                    } else {
                        obj.style.WebkitTransform = "translateX(" + -offset + "px)";
                    }
                }
            }
            window.removeEventListener('touchstart', onTouchstart);
            window.removeEventListener('touchmove', onTouchmove);
            window.removeEventListener('touchend', onTouchend);
            window.addEventListener('touchstart', onTouchstart, false);
            window.addEventListener('touchmove', onTouchmove, false);
            window.addEventListener('touchend', onTouchend, false);
            return {
                $elem: $elem,
                clear: function () {
                    window.removeEventListener('touchstart', onTouchstart);
                    window.removeEventListener('touchmove', onTouchmove);
                    window.removeEventListener('touchend', onTouchend);
                }
            }
        };
        var LayoutView = Backbone.View.extend({
            events: {},
            //
            initialize: function (options, config, events) {
                var t = this;
                t.config = config || {};
                t.events = events || {
                        loadData: function () {

                        },
                        appendItem: function (data) {
                            // console.log(data);
                        },
                        slideEdit: function (data, index) {
                            // console.log(data);
                            // console.log(index);
                        },
                        slideDelete: function (data, index) {
                            // console.log(data);
                            // console.log(index);
                        },
                        clickEdit: function (data, index) {
                            // console.log(data);
                            // console.log(index);
                        }
                    };
                t.config.page = t.config.page || 1;
                t.render();
                // t.events.loadData(t.config.page, function(data, currentPage, totalPages) {
                //     console.log(data);
                //     console.log(currentPage);
                //     console.log(totalPages);
                //     t.setCurrentPage(currentPage);
                //     t.setTotalPage(totalPages);
                //     var item_templates = '';
                //     if (t.config.page > 1) {
                //         item_templates = t.$el.find('#scroller').html();
                //     }
                //     if (data && data.length > 0) {
                //         $.each(data, function(i, item) {
                //             var item_template = t.events.appendItem(item);
                //             // t.$el.append(item_template);
                //             item_templates = item_templates + item_template;
                //         });
                //     }
                //     t.$el.html(item_templates);
                //     loadingEnable = true;
                // });
                t.loadData();
                // 初始化下拉刷新
                if (t.config.scroll) {
                    t._initScroll();
                }

                if (t.config.hidenslideBar) {
                    offset = 0;
                } else {
                    offset = 280;
                }

            },
            render: function () {
                var t = this;
                t.$el.append(tpl(ListTpl, {
                    data: t.config
                }));
            },
            loadData: function () {
                var t = this;
                if (loadingEnable) {
                    //放置加载中标记
                    $('#scroller').find('.page-end').remove();
                    t.$el.find('#scroller .scrollBox').append('<div class="page-end">加载中...</div>');
                }
                t.events.loadData(t.config.page, function (data, currentPage, totalPages, signal) {
                    t.setCurrentPage(currentPage);
                    t.setTotalPage(totalPages);
                    var item_templates = '';
                    if (t.config.page > 1) {
                        item_templates = t.$el.find('#scroller .scrollBox').html();
                    }
                    if (data && data.length > 0) {
                        $.each(data, function (i, item) {
                            var item_template = t.events.appendItem(item);
                            // t.$el.append(item_template);
                            item_templates += item_template;
                        });
                    }
                    var $items = $(item_templates);

                    signal = typeof(signal) == 'undefined' ? 1 : 0;
                    if (signal && $items.length){
                            t.slideBarLeft = slideBarLeft($items, t.events);
                    }
                    t.$el.find('#scroller .scrollBox').html($items);
                    loadingEnable = true;

                    if (loadingEnable) {
                        $('#scroller').find('.page-end').remove();
                        var _tmp_page = t.config.page + 1;
                        if (_tmp_page <= t.config.totalPage) {
                            //还有下一页可以加载
                            //console.log('还有下一页可以加载');
                            t.$el.find('#scroller .scrollBox').append('<div class="page-end">下拉加载更多</div>');
                        } else {
                            //到头了
                            var windowHeight = $(window).height(),
                                scrollHeight = $("#scroller .scrollBox").height(),
                                scrollOffset = $("#scroller .scrollBox").offset().top;
                            if (scrollHeight > (windowHeight - scrollOffset)) {
                                //console.log('到头了');
                                t.$el.find('#scroller .scrollBox').append('<div class="page-end">已经到底啦</div>');
                            }
                        }
                    }

                });
            },
            removeEvent: function () {
                if (this.slideBarLeft)
                    this.slideBarLeft.clear();
            },
            clearItem: function () {
                var t = this;
                t.$el.find('#scroller .scrollBox').html('');
            },
            setCurrentPage: function (page) {
                var t = this;
                t.config.page = page;
            },
            setTotalPage: function (page) {
                var t = this;
                t.config.totalPage = page;
            },
            _initScroll: function () {
                var t = this;
                $(window).scroll(function () {
                    var x1 = $(window).scrollTop();
                    var x2 = $('.scrollBox').height() - (window.screen.height * 2 - $('#header-container').height()); //因为屏幕是缩放了0.5的，屏幕高度应该是原长度的两倍
                    if (x1 > x2 && loadingEnable) {
                        // loadingEnable = false;
                        t._pullUpAction();
                        //console.log('可以翻页了');
                    } else {
                        // console.log('x1', x1);
                        //console.log('x2', x2);
                    }
                });
            },
            _pullUpAction: function () {
                var t = this;
                //console.log('加载下一页');
                t.config.page = t.config.page + 1;
                if (t.config.page <= t.config.totalPage) {
                    t.loadData();
                } else {
                    //console.log('没有什么可以加载的了');

                }
            }
        });
        return LayoutView;
    });