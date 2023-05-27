import { createSlice } from "@reduxjs/toolkit";

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    listCart: null,
  },
  reducers: {
    getCart(state, action) {
      state.listCart = action.payload;
    },
  },
});
export const { getCart } = cartSlice.actions;
export default cartSlice.reducer;
