import { useEffect } from "react";
import { axiosPrivate } from "../utils/https";
import useToken from "./useToken";
import { useRouter } from "next/router";

const useAxiosPrivate = () => {
  const router = useRouter();
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
  const responseInterceptor = axiosPrivate.interceptors.response.use(
    function (response) {
      return response;
    },
    async function (error) {
      if (error?.response?.status === 401) {
        return router.push("/login");
      } else {
        return error;
      }
    }
  );
  useEffect(() => {
    return () => {
      axiosPrivate.interceptors.request.eject(requestInterceptor);
      axiosPrivate.interceptors.response.eject(responseInterceptor);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);
  return axiosPrivate;
};
export default useAxiosPrivate;
