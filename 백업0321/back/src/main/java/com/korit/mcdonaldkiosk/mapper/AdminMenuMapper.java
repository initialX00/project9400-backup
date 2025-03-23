package com.korit.mcdonaldkiosk.mapper;

import com.korit.mcdonaldkiosk.entity.Menu;
import com.korit.mcdonaldkiosk.entity.MenuPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMenuMapper {
    List<Menu> selectAllInfoMenuList();
    List<Menu> selectAllCategories();
    int updateIsExposureByClick(
            @Param("menuId") int menuId,
            @Param("isExposure") int isExposure);

    List<Menu> selectAllMenus();
    List<MenuPrice> getMenuPrices(int menuId);
    int insertMenu(Menu menu);
    int insertMenuPrices(@Param("menuId") int menuId, @Param("menuPrices") List<MenuPrice> menuPrices);
    int deleteMenuPrices(int menuId);
    int deleteMenu(int menuId);


}


//
//    int selectMenuCountAllByCategory(@Param("searchCategory") String searchCategory);
//
//    List<Menu> selectMenuListByCategory(
//            @Param("startIndex") int startIndex,
//            @Param("limitCount") int limitCount,
//            @Param("category") String category
//    );