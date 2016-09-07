/**
 * mock data for m-web, except it in build process.
 * author: zhangxuechao
 */
define('js/api/community_mock', [], function() {
    return {
        //小区列表
        '/longyan/community/list': function(url, opts) {
            return {
                "ok": true,
                "message": null,
                "code": 200,
                "dataMap": {
                    "result": {
                        "currentPage": 1,
                        "currentRecords": [{
                            "id": 1,
                            "createDate": "2016-04-28 11:46:55",
                            "updateDate": "2016-05-08 02:06:45",
                            "createEmployeeId": 11503,
                            "createXingming": "张学超",
                            "updateEmployeeId": 11503,
                            "updateEmployeeXingMing": "管理员",
                            "ownerId": 11503,
                            "ownerXingMing": "张学超",
                            "province": "上海",
                            "provinceCode": "310000",
                            "city": "上海市",
                            "cityCode": "310100",
                            "area": "黄浦区",
                            "areaCode": "310101",
                            "xingMing": "张三",
                            "communityId": 5127,
                            "communityName": "古北国际花园",
                            "building": "1",
                            "unit": "1",
                            "gender": "male",
                            "phone": "18516246910",
                            "room": "101",
                            "source": "input",
                            "areaAmount": "60以下",
                            "areaAmountId": 1,
                            "roomLayout": "4室",
                            "roomLayoutId": 4,
                            "renovationStatus": "毛坯",
                            "renovationStatusId": 2,
                            "roomType": "普通住宅",
                            "roomTypeId": 3
                        }, {
                            "id": 2,
                            "createDate": "2016-05-07 23:38:52",
                            "updateDate": "2016-05-08 02:04:15",
                            "createEmployeeId": 11503,
                            "createXingming": "管理员",
                            "updateEmployeeId": 11503,
                            "updateEmployeeXingMing": "管理员",
                            "ownerId": 11503,
                            "ownerXingMing": "管理员",
                            "province": "北京",
                            "provinceCode": "110000",
                            "city": "北京市",
                            "cityCode": "110100",
                            "area": "东城区",
                            "areaCode": "110101",
                            "xingMing": null,
                            "communityId": 20523,
                            "communityName": null,
                            "building": "222",
                            "unit": "333",
                            "gender": null,
                            "phone": null,
                            "room": "222",
                            "source": "employee",
                            "areaAmount": "90-120",
                            "areaAmountId": 3,
                            "roomLayout": "2室",
                            "roomLayoutId": 2,
                            "renovationStatus": "不详",
                            "renovationStatusId": 1,
                            "roomType": "商住",
                            "roomTypeId": 2
                        }],
                        "currentRecordsNum": 0,
                        "totalPages": 1,
                        "totalRecords": 2,
                        "pageSize": 20,
                        "paginationDescribe": null
                    }
                }
            };
        },
        '/longyan/community/690': function(url, opts) {
            return {
                "ok": true,
                "message": null,
                "code": 0,
                "dataMap": {
                    "community": {
                        "id": 690,
                        "name": "中环一号",
                        "province": "上海",
                        "provinceCode": "310000",
                        "city": "上海市",
                        "cityCode": "310100",
                        "area": "宝山区",
                        "areaCode": "310101",
                        "InputRate": "0.8", //录入率
                        "occupancyRate": "0.3", //入住率
                        "address": "详细地址",
                        "areaMonut": 100000,
                        "roomMount": 1000,
                        "buildingAmount": 10,
                        "alreadyCheckAmount": 100,
                        "priceSection": 53000,
                        "buildingDate": 1990,
                        "developers": "一个开发商",
                        "hotline": "1550"
                    }
                }
            };
        },
        //创建小区
        '/longyan/community/create': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        //更新小区
        '/longyan/community/update': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
         //创建楼栋号
        '/longyan/community/building/list': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
         //创建楼栋号
        '/longyan/community/building/add': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        //搜索小区
        '/longyan/community/search-by-name': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {
                    "result": [{
                        "name": "汤臣豪园一期",
                        "id": 1
                    }, {
                        "name": "汤臣豪园二期",
                        "id": 2
                    }, {
                        "name": "汤臣豪园三期",
                        "id": 3
                    }, {
                        "name": "汤臣豪园四期",
                        "id": 4
                    }]
                }
            };
        }
    };
});