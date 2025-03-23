import { useMutation } from "@tanstack/react-query";
import { updateExposureApi } from "../apis/AdminApi/AdminMenuBoardApi";

export const useUpdateIsPosureMutation = () => useMutation({
    mutationKey: ["useUpdateIsPosureMutation"],
    mutationFn: updateExposureApi,
    retry: 0,
});