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

package org.yes.cart.bulkcommon.service;

import org.yes.cart.service.async.model.JobContext;

/**
 * User: denispavlov
 * Date: 12-08-11
 * Time: 12:54 PM
 */
public interface ExportService {

     enum BulkExportResult {
        OK,
        ERROR;
    }

    /**
     * Perform bulk import.
     *
     * @param context job context of this import.
     *
     * @return {@link BulkExportResult}
     */
    BulkExportResult doExport(JobContext context);

}
