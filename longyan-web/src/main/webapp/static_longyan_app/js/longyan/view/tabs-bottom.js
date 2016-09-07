define('js/longyan/view/tabs-bottom', [
    'js/element/view/tool-bar-box'
], function (ToolBarBox) {
    var LayoutView = Backbone.View.extend({
        //
        initialize: function(options, config) {
            var t = this;
            t.config = config || {
                selectedIndex: 0
            };
            t.render();
        },
        render: function () {
            //实例化一个底部工具栏
            var tabsItems = [
                {
                    text: '首页',//元素上的文字
                    icon_class: 'iconfont icon-longyanhome',//元素上的图标
                    width: '25%',//元素所占的宽度
                    onClick: function (e) {
                        router.navigate('index', {
                            trigger: true
                        });
                    }
                }, {
                    text: '报表',
                    icon_class: 'iconfont icon-longyanforms',
                    width: '25%',
                    onClick: function (e) {
                        router.navigate('report_corporation', {
                            trigger: true
                        });
                    }
                }, {
                    text: '小区',
                    icon_class: 'iconfont icon-longyanvillage',
                    width: '25%',
                    onClick: function (e) {
                        router.navigate('community_list', {
                            trigger: true
                        });
                    }
                }, {
                    text: '住宅',
                    icon_class: 'iconfont icon-longyanhouse',
                    width: '25%',
                    onClick: function (e) {
                        router.navigate('member_list', {
                            trigger: true
                        });
                    }
                }
            ];
            var tool_bar = new ToolBarBox({
                    el: this.$el,//组件注入的div或dom节点
                    fieldName: 'tabs'
                }, {
                    fieldName: 'tool_bar',//组件对应的字段名称(这个定义要求一个view上是全局唯一)
                    item: tabsItems
                }, {
                    Click: function (e, index) {
                        //点击按钮触发的事件
                        //e:jquery事件对象
                        //index:触发点击的index,左边第一个元素从0开始
                        tabsItems[index].onClick(e);
                    }
                }
            );
            //显示工具栏
            tool_bar.show(true);
            //隐藏工具栏
            //tool_bar.show(false);
            //设置第0个元素为选择状态
            tool_bar.setIndex(this.config.selectedIndex);
        }
    });
    return LayoutView;
});
