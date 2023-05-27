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
const asyncAddToCart = async ({ data, axios, alert, dispatch }) => {
  try {
    const resp = await axios.post(`/carts/products`, data);
    if (resp && resp.status === 200) {
      alert({
        message: `Bạn đã thêm sản phẩm '${data?.name || ""}' vào giỏ hàng`,
      });
      dispatch(getCart(resp.data));
      return resp.data;
    } else {
      alert({ message: resp?.data?.message || "Internal server error" });
    }
  } catch (error) {
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
export { asyncGetCart, asyncAddToCart, asyncRemovefromCart };
