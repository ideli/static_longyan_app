package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.LotteryService;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.model.*;
import com.chinaredstar.longyan.repository.*;
import com.chinaredstar.longyan.repository.specifation.RedstarLotteryLogSpcifications;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


/**
 * Created by mdc on 2016/8/5.
 */
@Service
public class LotteryServiceImpl extends BasicService implements LotteryService {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    @Autowired
    private RedstarLotteryRecordRepository lotteryRecordRepository;

    @Autowired
    private RedstarLotteryLocationRepository redstarLotteryLocationRepository;

    @Autowired
    private RedstarLotteryLogRepository redstarLotteryLogRepository;

    @Autowired
    private RedstarLotterySettingRepository redstarLotterySettingRepository;

    @Autowired
    private RedstarEmployeeRepository redstarEmployeeRepository;

    @Autowired
    private RedstarLotteryPresentRepository redstarLotteryPresentRepository;

    /**
     * 砸蛋-中奖名单
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Response getWinningList() {
        try {

            int page = 1;
            int rows = 20;
            //以获奖时间倒序排列  每页20条
            PageRequest pageRequest = new PageRequest(page - 1, rows, Sort.Direction.DESC, "createDate");

            //得到中奖信息类别resultList
            Page<RedstarLotteryRecord> lotteryRecordPage = lotteryRecordRepository.findAll(pageRequest);
            successResponse("查询成功");
            response.addKey("lotteryRecords", lotteryRecordPage.getContent());

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询成功");
            return response;
        }
        return response;
    }

    /**
     * 砸蛋-我的奖品
     *
     * @param employeeCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Response getMyWinningList(String employeeCode) {
        if (StringUtils.isBlank(employeeCode)) {
            errorResponse(-1001, "employeeCode不能为空");
            return response;
        }
        try {
            //已兑换list
            List<RedstarLotteryRecord> usedList = lotteryRecordRepository.findByemployeeCodeAndUsed(employeeCode, true);
            //未兑换list
            List<RedstarLotteryRecord> notUsedList = lotteryRecordRepository.findByemployeeCodeAndUsed(employeeCode, false);
            successResponse("查询成功");
            response.addKey("usedList", usedList);
            response.addKey("notUsedList", notUsedList);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询异常");
            return response;
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Response validLotteryAuth(String employeeCode) {
        DateTime today = DateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
        today = DateTime.parse(today.toString("yyyy-MM-dd") + " 00:00:00", format);
        DateTime tomorrow = today.plusDays(1);
        Specification<RedstarLotteryLog> specification = Specifications.where(RedstarLotteryLogSpcifications.filterByEmployeeCode(employeeCode))
                .and(RedstarLotteryLogSpcifications.filterGtCreatedDate(today.toDate()))
                .and(RedstarLotteryLogSpcifications.filterLtCreatedDate(tomorrow.toDate()));
        List<RedstarLotteryLog> logs = redstarLotteryLogRepository.findAll(specification);
        List<RedstarLotterySetting> settingList = redstarLotterySettingRepository.findByShowData(true);
        RedstarLotterySetting setting = settingList.size() > 0 ? settingList.get(0) : null;
        if (setting == null) {
            errorResponse(-1001, "请设置砸蛋配置");
            response.addKey("isLottery", false);
            return response;
        }
        if (logs.size() >= setting.getTotalTimes()) {
            successResponse("今天机会用完了，请明天再来");
            response.addKey("isLottery", false);
            return response;
        } else if (logs.size() < setting.getTotalTimes() && logs.size() >= 1) {
            successResponse("");
            RedstarLotteryLog log = logs.get(logs.size() - 1);
            DateTime checkDate = new DateTime(log.getCreateDate()).plusHours(setting.getCdTime());
            int hours = Hours.hoursBetween(new DateTime(log.getCreateDate()),  DateTime.now()).getHours();
            int minutes = Minutes.minutesBetween(new DateTime(log.getCreateDate()),  DateTime.now()).getMinutes();
            if (hours >= setting.getCdTime()) {
                response.addKey("isLottery", true);
            } else {
                response.addKey("isLottery", false);
                response.setMessage("龙蛋还未愈合哟~");
            }
            response.addKey("hours", setting.getCdTime() - hours);
            response.addKey("minutes", minutes);
        } else {
            successResponse("可以砸蛋");
            response.addKey("isLottery", true);
        }
        return response;
    }

    @Override
    @Transactional
    public Response drawLottery(String employeeCode) {
        DateTime today = DateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
        today = DateTime.parse(today.toString("yyyy-MM-dd") + " 00:00:00", format);
        DateTime tomorrow = today.plusDays(1);
        Specification<RedstarLotteryLog> specification = Specifications.where(RedstarLotteryLogSpcifications.filterByEmployeeCode(employeeCode))
                .and(RedstarLotteryLogSpcifications.filterGtCreatedDate(today.toDate()))
                .and(RedstarLotteryLogSpcifications.filterLtCreatedDate(tomorrow.toDate()));
        List<RedstarLotteryLog> logs = redstarLotteryLogRepository.findAll(specification);
        List<RedstarLotterySetting> settingList = redstarLotterySettingRepository.findByShowData(true);
        RedstarLotterySetting setting = settingList.size() > 0 ? settingList.get(0) : null;
        if (setting == null) {
            errorResponse(-1001, "请设置砸蛋配置");
            response.addKey("isLottery", false);
            return response;
        }
        if (logs.size() >= setting.getTotalTimes()) {
            successResponse("今天机会用完了，请明天再来");
            response.addKey("isLottery", false);
            return response;
        } else if (logs.size() < setting.getTotalTimes() && logs.size() >= 1) {
            successResponse("");
            RedstarLotteryLog log = logs.get(logs.size() - 1);
            DateTime checkDate = new DateTime(log.getCreateDate()).plusHours(setting.getCdTime());
            int hours = Hours.hoursBetween(new DateTime(log.getCreateDate()), DateTime.now()).getHours();
            int minutes = Minutes.minutesBetween(new DateTime(log.getCreateDate()),  DateTime.now()).getMinutes();
            if (hours >= setting.getCdTime()) {
                successResponse("砸蛋成功");
                response.addKey("isLottery", false);
                int type = 1;
                if (logs.size() + 1 == setting.getTotalTimes()) type = 2;
                getLotteryNumber(employeeCode, setting, response, type);
            } else {
                response.setMessage("龙蛋还未愈合哟~");
                response.addKey("isLottery", false);
            }
            response.addKey("hours", setting.getCdTime() - hours);
            response.addKey("minutes", minutes);
        } else {
            //砸蛋
            successResponse("砸蛋成功");
            response.addKey("isLottery", false);
            getLotteryNumber(employeeCode, setting, response, 1);
        }
        return response;
    }

    public void getLotteryNumber(String employeeCode, RedstarLotterySetting setting, Response response, int type) {
        String lotteryNumber = "";
        double percent = setting.getPercent();
        boolean isLucky = false;
        if (Math.random() * 100 < percent) {
            isLucky = true;
            lotteryNumber = String.valueOf(Math.round(Math.random() * 1000000));
            if (lotteryNumber.length() < 6) {
                lotteryNumber += "0";
            }
        }
        RedstarEmployee employee = redstarEmployeeRepository.findByEmployeeCode(employeeCode);
        if (employee != null) {
            RedstarLotteryLog log = new RedstarLotteryLog();
            log.setEmployeeInfo(employee);
            log.setCreateDate(DateTime.now().toDate());
            log.setPresentCode(lotteryNumber);
            redstarLotteryLogRepository.save(log);
            response.addKey("isLucky", isLucky);
            if (isLucky) {
                List<RedstarLotteryPresent> presents = redstarLotteryPresentRepository.findByShowDataAndIsUp(true, true);
                RedstarLotteryRecord record = new RedstarLotteryRecord();
                record.setEmployeeCode(employeeCode);
                record.setPresentCode(lotteryNumber);
                record.setEmployeeId(employee.getId());
                record.setEmployeeName(employee.getXingMing());
                record.setCreateDate(DateTime.now().toDate());
                record.setMobile(employee.getMobilePhone());
                Random rand = new Random();
                int num = rand.nextInt(presents.size());
                RedstarLotteryPresent present = presents.get(num);
                record.setPresentId(present.getId());
                record.setPresentName(present.getName());
                record.setColorCode(present.getColorCode());
                lotteryRecordRepository.save(record);
                response.addKey("present", present.getName());//中奖了 中奖名称。
            } else {
                if (type == 1) {
                    response.setMessage("加油，今天还有机会哟~");
                } else {
                    response.setMessage("奖品都已经找到主人了，明天再来吧!");
                }
            }
        }
    }

    /**
     * 砸蛋-获取砸蛋地点信息
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Response getLotteryLocation() {
        try {
            //获取砸蛋地点信息
            List<RedstarLotteryLocation> lotteryLocationList = (List<RedstarLotteryLocation>) redstarLotteryLocationRepository.findAll();
            successResponse("查询成功");
            response.addKey("lotteryLocation", lotteryLocationList);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "查询异常");
            return response;
        }
        return response;
    }
}
