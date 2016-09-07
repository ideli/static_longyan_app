define("js/components/checkbox",function(){
    var box = Backbone.View.extend({
        /*
        * opts
        *   state 可定义初始化状态
        *   change 可定义点击回调状态
        *   disable 可定义初始化状态
        */
        initialize:function(opts){
            var t = this;
            this.init();
            this.setParams(opts);

            if(opts.state){
                this.state = false;
                t.stateChange();
            }

            t.$el.bind('click',function(){
                if(!t.disable){
                    t.stateChange();
                    t.change && t.change();
                }
            })
        },
        init:function(){
             var t = this;
            t.$el.html('');
            t.state = false;
            if(!t.$el.hasClass('check_info_box')){t.$el.addClass('check_info_box')}
        },
        setParams:function(opts){
            var t = this;
            _.each(opts,function(value,key){
                t[key] = value;
            })
        },
        setDisable:function(disable){
            this.disable = !!disable
        },
        getState:function(){
            return !!this.state;
        },
        stateChange:function(){
            var t = this;
            t.state = !t.state;
            t.state?t.$el.addClass('check_info_box_choose'):t.$el.removeClass('check_info_box_choose');
        }
    })
    return box;
})