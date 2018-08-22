/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.fintecher.simple.demo.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mattias Hellborg Arthursson
 */
@Data
@Entry(objectClasses = {"organizationalUnit", "top"}, base = "ou=Departments")
public final class Department {
    @Id
    private Name id;

    @Attribute(name = "ou")
    private String name;
    @Transient
    private List<Department> departments;
    @Transient
    private String parent;

}
