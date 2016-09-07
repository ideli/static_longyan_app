define('js/components/finace_utility', function(require) {
    var util = {
        setPageNoData: function(dataObj, containerClass) {
            var txt = "没有符合条件的记录";
            var picFont = '&#xe63d;';
            var containClass = containerClass || "container"
            var souceObj = {
                txt: "没有符合条件的记录",
                picFont: picFont
            };
            if (dataObj) {
                souceObj = dataObj;
            }
            var temp = '<section class="nodata nodataNew" id="js_emptyBg"> <div class="cont"> <i class="lufaxfont noDataPic"><%=picFont%></i>  <div class="txt"><%=txt%></div> </div> </section>',
                data = _.template(temp)(souceObj);
            $("." + containClass).html(data);
            $(document.body).addClass("nodataPic");
        },
        clearBodyClass: function(dataObj, containerClass) {
            $(document.body).removeClass("nodataPic");
        },
        toThousands: function(numValue) {
            if (!numValue || !(Math.abs(numValue) >= 0)) {
                return numValue || "-";
            }
            var num = (numValue || 0).toString(),
                result = '',
                temp = "";
            isPositive = parseFloat(num) >= 0 ? true : false;
            num = (num || 0).toString();
            num = Math.abs(num) >= 0 ? num.replace("-", "") : num;
            if (num.indexOf('.') > -1) {
                temp = num.split(".")[1];
                num = num.split(".")[0];
            }
            while (num.length > 3) {
                result = ',' + num.slice(-3) + result;
                num = num.slice(0, num.length - 3);
            }
            if (num) {
                result = num + result;
            }
            if (temp) {
                result = result + "." + temp;
            }
            return isPositive ? result : "-" + result;
        },
        formatCurrency:function(num,noPoint){
            sign = (num == ( num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if(cents < 10)
                cents = "0" + cents;
            for(var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                num = num.substring(0,num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
            if(noPoint){
                return (((sign)?'':'-') + num);
            }else{
                if(cents == "00"){
                    return (((sign)?'':'-') + num);
                }else{
                    return (((sign)?'':'-') + num + '.' + cents);
                }
            }
         },
         changeNumFormat:function(num) {
            // var t=this;
            if (typeof num == 'string') {
                num = parseFloat(num);
            }
            num = Number(num) || 0;
            // 整数的处理
            if (parseInt(num) != parseFloat(num)) { // 小数
                return util.formatCurrency(num.toFixed(2));
            } else { // 整数
                return util.formatCurrency(parseInt(num));
            }
        }
    };
    
    var output = {
        util: util
    };
    return output;
});