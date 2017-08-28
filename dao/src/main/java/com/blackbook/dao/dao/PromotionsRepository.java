package com.blackbook.dao.dao;

import com.blackbook.dao.model.Promotion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by tymek on 25.08.17.
 */
public interface PromotionsRepository extends CrudRepository<Promotion, Long> {

    List<Promotion> findAll();
}
