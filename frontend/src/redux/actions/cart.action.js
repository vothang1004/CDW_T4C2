import { getCart } from "../reducers/cart.reducer";

// get cart
const asyncGetCart = async ({ alert, axios, dispatch }) => {
  try {
    const resp = await axios.get(`/carts/listmycard`);
    if (resp && resp.status === 200) {
      dispatch(getCart(resp.data));
      return resp.data;
    }
  } catch (error) {
    alert({ message: error?.response?.data?.message || "Server error" });
    return error?.response?.data;
  }
};
// add to cart
const asyncAddToCart = async ({
  alert,
  axios,
  data,
  dispatch,
  plus = true,
}) => {
  try {
    const resp = await axios.post(`carts/products`, data);
    if (resp && resp.status === 200) {
      dispatch(getCart(resp.data));
      if (plus) {
        alert({ message: `Bạn đã thêm sản phẩm '${data.name}' vào giỏ hàng` });
      }
      return resp.data;
    } else {
      alert({
        message: resp?.response?.data?.message || "Internal server error",
      });
      return null;
    }
  } catch (error) {
    alert({ message: error?.message || "Internal server error" });
    return error;
  }
};
// update cart
const asyncUpdateCart = async ({
  idProduct,
  number = 1,
  axios,
  alert,
  dispatch,
}) => {
  try {
    const resp = await axios.delete(`carts/products/${idProduct}/${number}`);
    if (resp && resp.status === 200) {
      dispatch(getCart(resp.data));
      return resp.data;
    } else {
      alert({
        message: resp?.response?.data?.message || "Internal server error",
      });
      return null;
    }
  } catch (error) {
    alert({
      message: error?.message || "Internal server error",
    });
    return error?.response?.data;
  }
};
// remove product from cart
const asyncRemovefromCart = async ({ idProduct, alert }) => {
  try {
    const resp = await axios.delete(`/carts/products/${idProduct}`);
    if (resp && resp.status === 200) {
      return resp.data;
    }
  } catch (error) {
    alert({ message: error?.response?.data?.message || "Server error" });
    return error?.response?.data;
  }
};
export { asyncGetCart, asyncAddToCart, asyncUpdateCart, asyncRemovefromCart };
