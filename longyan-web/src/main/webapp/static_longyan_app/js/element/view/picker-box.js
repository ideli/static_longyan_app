/**
 * picker 组件
 **/
define('js/element/view/picker-box', [
        'text!js/element/template/picker-box.tpl'
    ],
    function(SimpleTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.sn = $nvwa.string.randomSN();
                t.config = $.extend({
                    columns: 2, //表示picker有多少列
                    data: [
                        [{
                            text: 'aaa',
                            value: 'valueA'
                        }, {
                            text: 'aaa2',
                            value: 'valueA2'
                        }],
                        [{
                            text: 'bbb',
                            value: 'valueB'
                        }]
                    ]
                }, t.config, config);

                //是否已经加载picker插件了
                t.isPickerReady = false;
                t.render();
            },
            render: function() {
                var t = this;
                t.config['sn'] = t.sn;
                t.$el.append(tpl(SimpleTpl, {
                    data: t.config
                }));

                require(["js/components/picker"], function(Picker) {
                    t.isPickerReady = true;
                    new Picker();
                    t.initPicker();
                });
            },
            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    if (value) {
                        input.attr('readonly', 'readonly');
                    } else {
                        input.removeAttr('readonly');
                    }

                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var codes = container.attr('data-selected') || t.select_ids;
                    var names = container.attr('data-selected-name') || t.select_name;

                    if (codes && names) {
                        var codeArray = [];
                        var nameArray = [];
                        if (t.config.columns > 1) {
                            codeArray = codes.split(' ');
                            nameArray = names.split(' ');
                        } else {
                            codeArray.push(codes);
                            nameArray.push(names);
                        }

                        var num = codeArray.length;
                        var result = {};
                        for (var i = 0; i < num; i++) {
                            if (i == 0) {
                                result.col1Key = codeArray[0];
                                result.col1Value = nameArray[0];
                            } else if (i == 1) {
                                result.col2Key = codeArray[1];
                                result.col2Value = nameArray[1];
                            } else if (i == 2) {
                                result.col3Key = codeArray[2];
                                result.col3Value = nameArray[2];
                            } else if (i == 3) {
                                result.col4Key = codeArray[3];
                                result.col4Value = nameArray[3];
                            }
                        }
                        return result
                    }

                }
                // simple
                // {
                //     "col1Value": "上海"
                //     "col1Key": "310000",
                //     "col2Value": "上海市"
                //     "col2Key": "310100",
                //     "col3Value": "黄浦区"
                //     "col3Key": "310101",
                // }
            },
            clearValue: function() {
                var t = this;
                t.setValue('');
                t.$el.find('.' + t.config.fieldName).removeAttr('data-selected');
                t.$el.find('.' + t.config.fieldName).removeAttr('data-selected-name');
                if (t.col1Key) {
                    t.col1Key = '';
                }
                if (t.col2Key) {
                    t.col2Key = '';
                }
                if (t.col3Key) {
                    t.col3Key = '';
                }
                if (t.col4Key) {
                    t.col4Key = '';
                }

            },
            setValue: function(col1Key, col2Key, col3Key, col4Key) {
                var t = this;

                t.count = 0;
                var time = window.setInterval(function() {
                    t.count++;
                    if (t.count >= 100) {
                        window.clearInterval(time);
                    }
                    if (t.isPickerReady) {
                        t.col1Key = col1Key;
                        t.col2Key = col2Key;
                        t.col3Key = col3Key;
                        t.col4Key = col4Key;
                        t.initPicker();


                        if (t.col1Keys) {
                            window.clearInterval(time);
                            var container = t.$el.find('.' + t.config.fieldName);
                            if (container) {
                                var input = container.find('input');
                                var val = '';
                                var keyVal = col1Key;
                                if (col2Key) {
                                    keyVal += ' ' + col2Key;
                                }
                                if (col3Key) {
                                    keyVal += ' ' + col3Key;
                                }
                                if (col4Key) {
                                    keyVal += ' ' + col4Key;
                                }
                                for (var i = 0; i < t.col1Keys.length; i++) {
                                    if (col1Key == t.col1Keys[i]) {
                                        val += t.col1Values[i];
                                        break;
                                    }
                                }
                                if (t.col2Keys) {
                                    for (var i = 0; i < t.col2Keys.length; i++) {
                                        if (col2Key == t.col2Keys[i]) {
                                            val += ' ' + t.col2Values[i];
                                            break;
                                        }
                                    }
                                }
                                if (t.col3Keys) {
                                    for (var i = 0; i < t.col3Keys.length; i++) {
                                        if (col3Key == t.col3Keys[i]) {
                                            val += ' ' + t.col3Values[i];
                                            break;
                                        }
                                    }
                                }
                                if (t.col4Keys) {
                                    for (var i = 0; i < t.col4Keys.length; i++) {
                                        if (col4Key == t.col4Keys[i]) {
                                            val += ' ' + t.col4Values[i];
                                            break;
                                        }
                                    }
                                }
                                input.val(val);
                                container.data('selected', keyVal);
                                container.data('selected-name', val);
                                t.select_ids = keyVal;
                                t.select_name = val;
                            }
                        }
                    }
                }, 50);

            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            },
            initPickerParam: function() {
                var t = this;
                //最多支持4列
                t.col1Keys = [];
                t.col1Values = [];
                t.col2Keys = [];
                t.col2Values = [];
                t.col3Keys = [];
                t.col3Values = [];
                t.col4Keys = [];
                t.col4Values = [];

                t.col = [];

                if (t.config.columns != t.config.data.length) {
                    //参数错误
                    return false;
                }
                //最多四列
                for (var i = 0; i < t.config.data.length; i++) {
                    var colData = t.config.data[i];
                    for (var j = 0; j < colData.length; j++) {
                        if (i == 0) {
                            t.col1Keys.push(colData[j].value);
                            t.col1Values.push(colData[j].text);
                        } else if (i == 1) {
                            t.col2Keys.push(colData[j].value);
                            t.col2Values.push(colData[j].text);
                        } else if (i == 2) {
                            t.col3Keys.push(colData[j].value);
                            t.col3Values.push(colData[j].text);
                        } else if (i == 3) {
                            t.col4Keys.push(colData[j].value);
                            t.col4Values.push(colData[j].text);
                        }
                    }

                    if (i == 0) {
                        t.col.push({
                            textAlign: 'center',
                            values: t.col1Keys,
                            displayValues: t.col1Values
                        });
                    } else if (i == 1) {
                        t.col.push({
                            textAlign: 'center',
                            values: t.col2Keys,
                            displayValues: t.col2Values
                        });
                    } else if (i == 2) {
                        t.col.push({
                            textAlign: 'center',
                            values: t.col3Keys,
                            displayValues: t.col3Values
                        });
                    } else if (i == 3) {
                        t.col.push({
                            textAlign: 'center',
                            values: t.col4Keys,
                            displayValues: t.col4Values
                        });
                    }
                }

            },
            initPicker: function() {
                var t = this;

                t.initPickerParam();
                var _width = '30%';
                if (t.config.col_width) {
                    _width = t.config.col_width;
                }

                $('#box-picker' + t.sn).picker({
                    toolbarTemplate: '<div class="bar bar-nav box-picker">\
                      <button class="button button-link pull-left  cancel-picker">取消</button>\
                      <button class="button button-link pull-right close-picker">确定</button>\
                      <h1 class="title">' + (t.config.text || '选择器') + '</h1>\
                      </div>',
                    cols: t.col,
                    // needMask: true,
                    onOpen: function(p) {
                        var provinceSelected, citySelected, countrySelected;

                        $('.picker-items-col-left').css('width', _width);
                        $('.picker-items-col-center').css('width', _width);
                        $('.picker-items-col-right').css('width', _width);
                        // $('.picker-container .picker-modal .bar .title').css({
                        //     'line-height': '80px',
                        //     'font-size': '28px'
                        // });

                        //set初始化代码
                        if (t.col1Key) {
                            p.cols[0].setValue(t.col1Key);
                        }
                        if (t.col2Key) {
                            p.cols[1].setValue(t.col2Key);
                        }
                        if (t.col3Key) {
                            p.cols[2].setValue(t.col3Key);
                        }
                        if (t.col4Key) {
                            p.cols[3].setValue(t.col4Key);
                        }

                        $('input').blur();

                    },
                    onClose: function(p) {

                    }
                });
                $(document).on('click', '.box-picker .close-picker', function(e) {
                    var target = $(e.currentTarget);
                    if (target.hasClass("close-picker")) {
                        //确认选择逻辑
                        var selectedData = $('#box-picker' + t.sn).attr('data-selected');
                        var selectedDataName = $('#box-picker' + t.sn).attr('data-selected-name'); //.replace(/\s+/g, "")
                        if (selectedDataName) {
                            $('#box-picker' + t.sn + ' input').val(selectedDataName);
                        }

                    }
                    $.closeModal(".picker-modal.modal-in");
                });
                $(document).on('click', '.box-picker .cancel-picker', function(e) {
                    $.closeModal(".picker-modal.modal-in");
                });
            },
        });
        return LayoutView;
    });