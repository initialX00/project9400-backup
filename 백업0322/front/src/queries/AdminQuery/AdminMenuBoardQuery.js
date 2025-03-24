import { useQuery } from "@tanstack/react-query";
import { getAllInfoMenuByIdApi, getAllMenuListApi, getCategoriesApi } from "../../apis/AdminApi/AdminMenuBoardApi";

//카테고리 불러오기
export const useGetCategories = () => useQuery({
    queryKey: ["useGetCategories"],
    queryFn: getCategoriesApi,
    retry: 0,
    staleTime: 1000 * 60 * 10,
    gcTime: 1000 * 60 * 5
});

//모든 메뉴 불러오기
export const useAllMenuList = () => useQuery({
    queryKey: ["useAllMenuList"],
    queryFn: getAllMenuListApi,
    retry: 0,
    staleTime: 1000 * 60 * 10,
    gcTime: 1000 * 60 * 5
});

//아이디에 해당하는 메뉴 상세정보 불러오기
export const useInfoMenuById = () => useQuery({
    queryKey: ["useInfoMenuById"],
    queryFn: getAllInfoMenuByIdApi,
    retry: 0,
    staleTime: 1000 * 60 * 10,
    gcTime: 1000 * 60 * 5
}) 