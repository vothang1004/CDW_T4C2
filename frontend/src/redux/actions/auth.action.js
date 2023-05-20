import { axiosPublic } from "../../utils/https";
import { login } from "../reducers/auth.reducer";

const loginUser = async ({ dispatch, router, data }) => {
  try {
    const resp = await axiosPublic.post("/authenticate", data);
    if (resp && resp.status === 200) {
      dispatch(login(resp.data));
      router.push("/home");
    }
  } catch (error) {
    console.log({ error });
  }
};

export { loginUser };
