import { useEffect } from "react";
import { axiosPrivate } from "../utils/https";
import useToken from "./useToken";

const useAxiosPrivate = () => {
  const accessToken = useToken();

  const requestInterceptor = axiosPrivate.interceptors.request.use(
    function (config) {
      if (!config.headers["Authorization"]) {
        config.headers["Authorization"] = `Bearer ${accessToken}`;
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );
  useEffect(() => {
    return () => {
      axiosPrivate.interceptors.request.eject(requestInterceptor);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);
  return axiosPrivate;
};
export default useAxiosPrivate;
