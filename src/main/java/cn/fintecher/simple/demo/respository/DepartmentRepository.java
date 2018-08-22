package cn.fintecher.simple.demo.respository;

import cn.fintecher.simple.demo.entity.Department;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created by ChenChang on 2018/8/16.
 */
public interface DepartmentRepository  extends QuerydslPredicateExecutor<Department>, LdapRepository<Department> {


}
