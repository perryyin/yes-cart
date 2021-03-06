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

package org.yes.cart.service.domain.impl;

import org.junit.Before;
import org.junit.Test;
import org.yes.cart.BaseCoreDBTestCase;
import org.yes.cart.constants.Constants;
import org.yes.cart.constants.ServiceSpringKeys;
import org.yes.cart.domain.entity.ProductSku;
import org.yes.cart.domain.entity.SkuWarehouse;
import org.yes.cart.domain.entity.Warehouse;
import org.yes.cart.domain.misc.Pair;
import org.yes.cart.service.domain.ProductSkuService;
import org.yes.cart.service.domain.SkuWarehouseService;
import org.yes.cart.service.domain.WarehouseService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class TestSkuWarehouseServiceImpl extends BaseCoreDBTestCase {

    private WarehouseService warehouseService;
    private SkuWarehouseService skuWarehouseService;
    private ProductSkuService productSkuService;

    @Before
    public void setUp()  {
        warehouseService = (WarehouseService) ctx().getBean(ServiceSpringKeys.WAREHOUSE_SERVICE);
        skuWarehouseService = (SkuWarehouseService) ctx().getBean(ServiceSpringKeys.SKU_WAREHOUSE_SERVICE);
        productSkuService = (ProductSkuService) ctx().getBean(ServiceSpringKeys.PRODUCT_SKU_SERVICE);
        super.setUp();

    }

    @Test
    public void testCreate() {
        SkuWarehouse skuWarehouse = skuWarehouseService.getGenericDao().getEntityFactory().getByIface(SkuWarehouse.class);
        skuWarehouse.setSkuCode("PRODUCT7");
        skuWarehouse.setWarehouse(warehouseService.findById(1L));
        skuWarehouse.setQuantity(BigDecimal.TEN);
        skuWarehouse = skuWarehouseService.create(skuWarehouse);
        assertTrue(skuWarehouse.getSkuWarehouseId() > 0);
        skuWarehouseService.delete(skuWarehouse);
    }

    // TODO: YC-64 fix to not depend on order or running
    @Test
    public void testGetQuantity() {
        SkuWarehouse skuWarehouse;
        skuWarehouse = skuWarehouseService.getGenericDao().getEntityFactory().getByIface(SkuWarehouse.class);
        skuWarehouse.setSkuCode("PRODUCT7");
        skuWarehouse.setWarehouse(warehouseService.findById(2L));
        skuWarehouse.setQuantity(new BigDecimal("10.00"));
        skuWarehouse.setReserved(new BigDecimal("5.00"));
        skuWarehouseService.create(skuWarehouse);
        skuWarehouse = skuWarehouseService.getGenericDao().getEntityFactory().getByIface(SkuWarehouse.class);
        skuWarehouse.setSkuCode("PRODUCT7");
        skuWarehouse.setWarehouse(warehouseService.findById(3L));
        skuWarehouse.setQuantity(new BigDecimal("4.00"));
        skuWarehouse.setReserved(new BigDecimal("0.00"));
        skuWarehouseService.create(skuWarehouse);
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        warehouses.add(warehouseService.findById(3L));
        warehouses.add(warehouseService.findById(2L));
        warehouses.add(warehouseService.findById(1L));
        ProductSku psku = productSkuService.findById(11006L);
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(warehouses, psku.getCode());
        assertEquals(new BigDecimal("14.00"), rez.getFirst());
        assertEquals(new BigDecimal("5.00"), rez.getSecond());
    }

    /**
     * No records about quantity on warehouses.
     */
    @Test
    public void testGetQuantity2() {
        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        warehouses.add(warehouseService.findById(3L));
        warehouses.add(warehouseService.findById(2L));
        warehouses.add(warehouseService.findById(1L));
        ProductSku psku = productSkuService.findById(11007L);
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(warehouses, psku.getCode());
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), rez.getFirst());
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), rez.getSecond());
    }

    @Test
    public void testUpdate() {
        SkuWarehouse skuWarehouse = skuWarehouseService.getGenericDao().getEntityFactory().getByIface(SkuWarehouse.class);
        skuWarehouse.setSkuCode("PRODUCT7");
        skuWarehouse.setWarehouse(warehouseService.findById(1L));
        skuWarehouse.setQuantity(BigDecimal.TEN);
        skuWarehouse = skuWarehouseService.create(skuWarehouse);
        assertTrue(skuWarehouse.getSkuWarehouseId() > 0);
        skuWarehouse.setQuantity(BigDecimal.ONE);
        skuWarehouse = skuWarehouseService.update(skuWarehouse);
        assertEquals(BigDecimal.ONE, skuWarehouse.getQuantity());
        long pk = skuWarehouse.getSkuWarehouseId();
        skuWarehouseService.delete(skuWarehouse);
        skuWarehouse = skuWarehouseService.findById(pk);
        assertNull(skuWarehouse);
    }

    @Test
    public void testDelete() {
        SkuWarehouse skuWarehouse = skuWarehouseService.getGenericDao().getEntityFactory().getByIface(SkuWarehouse.class);
        skuWarehouse.setSkuCode("PRODUCT7");
        skuWarehouse.setWarehouse(warehouseService.findById(1L));
        skuWarehouse.setQuantity(BigDecimal.TEN);
        skuWarehouse = skuWarehouseService.create(skuWarehouse);
        assertTrue(skuWarehouse.getSkuWarehouseId() > 0);
        long pk = skuWarehouse.getSkuWarehouseId();
        skuWarehouseService.delete(skuWarehouse);
        skuWarehouse = skuWarehouseService.findById(pk);
        assertNull(skuWarehouse);
    }

    @Test
    public void testReserveQuantity() {
        final Warehouse warehouse = warehouseService.findById(1L);
        ProductSku productSku = productSkuService.findById(10004L); // 4 items on 1 warehouse
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("3.00"))); // 4 total and 3 reserved
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());

        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("3.00"), rez.getSecond());
        assertEquals(new BigDecimal("10.00"), skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("11.00"))); // 4 total and 4 reserved 10 to reserve
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("4.00"), rez.getSecond());
    }

    @Test
    public void testReserveBackorderQuantity() {
        final Warehouse warehouse = warehouseService.findById(1L);
        ProductSku productSku = productSkuService.findById(10004L); // 4 items on 1 warehouse
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("3.00"))); // 4 total and 3 reserved
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());

        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("3.00"), rez.getSecond());
        assertEquals(new BigDecimal("0.00"), skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("11.00"), true)); // 4 total and 14 reserved 0 to reserve
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("14.00"), rez.getSecond());
    }

    /**
     * Need to reduce reserved quantity and reduce available quantity
     */
    @Test
    public void testReservationAndVoidReservation() {
        final Warehouse warehouse = warehouseService.findById(1L);
        ProductSku productSku = productSkuService.findById(10004L); // 4 items on 1 warehouse
        //3 items reservation
        BigDecimal toReserve = skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("3.00"));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toReserve); // 4 total and 3 reserved
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("3.00"), rez.getSecond());
        // void 2 items
        BigDecimal toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("2.00"));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toVoidReserve); // 4 total and 3 reserved
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("1.00"), rez.getSecond());
        // void 1 item
        toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("1.00"));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toVoidReserve); // 4 total and 3 reserved
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        // void 5 items
        toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("5.00"));
        assertEquals(new BigDecimal("5.00"), toVoidReserve);
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
    }

    /**
     * Need to reduce reserved quantity and reduce available quantity
     */
    @Test
    public void testReservationBackorderAndVoidReservation() {
        final Warehouse warehouse = warehouseService.findById(1L);
        ProductSku productSku = productSkuService.findById(10004L); // 4 items on 1 warehouse
        //3 items reservation
        BigDecimal toReserve = skuWarehouseService.reservation(warehouse, productSku.getCode(), new BigDecimal("10.00"), true);
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toReserve); // 4 total and 10 reserved
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("10.00"), rez.getSecond());
        // void 2 items
        BigDecimal toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("2.00"));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toVoidReserve); // 4 total and 8 reserved
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("8.00"), rez.getSecond());
        // void 5 item
        toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("5.00"));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), toVoidReserve); // 4 total and 3 reserved
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("3.00"), rez.getSecond());
        // void 5 items
        toVoidReserve = skuWarehouseService.voidReservation(warehouse, productSku.getCode(), new BigDecimal("5.00"));
        assertEquals(new BigDecimal("2.00"), toVoidReserve);
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("4.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
    }

    @Test
    public void testDebitCredit() {
        final Warehouse warehouse = warehouseService.findById(1L);
        ProductSku productSku = productSkuService.findById(10004L); // 4 items on 1 warehouse
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouseService.debit(warehouse, productSku.getCode(), new BigDecimal("3.00"))); // 2 total and 1 reserved
        Pair<BigDecimal, BigDecimal> rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("1.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouseService.debit(warehouse, productSku.getCode(), new BigDecimal("1.00"))); // 2 total and 1 reserved
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("0.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        assertEquals(new BigDecimal("7.00"), skuWarehouseService.debit(warehouse, productSku.getCode(), new BigDecimal("7.00")));
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("0.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        assertEquals(new BigDecimal("0.00"), skuWarehouseService.debit(warehouse, productSku.getCode(), new BigDecimal("0.00")));
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("0.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        skuWarehouseService.credit(warehouse, productSku.getCode(), new BigDecimal("74.00"));
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("74.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        skuWarehouseService.credit(warehouse, productSku.getCode(), new BigDecimal("0.00"));
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("74.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
        assertEquals(new BigDecimal("6.00"), skuWarehouseService.debit(warehouse, productSku.getCode(), new BigDecimal("80.00")));
        rez = skuWarehouseService.findQuantity(new ArrayList<Warehouse>() {{
            add(warehouse);
        }}, productSku.getCode());
        assertEquals(new BigDecimal("0.00"), rez.getFirst());
        assertEquals(new BigDecimal("0.00"), rez.getSecond());
    }

    @Test
    public void testFindProductSkusOnWarehouse() {
        //10000 product id - sobot has 4 skus on 1 warehouse
        List<SkuWarehouse> skusWarehouseList = skuWarehouseService.getProductSkusOnWarehouse(10000L, 1L);
        assertEquals(4, skusWarehouseList.size());
        for (SkuWarehouse skuWarehouse : skusWarehouseList) {
            if ("SOBOT-BEER".equals(skuWarehouse.getSkuCode())) {
                assertEquals(1, skuWarehouse.getQuantity().intValue());
            }
            if ("SOBOT-PINK".equals(skuWarehouse.getSkuCode())) {
                assertEquals(2, skuWarehouse.getQuantity().intValue());
            }
            if ("SOBOT-LIGHT".equals(skuWarehouse.getSkuCode())) {
                assertEquals(3, skuWarehouse.getQuantity().intValue());
            }
            if ("SOBOT-ORIG".equals(skuWarehouse.getSkuCode())) {
                assertEquals(4, skuWarehouse.getQuantity().intValue());
            }
        }
    }

    @Test
    public void testFindProductAvailableToSellQuantitySobotSHOP10() {
        //10000 product id - sobot has 4 skus on 1 warehouse
        final List<Warehouse> shop10wh = warehouseService.getByShopId(10L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(10000L, shop10wh);
        assertEquals(4, skusWarehouse.size());
        assertEquals(0, new BigDecimal(1).compareTo(skusWarehouse.get("SOBOT-BEER")));
        assertEquals(0, new BigDecimal(2).compareTo(skusWarehouse.get("SOBOT-PINK")));
        assertEquals(0, new BigDecimal(3).compareTo(skusWarehouse.get("SOBOT-LIGHT")));
        assertEquals(0, new BigDecimal(4).compareTo(skusWarehouse.get("SOBOT-ORIG")));
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantitySobotSHOP10() {
        //10000 product id - sobot has 4 skus on 1 warehouse
        final List<Warehouse> shop10wh = warehouseService.getByShopId(10L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductSkuAvailableToSellQuantity("SOBOT-PINK", shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(2).compareTo(skusWarehouse.get("SOBOT-PINK")));
    }

    @Test
    public void testFindProductAvailableToSellQuantityProduct5AccSHOP10() {
        //14004 product id - PRODUCT5-ACC only has associations
        final List<Warehouse> shop10wh = warehouseService.getByShopId(10L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(14004L, shop10wh);
        assertEquals(0, skusWarehouse.size());
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantityProduct8SHOP10() {
        //15007 product id - PRODUCT8 only has SKU
        final List<Warehouse> shop10wh = warehouseService.getByShopId(10L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(15007L, shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("PRODUCT8")));
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantityProduct8bySkuSHOP10() {
        //15007 product id - PRODUCT8 only has SKU
        final List<Warehouse> shop10wh = warehouseService.getByShopId(10L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductSkuAvailableToSellQuantity("PRODUCT8", shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("PRODUCT8")));
    }


    @Test
    public void testFindProductAvailableToSellQuantitySobotSHOP50() {
        //10000 product id - sobot has 4 skus on 1 warehouse
        final List<Warehouse> shop10wh = warehouseService.getByShopId(50L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(10000L, shop10wh);
        assertEquals(4, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("SOBOT-BEER")));
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("SOBOT-PINK")));
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("SOBOT-LIGHT")));
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("SOBOT-ORIG")));
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantitySobotSHOP50() {
        //10000 product id - sobot has 4 skus on 1 warehouse
        final List<Warehouse> shop10wh = warehouseService.getByShopId(50L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductSkuAvailableToSellQuantity("SOBOT-PINK", shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("SOBOT-PINK")));
    }

    @Test
    public void testFindProductAvailableToSellQuantityProduct5AccSHOP50() {
        //14004 product id - PRODUCT5-ACC only has associations
        final List<Warehouse> shop10wh = warehouseService.getByShopId(50L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(14004L, shop10wh);
        assertEquals(0, skusWarehouse.size());
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantityProduct8SHOP50() {
        //15007 product id - PRODUCT8 only has SKU
        final List<Warehouse> shop10wh = warehouseService.getByShopId(50L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductAvailableToSellQuantity(15007L, shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("PRODUCT8")));
    }

    @Test
    public void testFindProductSkuAvailableToSellQuantityProduct8bySkuSHOP50() {
        //15007 product id - PRODUCT8 only has SKU
        final List<Warehouse> shop10wh = warehouseService.getByShopId(50L);
        Map<String, BigDecimal> skusWarehouse = skuWarehouseService.getProductSkuAvailableToSellQuantity("PRODUCT8", shop10wh);
        assertEquals(1, skusWarehouse.size());
        assertEquals(0, new BigDecimal(0).compareTo(skusWarehouse.get("PRODUCT8")));
    }


    @Test
    public void testIsSkuAvailabilityPreorder () {
        assertFalse(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15300L).getCode(), true));
        assertFalse(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15301L).getCode(), true));
        assertTrue(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15310L).getCode(), true));
        assertTrue(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15320L).getCode(), true));
        assertFalse(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15300L).getCode(), false));
        assertFalse(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15301L).getCode(), false));
        assertTrue(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15310L).getCode(), false));
        assertTrue(skuWarehouseService.isSkuAvailabilityPreorderOrBackorder(productSkuService.findById(15320L).getCode(), false));
    }

}
