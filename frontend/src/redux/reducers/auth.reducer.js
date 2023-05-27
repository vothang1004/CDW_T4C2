import { createSlice } from "@reduxjs/toolkit";

const authSlice = createSlice({
  name: "auth",
  initialState: {
    login: null,
  },
  reducers: {
    login(state, action) {
      state.login = action.payload;
    },
    logout(state) {
      state.login = null;
    },
  },
});
export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
