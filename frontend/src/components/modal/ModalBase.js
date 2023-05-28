import React from "react";
import { Box, IconButton, Modal, Stack, Typography } from "@mui/material";
import ButtonBase from "../button/ButtonBase";
import { IoCloseCircleOutline } from "react-icons/io5";
import { LoadingButton } from "@mui/lab";

function ModalBase({
  children,
  open,
  onClose,
  title,
  disableAction,
  onConfirm,
  confirmText,
  loading,
  width = "300px",
}) {
  return (
    <Modal open={open} onClose={onClose} disableAutoFocus>
      <Box
        sx={{
          width: width,
          maxWidth: "95vw",
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          backgroundColor: "#fff",
          borderRadius: "6px",
        }}
      >
        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
          sx={{ padding: "5px 10px", borderBottom: "1px solid #ccc" }}
        >
          <Typography sx={{ fontSize: "14px", fontWeight: "500" }}>
            {title}
          </Typography>
          <IconButton onClick={onClose}>
            <IoCloseCircleOutline size="16px" />
          </IconButton>
        </Stack>
        <Box sx={{ width: "100%" }}>{children}</Box>
        {!disableAction && (
          <Stack
            direction="row"
            spacing={2}
            alignItems="center"
            justifyContent="flex-end"
            sx={{ padding: "10px" }}
          >
            <ButtonBase
              onClick={onClose}
              className="bg-gray px-4 py-2 text-black rounded-md"
            >
              Đóng
            </ButtonBase>
            {!!onConfirm && (
              <LoadingButton
                loading={loading}
                onClick={onConfirm}
                sx={{
                  backgroundColor: "primary.main",
                  color: "#fff",
                  "&:hover": { backgroundColor: "secondary.main" },
                }}
              >
                {confirmText || "Đồng ý"}
              </LoadingButton>
            )}
          </Stack>
        )}
      </Box>
    </Modal>
  );
}

export default ModalBase;
