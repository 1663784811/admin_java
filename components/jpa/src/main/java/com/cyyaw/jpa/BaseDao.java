package com.cyyaw.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseDao<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    /**
     * 根据ID查询
     *
     * @return
     */
    E findByid(ID id);

    /**
     * 根据id数组查询
     */
    List<E> findByidIsIn(ID[] id);

    @Override
    void deleteById(ID id);


}
