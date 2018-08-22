package cn.fintecher.simple.demo.respository;

import cn.fintecher.simple.demo.entity.Group;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created by ChenChang on 2018/8/16.
 */
public interface GroupRepository extends QuerydslPredicateExecutor<Group>, LdapRepository<Group> {
}
