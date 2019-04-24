package com.github.xjq95.multipledatasourcejpa.dao.db2;

import com.github.xjq95.multipledatasourcejpa.entity.db2.Emp;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmpDao2 extends PagingAndSortingRepository<Emp, String> {
}