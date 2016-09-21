/**
 * mock data for m-web, except it in build process.
 * author: zhangxuechao
 */
define('js/api/score_mock', [], function() {
    return {
        //小区列表
        '/longyan/score/rank': function(url, opts) {
            return {
                "ok": true,
                "message": "查询成功",
                "code": 200,
                "dataMap": {
                    "rank": [{
                        "id": 169560,
                        "employeeId": 11640,
                        "employeeXingMing": "李燕",
                        "year": 2016,
                        "month": 7,
                        "inputCommunityAmount": 0,
                        "inputMemberAmount": 0,
                        "scoreRank": 1,
                        "scoreAmount": 82424,
                        "mallName": "三明三元商场",
                        "organizationName": "福建中区"
                    }, {
                        "id": 169561,
                        "employeeId": 10641,
                        "employeeXingMing": "李正",
                        "year": 2016,
                        "month": 7,
                        "inputCommunityAmount": 0,
                        "inputMemberAmount": 0,
                        "scoreRank": 2,
                        "scoreAmount": 73183,
                        "mallName": "三明三元商场",
                        "organizationName": "福建中区"
                    }, {
                        "id": 169562,
                        "employeeId": 11642,
                        "employeeXingMing": "许可",
                        "year": 2016,
                        "month": 7,
                        "inputCommunityAmount": 0,
                        "inputMemberAmount": 0,
                        "scoreRank": 3,
                        "scoreAmount": 73180,
                        "mallName": "三明三元商场",
                        "organizationName": "福建中区"
                    }, {
                        "id": 169799,
                        "employeeId": 11879,
                        "employeeXingMing": "陈征伟",
                        "year": 2016,
                        "month": 7,
                        "inputCommunityAmount": 0,
                        "inputMemberAmount": 0,
                        "scoreRank": 199,
                        "scoreAmount": 33180,
                        "mallName": "上海浦东沪南商场",
                        "organizationName": "京沪西南"
                    }],
                    "currentUser": {
                        "id": 169799,
                        "employeeId": 11879,
                        "employeeXingMing": "陈征伟",
                        "year": 2016,
                        "month": 7,
                        "inputCommunityAmount": 0,
                        "inputMemberAmount": 0,
                        "scoreRank": 199,
                        "scoreAmount": 33180,
                        "mallName": "上海浦东沪南商场",
                        "organizationName": "京沪西南"
                    }
                }
            };
        },
        '/longyan/score/rank-history': function(url, opts) {
            return {
                "ok": true,
                "message": "查询成功",
                "code": 200,
                "dataMap": {
                    "data": [{
                        "id": 133077,
                        "employeeId": 10425,
                        "employeeXingMing": "刘万华",
                        "year": 2016,
                        "month": 6,
                        "inputCommunityAmount": 0,
                        "scoreRank": 1,
                        "scoreAmount": 63464,
                        "inputMemberAmount": 0,
                        "mallName": "三明三元商场", //商场名称
                        "organizationName": "福建中区" //大区名称
                    }, {
                        "id": 133082,
                        "employeeId": 11498,
                        "employeeXingMing": "万明俊",
                        "year": 2016,
                        "month": 5,
                        "inputCommunityAmount": 0,
                        "scoreRank": 1,
                        "scoreAmount": 53259,
                        "inputMemberAmount": 0,
                        "mallName": "三明三元商场", //商场名称
                        "organizationName": "福建中区" //大区名称
                    }, {
                        "id": 133084,
                        "employeeId": 11498,
                        "employeeXingMing": "王八蛋",
                        "year": 2016,
                        "month": 4,
                        "inputCommunityAmount": 0,
                        "scoreRank": 1,
                        "scoreAmount": 53259,
                        "inputMemberAmount": 0,
                        "mallName": "三明三元商场", //商场名称
                        "organizationName": "福建中区" //大区名称
                    }]
                }
            };
        }
    };
});