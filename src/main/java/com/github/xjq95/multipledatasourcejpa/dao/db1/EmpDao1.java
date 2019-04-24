package com.github.xjq95.multipledatasourcejpa.dao.db1;

import com.github.xjq95.multipledatasourcejpa.entity.db1.Emp;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpDao1 extends PagingAndSortingRepository<Emp, String> {

}