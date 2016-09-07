package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/8/5.
 */
public interface LotteryService {
    /**
     * 获取今日中奖名单
     * @return
     */
    Response getWinningList();

    /**
     * 获取我的奖券列表
     * @param employeeCode
     * @return
     */
    Response getMyWinningList(String employeeCode);

    /**
     * 验证砸蛋状态
     * @param employeeCode
     * @return
     */
    Response validLotteryAuth(String employeeCode);

    /**
     * 砸蛋
     * @param employeeCode
     * @return
     */
    Response drawLottery(String employeeCode);

    /**
     * 获取抽奖地点信息.
     * @return
     */
    Response getLotteryLocation();
}
