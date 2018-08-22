package cn.fintecher.simple.demo.web;

import cn.fintecher.simple.demo.entity.Department;
import cn.fintecher.simple.demo.respository.DepartmentRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ChenChang on 2017/8/12.
 */
@RestController
@RequestMapping("/api/department")
@Api(value = "部门", description = "部门")
public class DepartmentController {
    private static final LdapName DEPARTMENTS_OU = LdapUtils.newLdapName("ou=Departments,dc=example,dc=com");
    private final Logger log = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/getDepartment/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable String id) throws InvalidNameException {
        log.debug("REST request to get Department : {}", id);
        Department department = departmentRepository.findById(new LdapName(id)).get();
        return Optional.ofNullable(department)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getDepartments")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<Department>> getDepartments(Pageable pageable, @QuerydslPredicate(root = Department.class) Predicate predicate) {
        log.debug("REST request to get all of Department");
        Page<Department> allList = departmentRepository.findAll(predicate, pageable);
        departmentRepository.findAll();
        return new ResponseEntity<>(allList, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Department>> findAll() {
        List<Department> departments = Lists.newArrayList(departmentRepository.findAll());
        return ResponseEntity.ok(buildRootDepartment(departments));
    }

    private Department findChildren(Department parent, List<Department> list) {
        for (Department department : list) {
            LdapName ldapName = LdapUtils.newLdapName(department.getId());
            if (ldapName.startsWith(parent.getId())&&!ldapName.equals(parent.getId())) {
                if (Objects.isNull(parent.getDepartments())) {
                    parent.setDepartments(new ArrayList<>());
                }
                parent.getDepartments().add(findChildren(department, list));
            }
        }
        return parent;
    }

    private List<Department> buildRootDepartment(List<Department> list) {
        List<Department> roots = new ArrayList<>();
        for (Department department : list) {
            LdapName ldapName = LdapUtils.newLdapName(DEPARTMENTS_OU);
            if (department.getId().equals(ldapName)) {
                 roots.add(findChildren(department, list));
            }
        }
        return roots;
    }
//    @PostMapping("/createDepartment")
//    public ResponseEntity<Department> createDepartment(@RequestBody @Validated CreateDepartmentRequest request) throws InvalidNameException {
//        log.debug("REST request to save Department : {}", request);
//        Department department = new Department();
//        BeanUtils.copyProperties(request, department);
//
//         Department result = departmentRepository.save(department);
//        return ResponseEntity.ok().body(result);
//    }

//    @PutMapping("/updateDepartment")
//    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
//        log.debug("REST request to save Department : {}", department);
//        if (department.getDn() == null) {
//            throw new BadRequestException(null, "createDepartment", "编辑ID不能为空");
//        }
//        Department result = departmentRepository.save(department);
//        return ResponseEntity.ok().body(result);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) throws InvalidNameException {
//        log.debug("REST request to delete department : {}", id);
//        departmentRepository.deleteById(new LdapName(id));
//        return ResponseEntity.ok().build();
//    }
}
