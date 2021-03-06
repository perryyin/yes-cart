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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.yes.cart.domain.entity.Product;
import org.yes.cart.domain.entity.ProductAvailabilityModel;
import org.yes.cart.domain.entity.ProductSku;
import org.yes.cart.domain.entity.Warehouse;
import org.yes.cart.service.domain.SkuWarehouseService;
import org.yes.cart.service.domain.WarehouseService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: denispavlov
 * Date: 13-04-06
 * Time: 1:23 PM
 */
public class ProductAvailabilityStrategyImplTest {

    private final Mockery context = new JUnit4Mockery();

    private WarehouseService warehouseService;
    private SkuWarehouseService skuWarehouseService;

    private Product product;
    private ProductSku sku;

    private long shopId = 10l;
    private String skuCode = "SKU";

    @Test
    public void testGetAvailabilityModelForStandardOne() throws Exception {

        final int availability = Product.AVAILABILITY_STANDARD;
        final BigDecimal qty = BigDecimal.ONE;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertTrue(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertTrue(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }

    @Test
    public void testGetAvailabilityModelForStandardZero() throws Exception {

        final int availability = Product.AVAILABILITY_STANDARD;
        final BigDecimal qty = BigDecimal.ZERO;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertFalse(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertFalse(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }


    @Test
    public void testGetAvailabilityModelForPreorderOne() throws Exception {

        final int availability = Product.AVAILABILITY_PREORDER;
        final BigDecimal qty = BigDecimal.ONE;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertTrue(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertTrue(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }

    @Test
    public void testGetAvailabilityModelForPreorderZero() throws Exception {

        final int availability = Product.AVAILABILITY_PREORDER;
        final BigDecimal qty = BigDecimal.ZERO;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }


    @Test
    public void testGetAvailabilityModelForBackorderOne() throws Exception {

        final int availability = Product.AVAILABILITY_BACKORDER;
        final BigDecimal qty = BigDecimal.ONE;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertTrue(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertTrue(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }

    @Test
    public void testGetAvailabilityModelForBackorderZero() throws Exception {

        final int availability = Product.AVAILABILITY_BACKORDER;
        final BigDecimal qty = BigDecimal.ZERO;

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }


    @Test
    public void testGetAvailabilityModelForAlwaysOne() throws Exception {

        final int availability = Product.AVAILABILITY_ALWAYS;
        final BigDecimal qty = BigDecimal.ONE;
        final BigDecimal max = new BigDecimal(Integer.MAX_VALUE);

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertTrue(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(max) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertTrue(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(max) == 0);

        context.assertIsSatisfied();

    }

    @Test
    public void testGetAvailabilityModelForAlwaysZero() throws Exception {

        final int availability = Product.AVAILABILITY_ALWAYS;
        final BigDecimal qty = BigDecimal.ZERO;
        final BigDecimal max = new BigDecimal(Integer.MAX_VALUE);

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertTrue(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertTrue(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(max) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertTrue(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertTrue(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(max) == 0);

        context.assertIsSatisfied();

    }


    @Test
    public void testGetAvailabilityModelForShowroomOne() throws Exception {

        final int availability = Product.AVAILABILITY_SHOWROOM;
        final BigDecimal qty = BigDecimal.ONE;
        final BigDecimal max = new BigDecimal(Integer.MAX_VALUE);

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertFalse(modelProduct.isAvailable());
        assertTrue(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertFalse(modelSku.isAvailable());
        assertTrue(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }

    @Test
    public void testGetAvailabilityModelForShowroomZero() throws Exception {

        final int availability = Product.AVAILABILITY_SHOWROOM;
        final BigDecimal qty = BigDecimal.ZERO;
        final BigDecimal max = new BigDecimal(Integer.MAX_VALUE);

        setTestExpectations(availability, qty);

        final ProductAvailabilityModel modelProduct = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, product);

        assertNotNull(modelProduct);
        assertFalse(modelProduct.isAvailable());
        assertFalse(modelProduct.isInStock());
        assertFalse(modelProduct.isPerpetual());
        assertTrue(modelProduct.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        final ProductAvailabilityModel modelSku = new ProductAvailabilityStrategyImpl(warehouseService, skuWarehouseService).getAvailabilityModel(shopId, sku);

        assertNotNull(modelSku);
        assertFalse(modelSku.isAvailable());
        assertFalse(modelSku.isInStock());
        assertFalse(modelSku.isPerpetual());
        assertTrue(modelSku.getAvailableToSellQuantity(skuCode).compareTo(qty) == 0);

        context.assertIsSatisfied();

    }


    private void setTestExpectations(final int availability, final BigDecimal qty) {

        warehouseService = context.mock(WarehouseService.class, "warehouseService");
        skuWarehouseService = context.mock(SkuWarehouseService.class, "skuWarehouseService");

        final Warehouse warehouse = context.mock(Warehouse.class, "warehouse");
        final List<Warehouse> warehouses = Arrays.asList(warehouse);

        sku = context.mock(ProductSku.class, "sku");
        product = context.mock(Product.class, "product");

        context.checking(new Expectations() {{
            allowing(warehouseService).getByShopId(shopId); will(returnValue(warehouses));
            allowing(sku).getProduct(); will(returnValue(product));
            allowing(sku).getCode(); will(returnValue("ABC"));
            allowing(product).getProductId(); will(returnValue(123L));
            allowing(product).getAvailablefrom(); will(returnValue(null));
            allowing(product).getAvailableto(); will(returnValue(null));
            allowing(product).getAvailability(); will(returnValue(availability));
            allowing(product).getDefaultSku(); will(returnValue(sku));
            allowing(sku).getCode(); will(returnValue(skuCode));
            allowing(skuWarehouseService).getProductAvailableToSellQuantity(123L, warehouses); will(returnValue(new HashMap<String, BigDecimal>() {{
                put(skuCode, qty);
            }}));
            allowing(skuWarehouseService).getProductSkuAvailableToSellQuantity("ABC", warehouses); will(returnValue(new HashMap<String, BigDecimal>() {{
                put(skuCode, qty);
            }}));
        }});

    }

}
