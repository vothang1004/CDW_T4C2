import React from "react";
import ModalBase from "./ModalBase";
import FormLogin from "../../pages/login/components/FormLogin";
import { Box } from "@mui/material";

function ModalLogin({ open, onClose }) {
  return (
    <ModalBase
      open={open}
      onClose={onClose}
      width="600px"
      title="Đăng nhập"
      disableAction
    >
      <Box sx={{ padding: "0 10px 10px 10px" }}>
        <FormLogin onCloseModal={onClose} />
      </Box>
    </ModalBase>
  );
}

export default ModalLogin;
