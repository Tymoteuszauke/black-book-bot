package com.blackbook.botrest.dao;

import com.blackbook.botrest.model.Promotion;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tymek on 25.08.17.
 */
public interface PromotionsRepository extends CrudRepository<Promotion, Long> {
}
