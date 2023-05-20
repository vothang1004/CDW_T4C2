import { useSelector } from "react-redux";

const useToken = () => {
  const login = useSelector((state) => state.auth.login);
  if (login) {
    return login.token;
  } else {
    return "";
  }
};
export default useToken;
