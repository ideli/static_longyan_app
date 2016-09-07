define('js/util/string', ['lib/uuid-js/lib/uuid'], function(UUID) {
    $nvwa.string = {
        //随即计算一个序列号，保证本地唯一
        randomSN: function() {
            //TODO
            // var uuid4 = UUID.create();
            return UUIDjs.create().toString();
        },
        //get url query string param
        getPar: function(par) {
            //获取当前URL
            var local_url = document.location.href;
            //获取要取得的get参数位置
            var get = local_url.indexOf(par + "=");
            if (get == -1) {
                return false;
            }
            //截取字符串
            var get_par = local_url.slice(par.length + get + 1);
            //判断截取后的字符串是否还有其他get参数
            var nextPar = get_par.indexOf("&");
            if (nextPar != -1) {
                get_par = get_par.slice(0, nextPar);
            }
            return get_par;
        },
        //string to int
        strToInt: function(str) {
            if (str && typeof(str) == 'string' && str.length > 0) {
                //return number value
                return parseInt(str);
            } else if (typeof(str) == 'number') {
                return str;
            } else {
                //no number string
                return null;
            }
        },
        //object to json string
        objectToJsonString: function(object) {
            if (object || typeof(object) == 'boolean') {
                return JSON.stringify(object);
            }
            return null;
        },
        //json string to object
        jsonStringToObject: function(str) {
            str = str || '{}';
            return eval("(" + str + ")");
        },
        //验证OI字符串
        oiStringVerification: function(str) {
            return this.noChineseVerification(str);
        },
        noChineseVerification: function(str) {
            if (str && str.length > 0) {
                if (str.match(/^([a-zA-Z0-9_])*$/)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //no text input
                return true;
            }
        },
        //验证字符串
        isVerify: function(str) {
            if (str && typeof(str) == 'string' && str.length > 0) {
                return true;
            } else {
                return false;
            }
        },
        isEmail: function(str) {
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            return reg.test(str);
        },
        isUrl: function(str) {
            var Expression = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
            var objExp = new RegExp(Expression);
            return objExp.test(str);
        },
        //校验手机号
        isPhone: function(phone) {
            var t = this;
            var myreg = /^[1][34578][0-9]{9}$/;
            return myreg.test(phone);
        },
        // 姓名格式校验  这个接口与其他接口  返回结果 相反   false 表示通过     有返回值 表示不通过
        isRealName: function(realname) {
            var t = this;
            var is_valid = false;
            var errorMsg;
            // var chineseRegex = /^[\u4e00-\u9fa5]+|\·$/; // 包含汉字或中间点·
            var chineseRegex = /^[\u4e00-\u9fa5·]{2,30}$/; // 包含汉字或中间点·
            // var specialChars = ",./;'<>?:\"[]\\{}|~!@#$%^&*()-=+`_1234567890";

            if (realname.length < 2) {
                errorMsg = '姓名必须为2~30个字';
                is_valid = false;
            } else if (realname.length > 30) {
                errorMsg = '姓名必须为2~30个字';
                is_valid = false;
            } else if (realname.indexOf(' ') >= 0) {
                errorMsg = '姓名不能包含空格';
                is_valid = false;
            } else if (!chineseRegex.test(realname)) {
                errorMsg = '姓名为汉字或·';
                is_valid = false;
            } else if (realname.indexOf('·') > 0) {
                // errorMsg = '姓名为·';
                is_valid = true;
            } else {
                is_valid = true;
            }
            return errorMsg;
        },
        isNumber: function(str) {
            return !isNaN(str);
        },
        //验证name字符串
        //e=jquery event
        //MessageView=MessageView
        nameTextVerification: function(e, MessageView) {
            var code = e.keyCode;
            var target = $(e.target);
            if (target) {
                var value = target.val();
                if (value) {
                    if (value.length > 0 && value.length < 16) {
                        //value length must >0 and <16
                        target.removeClass("alert-danger");
                        return true;
                    } else {
                        if (code != 8) { //不检测退格键
                            //error
                            new MessageView({
                                type: 'error',
                                msg: '请控制输入的内容在1到16个字符以内!',
                                timeout: 1500
                            });
                            target.addClass("alert-danger");
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        },
        aliasTextVerification: function(e, MessageView) {
            var t = this;
            var code = e.keyCode;
            var target = $(e.target);
            if (target) {
                var value = target.val();
                if (value) {
                    if (t.noChineseVerification(value)) {
                        if (value.length > 0 && value.length < 64) {
                            //value length must >0 and <16
                            target.removeClass("alert-danger");
                            return true;
                        } else {
                            if (code != 8) { //不检测退格键
                                //error
                                new MessageView({
                                    type: 'error',
                                    msg: '请控制输入的内容在1到64个字符以内!',
                                    timeout: 1500
                                });
                                target.addClass("alert-danger");
                                return false;
                            }
                        }
                    } else {
                        new MessageView({
                            type: 'error',
                            msg: '请不要输入中文或特殊符号!',
                            timeout: 1500
                        });
                    }
                } else {
                    return false;
                }
            }
            return true;
        },
        generateMixed: function(n) {
            var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
            var res = "";
            for (var i = 0; i < n; i++) {
                var id = Math.ceil(Math.random() * 35);
                res += chars[id];
            }
            return res;
        },
        dateDiff: function(sDate1, sDate2) { //sDate1和sDate2是字符串 yyyy-MM-dd格式 
            var aDate, oDate1, oDate2, iDays, ihours, iminutes, iseconds;
            aDate = sDate1.split("-");
            oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); //转换为MM-dd-yyyy格式 
            aDate = sDate2.split("-");
            oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
            var timeSpan = {};
            var TotalMilliseconds = Math.abs(oDate1 - oDate2); //相差的毫秒数
            timeSpan.Days = parseInt(TotalMilliseconds / 1000 / 60 / 60 / 24);
            timeSpan.TotalHours = parseInt(TotalMilliseconds / 1000 / 60 / 60);
            timeSpan.Hours = timeSpan.TotalHours % 24;
            timeSpan.TotalMinutes = parseInt(TotalMilliseconds / 1000 / 60);
            timeSpan.Minutes = timeSpan.TotalMinutes % 60;
            timeSpan.TotalSeconds = parseInt(TotalMilliseconds / 1000);
            timeSpan.Seconds = timeSpan.TotalSeconds % 60;
            timeSpan.TotalMilliseconds = TotalMilliseconds;
            timeSpan.Milliseconds = TotalMilliseconds % 1000;
            return timeSpan;
        }
    }

    Date.prototype.format = function(fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    return $nvwa.string;
});