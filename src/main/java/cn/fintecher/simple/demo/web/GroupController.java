package cn.fintecher.simple.demo.web;

import cn.fintecher.simple.demo.entity.Group;
import cn.fintecher.simple.demo.respository.GroupRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.util.List;
import java.util.Optional;

/**
 * Created by ChenChang on 2017/8/12.
 */
@RestController
@RequestMapping("/api/group")
@Api(value = "分组", description = "分组/类似角色")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/getGroup/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable String id) throws InvalidNameException {
        log.debug("REST request to get Group : {}", id);
        Group group = groupRepository.findById(new LdapName(id)).get();
        return Optional.ofNullable(group)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getGroups")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<Group>> getGroups(Pageable pageable, @QuerydslPredicate(root = Group.class) Predicate predicate) {
        log.debug("REST request to get all of Group");
        Page<Group> allList = groupRepository.findAll(predicate, pageable);
        groupRepository.findAll();
        return new ResponseEntity<>(allList, HttpStatus.OK);
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Group>> findAll(){
        return ResponseEntity.ok(Lists.newArrayList(groupRepository.findAll()));
    }

//    @PostMapping("/createGroup")
//    public ResponseEntity<Group> createGroup(@RequestBody @Validated CreateGroupRequest request) throws InvalidNameException {
//        log.debug("REST request to save Group : {}", request);
//        Group group = new Group();
//        BeanUtils.copyProperties(request, group);
//
//         Group result = groupRepository.save(group);
//        return ResponseEntity.ok().body(result);
//    }

//    @PutMapping("/updateGroup")
//    public ResponseEntity<Group> updateGroup(@RequestBody Group group) {
//        log.debug("REST request to save Group : {}", group);
//        if (group.getDn() == null) {
//            throw new BadRequestException(null, "createGroup", "编辑ID不能为空");
//        }
//        Group result = groupRepository.save(group);
//        return ResponseEntity.ok().body(result);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteGroup(@PathVariable String id) throws InvalidNameException {
//        log.debug("REST request to delete group : {}", id);
//        groupRepository.deleteById(new LdapName(id));
//        return ResponseEntity.ok().build();
//    }
}
