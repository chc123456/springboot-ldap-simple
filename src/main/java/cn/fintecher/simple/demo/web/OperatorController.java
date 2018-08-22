package cn.fintecher.simple.demo.web;

import cn.fintecher.simple.demo.entity.Operator;
import cn.fintecher.simple.demo.model.request.CreateOperatorRequest;
import cn.fintecher.simple.demo.respository.OperatorRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import java.util.List;
import java.util.Optional;

/**
 * Created by ChenChang on 2017/8/12.
 */
@RestController
@RequestMapping("/api/operator")
@Api(value = "系统用户", description = "系统用户")
public class OperatorController {
    private final Logger log = LoggerFactory.getLogger(OperatorController.class);
    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping("/getOperator/{id}")
    public ResponseEntity<Operator> getOperator(@PathVariable String id) throws InvalidNameException {
        log.debug("REST request to get Operator : {}", id);
        Operator operator = operatorRepository.findById(new LdapName(id)).get();
        return Optional.ofNullable(operator)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getOperators")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<Operator>> getOperators(Pageable pageable, @QuerydslPredicate(root = Operator.class) Predicate predicate) {
        log.debug("REST request to get all of Operator");
        Page<Operator> allList = operatorRepository.findAll(predicate, pageable);
        operatorRepository.findAll();
        return new ResponseEntity<>(allList, HttpStatus.OK);
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Operator>> findAll(){
        return ResponseEntity.ok(Lists.newArrayList(operatorRepository.findAll()));
    }

    @PostMapping("/createOperator")
    public ResponseEntity<Operator> createOperator(@RequestBody @Validated CreateOperatorRequest request) throws InvalidNameException {
        log.debug("REST request to save Operator : {}", request);
        Operator operator = new Operator();
        BeanUtils.copyProperties(request, operator);

         Operator result = operatorRepository.save(operator);
        return ResponseEntity.ok().body(result);
    }

//    @PutMapping("/updateOperator")
//    public ResponseEntity<Operator> updateOperator(@RequestBody Operator operator) {
//        log.debug("REST request to save Operator : {}", operator);
//        if (operator.getDn() == null) {
//            throw new BadRequestException(null, "createOperator", "编辑ID不能为空");
//        }
//        Operator result = operatorRepository.save(operator);
//        return ResponseEntity.ok().body(result);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOperator(@PathVariable String id) throws InvalidNameException {
//        log.debug("REST request to delete operator : {}", id);
//        operatorRepository.deleteById(new LdapName(id));
//        return ResponseEntity.ok().build();
//    }
}
