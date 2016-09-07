/**
 * mock data for m-web, except it in build process.
 * author: zhangxuechao
 */
define('js/api/integral_mock', [], function() {
    return {
        //小区列表
        '/longyan/score/my-score-history': function(url, opts) {
            return {    
                    "ok":true,
                    "message":"查询成功",
                    "code":200,
                    "dataMap":{
                        "page_data":{//积分产生日志
                            "currentPage":2,
                            "currentRecords":[
                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                },
                                                                {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"添加住宅",//产生积分规则的名称
                                    "deltaValue":10,//产生积分的数量
                                    "remark":null
                                }, {
                                    "id":1,
                                    "serialnumber":"12345",
                                    "userId":11757,
                                    "userObject":"employee",
                                    "createDate":"2016-07-10 00:00:00", //产生积分的时间
                                    "ruleId":1,
                                    "ruleName":"删除小区",//产生积分规则的名称
                                    "deltaValue":-20,//产生积分的数量
                                    "remark":null
                                },
                                
                            ],
                            "currentRecordsNum":0,
                            "totalPages":2,
                            "totalRecords":12,
                            "pageSize":10,
                            "paginationDescribe":null
                        },
                        "score":110,//当前积分
                        "score_month":40//当月积分
                    }
            };
        }
    };
});