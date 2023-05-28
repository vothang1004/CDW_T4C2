import React, { useState } from "react";
import ModalBase from "./ModalBase";
import {
  Box,
  Button,
  FormControl,
  FormControlLabel,
  Grid,
  Radio,
  RadioGroup,
  Stack,
  Typography,
} from "@mui/material";
import InputBase from "../input/InputBase";
import { useDispatch, useSelector } from "react-redux";
import { useForm, Controller } from "react-hook-form";
import { updateUser } from "../../redux/actions/auth.action";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useAlertContext } from "../../contexts/alert/AlertProvider";
import { axiosPublic } from "../../utils/https";
import ModalChangePassword from "../modal/ModalChangePassword";
import { LoadingButton } from "@mui/lab";

function ModalUser({ open, onClose }) {
  const login = useSelector((state) => state.auth.login);
  const axiosPrivate = useAxiosPrivate();
  const { alert } = useAlertContext();
  const dispatch = useDispatch();
  const {
    register,
    control,
    handleSubmit,
    getValues,
    formState: { isSubmitting },
  } = useForm({
    defaultValues: {
      username: login?.user?.username,
      email: login?.user?.email,
      phoneNumber: login?.user?.phoneNumber,
      gender: login?.user?.gender,
    },
  });
  const [openModalChangePassword, setOpenModalChangePassword] = useState(false);
  const [loading, setLoading] = useState(false);

  //handleChangePassword
  const handleChangePassword = async () => {
    try {
      setLoading(true);
      const resp = await axiosPublic.post("/users/forgot-password", {
        email: getValues("email"),
      });
      if (resp && resp.status === 200) {
        setOpenModalChangePassword(true);
        setLoading(false);
      }
    } catch (error) {
      alert({ message: "Internal server error" });
    }
  };

  // handle save
  const handleSave = async (values) => {
    return new Promise((resovle) => {
      setTimeout(() => {
        updateUser({ data: values, axios: axiosPrivate, alert, dispatch });
        onClose();
        resovle();
      }, 200);
    });
  };

  return (
    <>
      {openModalChangePassword && (
        <ModalChangePassword
          open={openModalChangePassword}
          onClose={() => setOpenModalChangePassword(false)}
        />
      )}
      <ModalBase
        open={open}
        onClose={onClose}
        width="600px"
        title="Thông tin của bạn"
        confirmText="Lưu"
        onConfirm={handleSubmit(handleSave)}
        loading={isSubmitting}
      >
        <Box sx={{ padding: "10px" }}>
          <Stack spacing={1}>
            <Grid container spacing={2}>
              {/* username */}
              <Grid item xs={6} md={3}>
                <Typography sx={{ textAlign: "right" }}>
                  Tên người dùng:
                </Typography>
              </Grid>
              <Grid item xs={6} md={9}>
                <InputBase name="username" register={register} />
              </Grid>
              {/* Email */}
              <Grid item xs={6} md={3}>
                <Typography sx={{ textAlign: "right" }}>Email:</Typography>
              </Grid>
              <Grid item xs={6} md={9}>
                <InputBase name="email" register={register} />
              </Grid>
              {/* Phone number */}
              <Grid item xs={6} md={3}>
                <Typography sx={{ textAlign: "right" }}>Điện thoại:</Typography>
              </Grid>
              <Grid item xs={6} md={9}>
                <InputBase name="phoneNumber" register={register} />
              </Grid>
              {/* Gender */}
              <Grid item xs={6} md={3}>
                <Typography sx={{ textAlign: "right" }}>Giới tính:</Typography>
              </Grid>
              <Grid item xs={6} md={9}>
                <Controller
                  name="gender"
                  control={control}
                  render={({ field: { value, onChange } }) => (
                    <FormControl>
                      <RadioGroup row value={value} onChange={onChange}>
                        <FormControlLabel
                          value="female"
                          control={<Radio size="small" />}
                          label="Female"
                        />
                        <FormControlLabel
                          value="male"
                          control={<Radio size="small" />}
                          label="Male"
                        />
                        <FormControlLabel
                          value="other"
                          control={<Radio size="small" />}
                          label="Other"
                        />
                      </RadioGroup>
                    </FormControl>
                  )}
                />
              </Grid>
              <Grid item xs={12}>
                <Stack
                  sx={{ width: "100%" }}
                  direction="row"
                  alignItems="center"
                  justifyContent="center"
                >
                  <LoadingButton
                    loading={loading}
                    onClick={handleChangePassword}
                    variant="contained"
                  >
                    Đổi mật khẩu
                  </LoadingButton>
                </Stack>
              </Grid>
            </Grid>
          </Stack>
        </Box>
      </ModalBase>
    </>
  );
}

export default ModalUser;
