package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MaterialCurrentStockMapperEx {

    int batchInsert(List<MaterialCurrentStock> list);

    List<MaterialCurrentStock> getCurrentStockMapByIdList(
            @Param("materialIdList") List<Long> materialIdList);

    void updateUnitPriceByMId(
            @Param("currentUnitPrice") BigDecimal currentUnitPrice,
            @Param("materialId") Long materialId);

    BigDecimal getCurrentUnitPriceByMId(@Param("materialId") Long materialId);

    void batchDeleteByDepots(@Param("ids") String ids[]);

    /**
     * 原子扣减库存（带乐观锁和库存校验）
     * @param materialId 商品ID
     * @param depotId 仓库ID
     * @param decreaseNumber 扣减数量
     * @param version 版本号（乐观锁）
     * @return 更新的记录数
     */
    int decreaseStock(@Param("materialId") Long materialId,
                      @Param("depotId") Long depotId,
                      @Param("decreaseNumber") BigDecimal decreaseNumber,
                      @Param("version") Integer version);

    /**
     * 原子增加库存（带乐观锁）
     * @param materialId 商品ID
     * @param depotId 仓库ID
     * @param increaseNumber 增加数量
     * @param version 版本号（乐观锁）
     * @return 更新的记录数
     */
    int increaseStock(@Param("materialId") Long materialId,
                      @Param("depotId") Long depotId,
                      @Param("increaseNumber") BigDecimal increaseNumber,
                      @Param("version") Integer version);

    /**
     * 查询当前库存（带悲观锁 for update）
     * @param materialId 商品ID
     * @param depotId 仓库ID
     * @return 库存记录
     */
    MaterialCurrentStock getStockWithLock(@Param("materialId") Long materialId,
                                          @Param("depotId") Long depotId);

    /**
     * 查询当前库存（不带锁）
     * @param materialId 商品ID
     * @param depotId 仓库ID
     * @return 库存记录
     */
    MaterialCurrentStock getCurrentStock(@Param("materialId") Long materialId,
                                         @Param("depotId") Long depotId);

    /**
     * 乐观锁更新库存数量
     * @param id 记录ID
     * @param currentNumber 当前数量
     * @param version 版本号
     * @return 更新的记录数
     */
    int updateCurrentNumberWithVersion(@Param("id") Long id,
                                       @Param("currentNumber") BigDecimal currentNumber,
                                       @Param("version") Integer version);
}