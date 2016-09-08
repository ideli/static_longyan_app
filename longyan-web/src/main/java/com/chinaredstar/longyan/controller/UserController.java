//package com.chinaredstar.longyan.controller;
//
//
//import com.wordnik.swagger.annotations.Api;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping(value = "user")
//@Api(value = "user", description = "用户相关的接口")
//public class UserController {
////    private static Logger logger = LoggerFactory.getLogger(UserController.class);
////
////    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
////    @ApiOperation(value = "save", notes = "保存用户")
////    @ResponseBody
////    @RequestMapping(value = "save", method = {RequestMethod.POST})
////    public void save(@RequestBody @Valid UserVo user) {
////        /* todo */
////    }
////
////    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
////    @ApiOperation(value = "remove", notes = "删除用户")
////    @ResponseBody
////    @RequestMapping(value = "remove", method = {RequestMethod.POST})
////    @CacheEvict(cacheNames = "skeleton_userController", key = "#id.toString()")
////    public Result remove(@RequestBody Integer id,HttpServletRequest request) {
////        Result result = new Result();
////        result.setCode(ResultCode.C200.getCode());
////        request.getSession().invalidate();
////        logger.info("remove success");
////
////        return result;
////    }
////
////    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
////    @ApiOperation(value = "modify", notes = "修改用户")
////    @ResponseBody
////    @RequestMapping(value = "modify", method = RequestMethod.POST)
////    @CachePut(cacheNames = "skeleton_userController", key = "#user.getId().toString()")
////    public Result<UserVo> modify(@RequestBody UserVo user) {
////        Result result = new Result();
////        result.setCode(ResultCode.C200.getCode());
////        UserVo userVo = new UserVo();
////        userVo.setId(1);
////        userVo.setName("yangguo-update");
////        result.setValue(userVo);
////        logger.info("modify success");
////        return result;
////    }
////
////    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
////    @ApiOperation(value = "query", notes = "查询用户")
////    @ResponseBody
////    @RequestMapping(value = "query/{id}", method = {RequestMethod.GET})
////    @Cacheable(cacheNames = {"skeleton_userController"}, key = "#id.toString()")
////    public Result<UserVo> query(@PathVariable("id") Integer id, HttpServletRequest request) {
////        Result result = new Result();
////        result.setCode(ResultCode.C200.getCode());
////        UserVo userVo = new UserVo();
////        userVo.setId(1);
////        userVo.setName("yangguo");
////        result.setValue(userVo);
////        logger.info("query success");
////        request.getSession().setAttribute("result", result);
////        //这个只能由用户中心设置,其余的系统都不能设置这个attribute
//////        request.getSession().setAttribute("UC-MaxInactiveIntervalInSeconds", 1000000000);
////        return result;
////    }
////
////    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
////    @ApiOperation(value = "query", notes = "查询用户")
////    @ResponseBody
////    @RequestMapping(value = "query", method = {RequestMethod.GET})
////    public Result<UserVo> query(HttpServletRequest request) {
////        logger.info("session success");
////        Result<UserVo> result = (Result<UserVo>) request.getSession().getAttribute("result");
////        request.getSession().setAttribute("name", "yangguo");
////        return result;
////    }
//}
