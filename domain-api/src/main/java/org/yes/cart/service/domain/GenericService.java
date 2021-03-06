/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.service.domain;

import org.hibernate.criterion.Criterion;
import org.yes.cart.dao.GenericDAO;

import java.util.List;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 07-May-2011
 * Time: 11:13:01
 */
public interface GenericService<T> {

    /**
     * Get all entities.
     *
     * @return list of all entities
     */
    List<T> findAll();

    /**
     * Get object by given primary key.
     *
     * @param pk pk value.
     * @return instance if found, otherwise null.
     */
    T findById(long pk);

    /**
     * Persist instance.
     *
     * @param instance instance to persist
     * @return persisted instance
     */
    T create(T instance);

    /**
     * Update instance.
     *
     * @param instance instance to update
     * @return persisted instance
     */
    T update(T instance);

    /**
     * delete instance.
     *
     * @param instance instance to delete
     */
    void delete(T instance);

    /**
     * Find entities by criteria.
     *
     * @param criterion given criteria
     * @return list of found entities.
     */
    List<T> findByCriteria(Criterion... criterion);

    /**
     * Find single entity by criteria.
     *
     * @param criterion given criteria
     * @return single entity or null if not found.
     */
    T findSingleByCriteria(Criterion... criterion);

    /**
     * Get generic dao
     *
     * @return generic dao
     */
    GenericDAO<T, Long> getGenericDao();


}
