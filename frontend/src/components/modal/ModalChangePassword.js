import React from "react";
import ModalBase from "../modal/ModalBase";
import { Button, Grid, Stack, Typography } from "@mui/material";
import InputBase from "../input/InputBase";
import { useForm } from "react-hook-form";
import { useAlertContext } from "../../contexts/alert/AlertProvider";
import { axiosPublic } from "../../utils/https";
import { useDispatch } from "react-redux";
import { logout } from "../../redux/reducers/auth.reducer";
import { clearCart } from "../../redux/reducers/cart.reducer";
import { useRouter } from "next/router";

function ModalChangePassword({ open, onClose }) {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();
  const { alert } = useAlertContext();
  const dispatch = useDispatch();
  const router = useRouter();

  const handleChangePassword = async (values) => {
    try {
      const resp = await axiosPublic.post("/users/reset-password", values);
      if (resp && resp.status === 200) {
        dispatch(logout());
        dispatch(clearCart());
        router.push("/login");
      }
    } catch (error) {
      alert({ message: "Internal server error" });
    }
  };

  return (
    <ModalBase
      open={open}
      onClose={onClose}
      width="600px"
      title="Thay đổi mật khẩu"
      confirmText="Xác nhận"
      onConfirm={handleSubmit(handleChangePassword)}
      loading={isSubmitting}
    >
      <Stack sx={{ padding: "10px" }} spacing={1}>
        <Typography sx={{ fontSize: "13px", textAlign: "center" }}>
          Chúng tôi đã gửi mã xác thực qua email của bạn, vui lòng kiểm tra và
          điền vào ô bên dưới
        </Typography>
        <Grid container spacing={2} sx={{ width: "100%" }}>
          <Grid item xs={6} md={3}>
            <Typography sx={{ textAlign: "right" }}>Mã xác thực:</Typography>
          </Grid>
          <Grid item xs={6} md={9}>
            <InputBase name="token" register={register} />
          </Grid>
          <Grid item xs={6} md={3}>
            <Typography sx={{ textAlign: "right" }}>Mật khẩu mới:</Typography>
          </Grid>
          <Grid item xs={6} md={9}>
            <InputBase name="password" register={register} />
          </Grid>
        </Grid>
      </Stack>
    </ModalBase>
  );
}

export default ModalChangePassword;
