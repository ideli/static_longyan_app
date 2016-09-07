package com.chinaredstar.longyan.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.chinaredstar.longyan.api.ContactService;
import com.chinaredstar.longyan.api.vo.Response;
import com.chinaredstar.longyan.common.form.DepartmentForm;
import com.chinaredstar.longyan.model.RedstarDepartment;
import com.chinaredstar.longyan.model.RedstarEmployee;
import com.chinaredstar.longyan.repository.RedstarDepartmentRepository;
import com.chinaredstar.longyan.repository.RedstarEmployeeRepository;
import com.chinaredstar.longyan.repository.specifation.EmployeeSpecifications;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdc on 2016/8/2.
 */
@Service
public class ContactServiceImpl extends BasicService implements ContactService {

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private RedstarEmployeeRepository redstarEmployeeRepository;

    @Autowired
    private RedstarDepartmentRepository redstarDepartmentRepository;

    @Override
    @Transactional(readOnly = true)
    public Response getDepartmentList(String departmentCode) {

        //创建一个结果集
        List<DepartmentForm> departmentFormList = new ArrayList<DepartmentForm>();

        try {
            if (StringUtils.isEmpty(departmentCode)) {
                departmentCode = "";
            }
            List<RedstarDepartment> redstarDepartmentList = redstarDepartmentRepository.findByDepartmentParentCode(departmentCode);
            DepartmentForm departmentForm = null;
            for (RedstarDepartment dept : redstarDepartmentList) {
                departmentForm = new DepartmentForm();
                departmentForm.setDepartmentCode(dept.getDepartmentCode());
                departmentForm.setName(dept.getName());
                departmentForm.setNumber(dept.getPeopleNumber());
                if (departmentForm.getNumber() > 0) departmentForm.setHasChild(true);
                if (!"公司高层".equals(dept.getName())) departmentFormList.add(departmentForm);
            }
            successResponse("查询成功");
            response.addKey("departments", departmentFormList);
            if (!StringUtils.isEmpty(departmentCode)) {
                Specification<RedstarEmployee> specification = Specifications.where(EmployeeSpecifications.filterByDepartmentCode(departmentCode));
                List<RedstarEmployee> employees = redstarEmployeeRepository.findAll(specification);
                response.addKey("employees", employees);
            } else {
                //departmentCode为空时，查出所有在职人员总数
                Specification<RedstarEmployee> specification = Specifications.where(EmployeeSpecifications.findByHrStatus("A"));
                long totalEmpNumber = redstarEmployeeRepository.count(specification);
                response.addKey("totalEmpNumber", totalEmpNumber);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "部门查询异常");
            return response;
        }
        return response;
    }

    /**
     * 模糊查询员工信息
     *
     * @param name
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Response getEmployeeByName(String name) {
        try {
            if (!StringUtils.isEmpty(name)) {

                Specification<RedstarEmployee> specification = Specifications.where(EmployeeSpecifications.findByUserNameLike(name));
                List<RedstarEmployee> employees = redstarEmployeeRepository.findAll(specification);

                successResponse("查询成功");
                Object employee = employees;
                response.addKey("employees", employee);
            }
        } catch (Exception ex) {
            errorResponse(EXCEPTION_CODE, "查询异常");
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Response getEmployeeInfo(String employeeCode) {
        if (StringUtils.isEmpty(employeeCode)) {
            errorResponse(-1001, "employeeCode不能为空");
            return response;
        }
        try {
            RedstarEmployee redstarEmployee = redstarEmployeeRepository.findByEmployeeCode(employeeCode);
            successResponse("查询成功");
            response.addKey("redstarEmployee", redstarEmployee);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception.getMessage());
            errorResponse(EXCEPTION_CODE, "员工信息查询异常");
            return response;
        }

        return response;
    }
}
