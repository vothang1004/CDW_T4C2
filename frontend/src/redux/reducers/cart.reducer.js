import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    listCart: null,
    checkedItems: [],
  },
  reducers: {
    getCart(state, action) {
      state.listCart = action.payload;
    },
    clearCart(state) {
      state.listCart = null;
      state.checkedItems = [];
    },
    updateCheckedItems(state, action) {
      const itemCart = action.payload;
      const isExist = state.checkedItems
        .map((item) => item.id)
        .includes(itemCart.id);
      if (isExist) {
        state.checkedItems = state.checkedItems.filter(
          (item) => item.id !== itemCart.id
        );
      } else {
        state.checkedItems.push(itemCart);
      }
    },
  },
});
export const { getCart, clearCart, updateCheckedItems } = cartSlice.actions;
export default cartSlice.reducer;
