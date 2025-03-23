package com.korit.mcdonaldkiosk.controller.admin;


import com.korit.mcdonaldkiosk.dto.request.ReqExposureDto;
import com.korit.mcdonaldkiosk.entity.Menu;
import com.korit.mcdonaldkiosk.entity.MenuPrice;
import com.korit.mcdonaldkiosk.service.admin.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminMenuController {

    @Autowired
    private AdminMenuService adminMenuService;

    // 메뉴의 모든 정보를 조회하는 API
    @GetMapping("/menus1")
    public ResponseEntity<List<Menu>> getAllInfoMenu() {
        return ResponseEntity.ok().body(adminMenuService.getAllInfoMenuList());
    }

    // 카테고리 목록을 조회하는 API
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<Menu> allCategories = adminMenuService.getAllCategories();
        //객체에서 카테고리 값만 추출한다.
        List<String> categories = allCategories
                .stream()
                .map(Menu::getMenuCategory)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(categories);
    }

    // 메뉴 노출 여부 변경 API
    // 프론트랑 연결이 원할하지 않을경우 Map이 아니라 dto 만들어서 값 받기
    @PutMapping("/isExposure")
    public ResponseEntity<?> changeExposure(
            @RequestBody ReqExposureDto request
    ) {
        //받은값 int로 변환
        int menuId = request.getMenuId();
        int isExposure = request.getIsExposure();
        adminMenuService.changeIsExposure(menuId, isExposure);
        return ResponseEntity.ok().build();
    }

    // 모든 메뉴 조회
    @GetMapping("/menus")
    public ResponseEntity<?> getAllMenus() {
        return ResponseEntity.ok().body(adminMenuService.getAllMenus());
    }


    // 특정 메뉴의 가격 정보 조회
    @GetMapping("/menus/{menuId}/prices")
    public ResponseEntity<?> getMenuPrices(@PathVariable int menuId) {
        return ResponseEntity.ok().body(adminMenuService.getMenuPrices(menuId));
    }

    // 메뉴 추가 (가격 포함)
    @PostMapping("/menus")
    public void addMenu(@RequestBody Menu menu, @RequestParam List<MenuPrice> prices) {
        adminMenuService.addMenu(menu, prices);
    }

    // 메뉴 삭제
    @DeleteMapping("/menus/{menuId}")
    public void deleteMenu(@PathVariable int menuId) {
        adminMenuService.deleteMenu(menuId);
    }


}



//    // 페이지 갯수 API
//    @GetMapping("/list")
//    public ResponseEntity<?> searchBoardList(@ModelAttribute ReqMenuListDto dto) {
//        //메뉴 갯수
//        int totalMenuListCount = adminMenuService.getMenuListCountByCategory(dto.getCategory());
//        //페이지 수 계산
//        int totalPages = totalMenuListCount * dto.getLimitCount() == 0
//                ? totalMenuListCount / dto.getLimitCount()
//                : totalMenuListCount / dto.getLimitCount() + 1;
//        //입력받은 카테고리와 일치하는 카테고리 찾기
//        String filteredCategory = adminMenuService.getAllCategories()
//                .stream()
//                .filter(menu -> menu.getMenuCategory().equals(dto.getCategory()))
//                .map(menu -> menu.getMenuCategory())
//                .collect(Collectors.joining(","));
//
//        if (filteredCategory.isEmpty()) {
//            dto.setCategory("전체");
//        }
//
//
//        //빌더로 객체를 생성하여 응답 담기
//        RespMenuListDto respMenuListDto =
//                RespMenuListDto.builder()
//                        .page(dto.getPage())
//                        .limitCount(dto.getLimitCount())
//                        .totalPages(totalPages)
//                        .totalElements(totalMenuListCount)
//                        .isFirstPage(dto.getPage() == 1)
//                        .isLastPage(dto.getPage() == totalPages)
//                        .nextPage(dto.getPage() != totalPages ? dto.getPage() + 1 :0)
//                        .menuList(adminMenuService.getMenuListSearchByCategory(dto)) //검색 조건에 맞는 카테고리 가져오기
//                        .build();
//
//        return ResponseEntity.ok().body(respMenuListDto);
//    }