// hooks/useProcessPointMutation.js
import { useMutation } from "@tanstack/react-query";
import { processPointApi } from "../apis/pointApi";  // processPointApi import

export const useProcessPointMutation = () => useMutation({
    mutationKey: ["processPointMutation"],
    mutationFn: processPointApi,
    retry: 0,  // 실패 시 재시도 횟수를 0으로 설정 (원하는 값으로 설정 가능)
});
