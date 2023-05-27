import { axiosPublic } from "../../utils/https";
import { login, logout } from "../reducers/auth.reducer";
import { getCart } from "../reducers/cart.reducer";

// login
const loginUser = async ({
  dispatch,
  router,
  data,
  pathAfterSuccess,
  alert,
}) => {
  try {
    const resp = await axiosPublic.post("/authenticate", data);
    if (resp && resp.status === 200) {
      dispatch(login(resp.data));
      router?.push(pathAfterSuccess);
      return resp.data;
    }
  } catch (error) {
    alert({ message: error.response.data.message });
    return error.response.data;
  }
};
// register
const registerUser = async ({ data, router }) => {
  try {
    const resp = await axiosPublic.post("/users/register", data);
    if (resp && resp.status === 200) {
      router.push("/login");
    }
  } catch (error) {
    console.log(error);
  }
};
// logout user
const logoutUser = ({ dispatch, alert }) => {
  dispatch(logout());
  dispatch(getCart(null));
  alert({ message: "Đăng xuất thành công" });
};

export { loginUser, registerUser, logoutUser };
