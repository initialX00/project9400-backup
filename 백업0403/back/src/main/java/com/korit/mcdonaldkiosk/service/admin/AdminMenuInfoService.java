package com.korit.mcdonaldkiosk.service.admin;

import com.korit.mcdonaldkiosk.dto.request.ReqAdminMenuInfoDto;
import com.korit.mcdonaldkiosk.entity.MenuInfo;
import com.korit.mcdonaldkiosk.repository.admin.AdminMenuInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMenuInfoService {
    @Autowired
    private AdminMenuInfoRepository adminMenuInfoRepository;

    // 메뉴 영양 정보 다건 조회
    public List<MenuInfo> getMenuInfoListByMenuId(int menuId) {
        return adminMenuInfoRepository.getMenuInfoListByMenuId(menuId);
    }

    // 메뉴 영양 정보 및 원산지 수정
    public int updateMenuInfo(ReqAdminMenuInfoDto reqDto) {
        MenuInfo menuInfo = MenuInfo.builder()
                .menuInfoId(reqDto.getMenuInfoId())
                .weight(reqDto.getWeight())
                .volume(reqDto.getVolume())
                .calories(reqDto.getCalories())
                .sugars(reqDto.getSugars())
                .protein(reqDto.getProtein())
                .saturatedFat(reqDto.getSaturatedFat())
                .sodium(reqDto.getSodium())
                .caffeine(reqDto.getCaffeine())
                .menuOrigin(reqDto.getMenuOrigin())
                .build();

        return adminMenuInfoRepository.updateMenuInfo(menuInfo);
    }

    // 메뉴 영양 정보 및 원산지 추가
    public int addMenuInfo(ReqAdminMenuInfoDto reqDto) {
        MenuInfo menuInfo = MenuInfo.builder()
                .menuId(reqDto.getMenuId()) // 추가할 때는 menuId 필요
                .weight(reqDto.getWeight())
                .volume(reqDto.getVolume())
                .calories(reqDto.getCalories())
                .sugars(reqDto.getSugars())
                .protein(reqDto.getProtein())
                .saturatedFat(reqDto.getSaturatedFat())
                .sodium(reqDto.getSodium())
                .caffeine(reqDto.getCaffeine())
                .menuOrigin(reqDto.getMenuOrigin())
                .size(reqDto.getSize()) // M or L
                .build();
        return adminMenuInfoRepository.addMenuInfo(menuInfo);
    }

    // 메뉴 영양 정보 및 원산지 삭제
}
