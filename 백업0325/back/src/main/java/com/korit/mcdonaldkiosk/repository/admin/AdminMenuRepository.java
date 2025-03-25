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
            // 1. 메뉴 데이터 추가 (menu_tb)
            adminMenuMapper.insertMenu(menu);

            // 2. 가격 데이터 추가 (menu_price_tb)
            if (!menuPrices.isEmpty()) {
                adminMenuMapper.insertMenuPrices(menu.getMenuId(), menuPrices);
            }

            return Optional.of(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(false);
        }
    }

    public Optional<Boolean> deleteMenu(int menuId) {
        try {
            // 1. 가격 정보 삭제 (menu_price_tb) - menu_id 외래키 참조로 인해 먼저 삭제 필요
            adminMenuMapper.deleteMenuPrices(menuId);

            // 2. 메뉴 정보 삭제 (menu_tb)
            int deletedRows = adminMenuMapper.deleteMenu(menuId);

            // 삭제된 행이 없으면 false 반환
            if (deletedRows == 0) {
                return Optional.of(false);
            }

            return Optional.of(true); //  성공 시 true 반환
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(false); //  실패 시 false 반환
        }
    }

    // 페이지네이션 이미지 + 메뉴명
    public List<Menu> getAllMenuImages() {
        return adminMenuMapper.selectAllMenuImages();
    }

}
