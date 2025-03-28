package com.korit.mcdonaldkiosk.repository.admin;


import com.korit.mcdonaldkiosk.entity.Menu;
import com.korit.mcdonaldkiosk.entity.MenuPrice;
import com.korit.mcdonaldkiosk.entity.MenuWithAllInfo;
import com.korit.mcdonaldkiosk.entity.Order;
import com.korit.mcdonaldkiosk.mapper.AdminMenuMapper;
import com.korit.mcdonaldkiosk.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminMenuRepository {
    @Autowired
    private AdminMenuMapper adminMenuMapper;

    // 모든 메뉴 리스트를 반환
    public Optional<MenuWithAllInfo> findAllInfoMenuById(int menuId) {
        return Optional.ofNullable(adminMenuMapper.selectAllInfoMenuById(menuId));
    }

    // 모든 카테고리를 반환
    public List<Menu> findAllCategories() {
        return adminMenuMapper.selectAllCategories();
    }

    public void updateIsExposure(int menuId,int isExposure) {
        adminMenuMapper.updateIsExposureByClick(menuId, isExposure);
    }

    // 전체 메뉴 조회
    public Optional<List<Menu>> getAllMenus() {
        List<Menu> foundMenus = adminMenuMapper.selectAllMenus();
        return foundMenus.isEmpty() ? Optional.empty() : Optional.of(foundMenus);
    }

    // 특정 메뉴 정보 조회
    public Optional<Menu> getMenuById(int menuId) {
        return Optional.ofNullable(adminMenuMapper.selectMenuById(menuId));
    }

    public Optional<Boolean> addMenu(Menu menu, List<MenuPrice> menuPrices) {
        try {
            adminMenuMapper.insertMenu(menu);
            if (!menuPrices.isEmpty()) {
                adminMenuMapper.insertMenuPrice(menu.getMenuId(), menuPrices);
            }
            return Optional.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(false);
        }
    }

    public Optional<Boolean> deleteMenu(int menuId) {
        try {
            adminMenuMapper.deleteMenuPrices(menuId);
            int deletedRows = adminMenuMapper.deleteMenu(menuId);
            return Optional.of(deletedRows > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(false);
        }
    }

    public Optional<Boolean> updateMenu(Menu menu, List<MenuPrice> menuPrices) {
        try {
            adminMenuMapper.updateMenu(menu);
            adminMenuMapper.deleteMenuPrices(menu.getMenuId());
            if (!menuPrices.isEmpty()) {
                adminMenuMapper.insertMenuPrice(menu.getMenuId(), menuPrices);
            }
            return Optional.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(false);
        }
    }

    // 페이지네이션 이미지 + 메뉴명
    public List<Menu> getAllMenuImages() {
        return adminMenuMapper.selectAllMenuImages();
    }

}
