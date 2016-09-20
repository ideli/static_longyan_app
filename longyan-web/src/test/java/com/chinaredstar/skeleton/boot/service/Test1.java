package com.chinaredstar.skeleton.boot.service;

import com.chinaredstar.perseus.utils.JsonUtil;
//import com.chinaredstar.longyan.controller.UserController;
//import com.chinaredstar.longyan.vo.UserVo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.util.AopTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试点:
 * <p>
 * 1.spring mvc mock and rest test
 * <p>
 * 2.cache test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring/applicationContext1.xml")
public class Test1 {
    private MockMvc mockMvc;

//    @Autowired
//    UserController userController;

    @Autowired
    CacheManager cacheManager;

    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);
//
//        this.mockMvc = MockMvcBuilders
//                .standaloneSetup(AopTestUtils.getTargetObject(userController))
//                .build();
    }

    @Test
    public void test1() throws Exception {
//        UserVo userVo = new UserVo();
//        userVo.setId(1);
//        userVo.setName("yangguo");
//        Result result = new Result();
//        result.setCode(ResultCode.C200.getCode());
//        result.setValue(userVo);
//        String json = JsonUtil.toJson(result, false);
//
//
//        mockMvc.perform(get("/user/query/{id}", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(json, true));
    }
}
