/**
 * mock data for m-web, except it in build process.
 * author: fuyun
 */
define('js/api/contactBook_mock', [], function () {
    return {
        '/longyan/contact/deptList': function (url, opts) {
            return {
                "ok": true,
                "message": "查询成功",
                "code": 200,
                "dataMap": {
                    "departments": [{
                        "name": "设计院",
                        "hasChild": true,
                        "number": 24,
                        "departmentCode": "60000001"
                    }, {"name": "集团总部BU", "hasChild": true, "number": 0, "departmentCode": "10000001"}, {
                        "name": "家居BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "20000001"
                    }, {
                        "name": "建设事业中心",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "30000001"
                    }, {
                        "name": "电子商务BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "70000001"
                    }, {
                        "name": "星家装饰建材BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "80000001"
                    }, {
                        "name": "悠美家居BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "90000001"
                    }, {
                        "name": "家品会BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "A0000001"
                    }, {
                        "name": "上海星易通汇",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "B0000001"
                    }, {
                        "name": "亿家装饰BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "C0000001"
                    }, {
                        "name": "上海红星宏易投资公司",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "D0000001"
                    }, {
                        "name": "住建集采事业部",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "E0000001"
                    }, {"name": "嵘天投资", "hasChild": true, "number": 0, "departmentCode": "F0000001"}, {
                        "name": "家倍得BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "G0000001"
                    }, {
                        "name": "家倍得总部BU",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "H0000001"
                    }, {
                        "name": "上海红星美凯龙品牌管",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "I0000001"
                    }, {"name": "装修公BU", "hasChild": true, "number": 0, "departmentCode": "J0000001"}, {
                        "name": "西西里亚",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "K0000001"
                    }, {"name": "家金所", "hasChild": true, "number": 0, "departmentCode": "L0000001"}, {
                        "name": "鼎幸投资",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "M0000001"
                    }, {"name": "电商物流BU", "hasChild": true, "number": 0, "departmentCode": "N0000001"}, {
                        "name": "商务咨询",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "O0000001"
                    }, {"name": "星艺佳BU", "hasChild": true, "number": 0, "departmentCode": "P0000001"}, {
                        "name": "星和家居",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "Q0000001"
                    }, {"name": "新伟置业", "hasChild": true, "number": 0, "departmentCode": "S1000001"}, {
                        "name": "悦家",
                        "hasChild": true,
                        "number": 0,
                        "departmentCode": "T0000001"
                    }, {"name": "物流地产", "hasChild": true, "number": 0, "departmentCode": "U0000001"}],
                    "totalEmpNumber": 18912
                }
            };
        },
        '/longyan/feedback/create': function (url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {}
            };
        }
    };
});