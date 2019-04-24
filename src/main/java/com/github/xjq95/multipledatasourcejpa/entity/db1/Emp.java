package com.github.xjq95.multipledatasourcejpa.entity.db1;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Description: todo complete the comment.
 * User: Administrator
 * Date: 2019-02-15 18:42
 */
@Entity
@Table(name = "emp", schema = "db1", catalog = "")
@Data
public class Emp {
    @Id
    private String id;
    private String name;
}
