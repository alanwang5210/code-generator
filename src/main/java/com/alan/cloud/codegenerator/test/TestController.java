package com.alan.cloud.codegenerator.test;

import com.alan.cloud.codegenerator.common.commonresponse.IgnoreResponseAdvice;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 10100
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 新增
     */
    @PostMapping(value = "/localDateTime")
    @ApiOperation(value = "对比对象新增")
    @IgnoreResponseAdvice
    public List<TestModel> save(TestModel testModel) {
        System.out.println(testModel);



        List<TestModel> testModels = new ArrayList<>();
        testModels.add(testModel);
        return testModels;

    }


}
